package com.example.appestudo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appestudo.ui.navigation.BottomItem
import com.example.appestudo.ui.screens.home.PrimeiraTela
import com.example.appestudo.ui.screens.outros.TelaC
import com.example.appestudo.ui.screens.vacina.TelaB
import com.example.appestudo.ui.theme.Yellow10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(rootNavController: NavController) {

    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(BottomItem.HOME) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Yellow10),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.pt02),
                            contentDescription = "Logo",
                            modifier = Modifier.height(120.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, null)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Yellow10) {
                BottomItem.entries.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem == item,
                        onClick = {
                            selectedItem = item
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, null) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White,
                            selectedIconColor = Color.Black
                        )
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomItem.HOME.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomItem.HOME.route) {
                PrimeiraTela(rootNavController)
            }
            composable(BottomItem.VACINA.route) {
                TelaB(rootNavController)
            }
            composable(BottomItem.OUTROS.route) {
                TelaC(rootNavController)
            }
        }
    }
}