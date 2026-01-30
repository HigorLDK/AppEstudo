package com.example.appestudo.ui.screens.marcacao.servicos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaHorarios(
    viewModel: AgendamentoViewModel,
    onAvancar: () -> Unit
) {
    val horariosOcupados by viewModel.horariosOcupados.collectAsState()
    val horarios = remember { gerarHorarios() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Text("Selecione o horário", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(horarios) { hora ->
                val ocupado = horariosOcupados.contains(hora)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !ocupado) {
                            viewModel.selecionarHora(hora)
                            onAvancar()
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (ocupado) Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
                    )
                ) {
                    Text(hora, modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}

private fun gerarHorarios(): List<String> {
    val lista = mutableListOf<String>()
    var hora = 8
    var minuto = 0
    while (hora < 18) {
        lista.add(String.format("%02d:%02d", hora, minuto))
        minuto += 30
        if (minuto == 60) {
            minuto = 0
            hora++
        }
    }
    return lista
}
