package com.example.appestudo.ui.screens.vacina

import androidx.lifecycle.ViewModel
import com.example.appestudo.data.model.PetInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import android.util.Log

class ProntuarioViewModel : ViewModel() {

    private val petsFake = listOf(fakePet())

    fun getPet(id: String): Flow<PetInfo?> = flow {
        Log.d("PET_DEBUG", "ViewModel recebeu id = $id")

        val pet = petsFake.find { it.id == id }

        Log.d("PET_DEBUG", "Pet encontrado = $pet")

        emit(pet)
    }

    private fun fakePet() = PetInfo(
        id = "01",
        nome = "Thor",
        especie = "Canino",
        raca = "Golden Retriever",
        peso = 28.0,
        idade = 4,
        tutor = "Marcos Silva",
        cpf = "123.456.789-00",
        telefone = "(11) 99999-0000",
        endereco = "Rua das Flores, 123",

        vacinacao = true,
        endoparasitas = true,
        ectoparasitas = false,
        medicacaoContinua = false,
        suplementacao = true,
        cirurgias = false,
        exameRecente = true,
        felinoTestado = false,
        alergiaMedicamento = false,

        hidratacao = "Normal",
        glicemia = "Normal",
        mucosa = "Normal",
        pelagem = "Normal",
        freqCardiaca = "Normal",
        freqRespiratoria = "Normal",
        cavidadeOral = "Normal",
        condutoresAuditivos = "Normal",
        temperatura = "38.5°C",
        pressao = "120/80",
        linfonodos = "Normais",
        deambulacao = "Normal",
        propriocepcao = "Normal",
        palpacaoAbdominal = "Sem dor",
        cavidadeNasal = "Normal",
        oftalmologicos = "Normais",

        doencasPregressas = false,
        doencasPresentes = true,
        digestorio = listOf("Vômito", "Diarreia"),
        urogenital = listOf("Urina normal"),
        cardiorrespiratorio = listOf("Tosse"),
        neurologico = emptyList(),
        locomotor = emptyList(),
        pele = listOf("Prurido"),
        olhos = emptyList(),
        ouvido = emptyList(),
        ambiente = listOf("Urbano"),
        contactantes = emptyList(),
        produtosToxicos = emptyList(),

        racaoPetiscos = listOf("Ração seca comercial", "Petiscos"),
        alimentacaoNatural = emptyList(),

        queixa = "Paciente apresentou episódios de vômito e apatia há 2 dias.",
        conduta = "Prescrito antiemético e dieta leve por 3 dias.",
        tratamentos = "Solicitado hemograma completo e ultrassonografia abdominal.",
        retorno = "Reavaliar em 7 dias."
    )
}
