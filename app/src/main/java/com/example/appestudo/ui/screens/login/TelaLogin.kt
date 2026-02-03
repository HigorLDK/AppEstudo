package com.example.appestudo.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.appestudo.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@Composable
fun TelaLogin(
    viewModel: LoginViewModel = viewModel(),
    onLoginSucesso: () -> Unit = {}
) {
    val loading by viewModel.loading.collectAsState()
    val erro by viewModel.erro.collectAsState()

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEB3B))
            .padding(horizontal = 32.dp, vertical = 48.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "É uma honra ter\nvocê aqui!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        UnderlineTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        UnderlineTextField(
            label = "Senha",
            value = senha,
            onValueChange = { senha = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Esqueceu a senha?",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(16.dp))

        erro?.let {
            Text(it, color = Color.Red, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.login(email, senha, onLoginSucesso)
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(
                if (loading) "Entrando..." else "Acessar",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "ou",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        GoogleButton(
            onClick = {
                scope.launch {
                    loginComGoogleCredentialManager(
                        context = context,
                        onSuccess = { idToken ->
                            viewModel.loginGoogle(idToken, onLoginSucesso)
                        },
                        onError = { /* você pode exibir snackbar se quiser */ }
                    )
                }
            }
        )
    }
}



@Composable
fun UnderlineTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column {
        Text(label, fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))
        Divider(color = Color.Black, thickness = 1.dp)
    }
}

@Composable
fun GoogleButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.google_logo),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Continue com Google", fontWeight = FontWeight.Medium)
        }
    }
}

suspend fun loginComGoogleCredentialManager(
    context: android.content.Context,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) {
    val credentialManager = CredentialManager.create(context)

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.default_web_client_id))
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val result = credentialManager.getCredential(context, request)
        val credential = result.credential
        val googleCredential =
            GoogleIdTokenCredential.createFrom(credential.data)

        onSuccess(googleCredential.idToken)
    } catch (e: Exception) {
        onError("Login cancelado")
    }
}



@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    TelaLogin()
}
