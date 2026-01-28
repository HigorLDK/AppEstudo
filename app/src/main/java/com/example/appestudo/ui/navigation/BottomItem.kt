package com.example.appestudo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomItem(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "Início", Icons.Default.Home),
    VACINA("vacina", "Vacina", Icons.Default.Info),
    OUTROS("outros", "Outros", Icons.Default.Favorite)
}