package com.example.appestudo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomItem(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "Início", Icons.Default.Home),
   // VACINA("vacina", "Vacina", Icons.Default.Info),
    PERFIL("perfil", "Perfil", Icons.Default.AccountCircle)
}