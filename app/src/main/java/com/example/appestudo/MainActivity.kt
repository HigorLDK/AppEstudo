package com.example.appestudo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appestudo.ui.screens.cadastro.TelaCadastro
import com.example.appestudo.ui.screens.infoPet.TelaInfoPet
import com.example.appestudo.ui.screens.infoPet.prontuario.ProntuariopetInfoScreen
import com.example.appestudo.ui.screens.login.TelaLogin
import com.example.appestudo.ui.screens.login.WelcomeScreen
import com.example.appestudo.ui.screens.marcacao.servicos.AgendamentoViewModel
import com.example.appestudo.ui.screens.marcacao.servicos.TelaCalendario
import com.example.appestudo.ui.screens.marcacao.servicos.TelaConfirmacao
import com.example.appestudo.ui.screens.marcacao.servicos.TelaHorarios
import com.example.appestudo.ui.screens.marcacao.servicos.TelaServicos
import com.example.appestudo.ui.theme.AppEstudoTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !viewModel.isReady.value
        }

        enableEdgeToEdge()
        setContent {
            AppEstudoTheme {

                val navController = rememberNavController()
                val agendamentoViewModel: AgendamentoViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
                        "main_container"
                    } else {
                        "main"
                    }
                ) {

                    composable("main") {
                        WelcomeScreen(
                            navController,
                            onLoginClick = {navController.navigate("login")},
                            onRegisterClick = {navController.navigate("cadastro")})
                    }

                    composable("main_container") {
                        MainScaffold(navController)
                    }

                    composable("login") {
                        TelaLogin(onLoginSucesso = {navController.navigate("main_container")})
                    }

                    composable("cadastro") {
                        TelaCadastro(onCadastroSucesso = {navController.navigate("login")})
                    }

                    composable("marcacao") {
                        // TelaMarcacao(navController)
                    }

                    composable(
                        route = "prontuario/{petId}",
                        arguments = listOf(navArgument("petId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val petId = backStackEntry.arguments?.getString("petId")!!
                        ProntuariopetInfoScreen(petInfoId = petId, navController = navController)
                    }

                    composable(
                        route = "infopet/{petId}",
                        arguments = listOf(navArgument("petId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val petId = backStackEntry.arguments?.getString("petId")!!
                        TelaInfoPet(petInfoId = petId, navController = navController)
                    }

                    /* ---------------- FLUXO DE AGENDAMENTO ---------------- */

                    composable("consulta") {
                        TelaServicos(agendamentoViewModel) {
                            navController.navigate("data")
                        }
                    }

                    composable("data") {
                        TelaCalendario(agendamentoViewModel) {
                            navController.navigate("horario")
                        }
                    }

                    composable("horario") {
                        TelaHorarios(agendamentoViewModel) {
                            navController.navigate("confirmacao")
                        }
                    }

                    composable("confirmacao") {
                        TelaConfirmacao(
                            viewModel = agendamentoViewModel,
                            petId = "pet123",
                            clienteId = "user456"
                        ) {
                            navController.popBackStack("main_container", false)
                        }
                    }
                }
            }
        }
    }
}
