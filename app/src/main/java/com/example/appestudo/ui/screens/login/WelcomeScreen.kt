package com.example.appestudo.ui.screens.login

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appestudo.R
import com.example.appestudo.ui.theme.AppEstudoTheme
import com.example.appestudo.ui.theme.Yellow10

@Composable
fun WelcomeScreen(
    navController: NavController,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(bottomStart = 120.dp, bottomEnd = 120.dp))
                .background(Yellow10)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, top = 40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.logo_po),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(width = 188.dp, height = 166.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {

                CircularPetImage(
                    res = R.drawable.dog02,
                    size = 110.dp,
                    modifier = Modifier.offset(x = (-100).dp, y = (-10).dp)
                )

                CircularPetImage(
                    res = R.drawable.cat,
                    size = 160.dp,
                    modifier = Modifier.offset(x = 80.dp, y = (-120).dp)
                )

                CircularPetImage(
                    res = R.drawable.dog01,
                    size = 220.dp,
                    modifier = Modifier.offset(x = 50.dp, y = 70.dp)
                )
            }

            Spacer(modifier = Modifier.height(200.dp))

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEB3B),
                    contentColor = Color.Black
                )
            ) {
                Text("Acessar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate("cadastro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color(0xFFFFEB3B))
            ) {
                Text(
                    "Cadastrar-se",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun CircularPetImage(
    @DrawableRes res: Int,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
                .border(4.dp, Color.White.copy(alpha = 0.95f), CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    AppEstudoTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}
