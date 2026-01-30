package com.example.appestudo.ui.screens.marcacao.servicos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaConfirmacao(
    viewModel: AgendamentoViewModel,
    petId: String,
    clienteId: String,
    onConfirmar: () -> Unit
) {
    val servico by viewModel.servicoSelecionado.collectAsState()
    val data by viewModel.dataSelecionada.collectAsState()
    val hora by viewModel.horaSelecionada.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Confirme seu agendamento", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Serviço: $servico")
                Text("Data: $data")
                Text("Hora: $hora")
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.confirmarAgendamento(
                    petId = petId,
                    clienteId = clienteId,
                    onSuccess = onConfirmar,
                    onError = {}
                )
            }
        ) {
            Text("Confirmar")
        }
    }
}
