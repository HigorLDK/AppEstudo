package com.example.appestudo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appestudo.ui.screens.marcacao.TelaMarcacao
import com.example.appestudo.ui.theme.AppEstudoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppEstudoTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main_container"
                ) {
                    composable("main_container") {
                        MainScaffold(navController)
                    }

                    composable("marcacao") {
                        TelaMarcacao(navController)
                    }
                }
            }
        }
    }
}
