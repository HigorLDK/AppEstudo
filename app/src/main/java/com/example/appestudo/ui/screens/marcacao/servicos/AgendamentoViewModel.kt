package com.example.appestudo.ui.screens.marcacao.servicos


import androidx.lifecycle.ViewModel
import com.example.appestudo.data.repository.AgendamentoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AgendamentoViewModel : ViewModel() {

    private val _diasOcupados = MutableStateFlow<Set<String>>(emptySet())
    val diasOcupados: StateFlow<Set<String>> = _diasOcupados

    private val _horariosOcupados = MutableStateFlow<Set<String>>(emptySet())
    val horariosOcupados: StateFlow<Set<String>> = _horariosOcupados

    private val _servicoSelecionado = MutableStateFlow<String?>(null)
    val servicoSelecionado: StateFlow<String?> = _servicoSelecionado

    private val _dataSelecionada = MutableStateFlow<String?>(null)
    val dataSelecionada: StateFlow<String?> = _dataSelecionada

    private val _horaSelecionada = MutableStateFlow<String?>(null)
    val horaSelecionada: StateFlow<String?> = _horaSelecionada

    fun selecionarServico(servico: String) {
        _servicoSelecionado.value = servico
    }

    fun selecionarData(data: String) {
        _dataSelecionada.value = data
        carregarHorarios(data)
    }

    fun selecionarHora(hora: String) {
        _horaSelecionada.value = hora
    }

    fun carregarDiasDoMes(dataAtual: LocalDate = LocalDate.now()) {
        val mes = dataAtual.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        AgendamentoRepository.buscarAgendamentosDoMes(mes) { lista ->
            val ocupados = lista.groupBy { it.first }
                .filter { (_, horarios) -> horarios.size >= gerarHorarios().size }
                .keys
            _diasOcupados.value = ocupados.toSet()
        }
    }

    fun carregarHorarios(data: String) {
        AgendamentoRepository.buscarHorariosDoDia(data) { lista ->
            _horariosOcupados.value = lista.toSet()
        }
    }

    fun confirmarAgendamento(
        petId: String,
        clienteId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val servico = _servicoSelecionado.value ?: return
        val data = _dataSelecionada.value ?: return
        val hora = _horaSelecionada.value ?: return

        AgendamentoRepository.criarAgendamento(
            data = data,
            hora = hora,
            servico = servico,
            petId = petId,
            clienteId = clienteId,
            onSuccess = onSuccess,
            onError = onError
        )
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
}
