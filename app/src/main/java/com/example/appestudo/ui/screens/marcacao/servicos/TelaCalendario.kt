package com.example.appestudo.ui.screens.marcacao.servicos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun TelaCalendario(
    viewModel: AgendamentoViewModel,
    onAvancar: () -> Unit
) {
    val diasOcupados by viewModel.diasOcupados.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.carregarDiasDoMes()
    }

    val hoje = LocalDate.now()
    val diasNoMes = hoje.lengthOfMonth()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Text("Selecione a data", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(diasNoMes) { index ->
                val dia = index + 1
                val data = hoje.withDayOfMonth(dia).toString()
                val ocupado = diasOcupados.contains(data)

                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            if (ocupado) Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
                        )
                        .clickable(enabled = !ocupado) {
                            viewModel.selecionarData(data)
                            onAvancar()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(dia.toString())
                }
            }
        }
    }
}
