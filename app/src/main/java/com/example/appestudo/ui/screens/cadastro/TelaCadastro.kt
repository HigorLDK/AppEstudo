package com.example.appestudo.ui.screens.cadastro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TelaCadastro(
    viewModel: CadastroViewModel = viewModel(),
    onCadastroSucesso: () -> Unit = {}
) {
    val loading by viewModel.loading.collectAsState()
    val erro by viewModel.erro.collectAsState()

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // Fundo branco
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )

        // Forma amarela diagonal
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .align(Alignment.TopEnd)
        ) {
            val path = Path().apply {
                moveTo(size.width * 0.35f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                close()
            }
            drawPath(path, color = Color(0xFFFFEB3B))
        }

        // Conteúdo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {

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

            Spacer(modifier = Modifier.height(12.dp))

            erro?.let {
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedButton(
                onClick = {
                    viewModel.cadastrar(email, senha, onCadastroSucesso)
                },
                enabled = !loading,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, Color(0xFFFFEB3B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    if (loading) "Cadastrando..." else "Cadastrar-se",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
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
        Text(text = label, fontSize = 14.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(6.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )

        Divider(color = Color.Black, thickness = 1.dp)
    }
}


@Preview(showBackground = true)
@Composable
fun CadastroPreview() {
    TelaCadastro()
}
