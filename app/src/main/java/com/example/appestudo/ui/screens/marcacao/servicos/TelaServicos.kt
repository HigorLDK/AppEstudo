package com.example.appestudo.ui.screens.marcacao.servicos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appestudo.ui.theme.Yellow10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaServicos(
    viewModel: AgendamentoViewModel,
    onAvancar: () -> Unit
) {
    val servicos = listOf(
        "Consulta",
        "Cirurgias",
        "Banho e Tosa",
        "Vacinação",
        "Retorno",
        "Cancelamento"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Marcação",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Yellow10
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            servicos.forEach { servico ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selecionarServico(servico)
                            onAvancar()
                        },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        servico,
                        modifier = Modifier.padding(20.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
