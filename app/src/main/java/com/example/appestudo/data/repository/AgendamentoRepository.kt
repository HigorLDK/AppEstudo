package com.example.appestudo.data.repository

import com.google.firebase.firestore.FirebaseFirestore

object AgendamentoRepository {

    private val db = FirebaseFirestore.getInstance()

    fun buscarAgendamentosDoMes(
        mes: String,
        onResult: (List<Pair<String, String>>) -> Unit
    ) {
        db.collection("agendamentos")
            .whereGreaterThanOrEqualTo("data", "$mes-01")
            .whereLessThanOrEqualTo("data", "$mes-31")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull {
                    val data = it.getString("data")
                    val hora = it.getString("hora")
                    if (data != null && hora != null) data to hora else null
                }
                onResult(lista)
            }
    }

    fun buscarHorariosDoDia(
        data: String,
        onResult: (List<String>) -> Unit
    ) {
        db.collection("agendamentos")
            .whereEqualTo("data", data)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull {
                    it.getString("hora")
                }
                onResult(lista)
            }
    }

    fun criarAgendamento(
        data: String,
        hora: String,
        servico: String,
        petId: String,
        clienteId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val docId = "$data-$hora"
        val docRef = db.collection("agendamentos").document(docId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            if (snapshot.exists()) {
                throw Exception("Horário já ocupado")
            }

            val dados = hashMapOf(
                "data" to data,
                "hora" to hora,
                "servico" to servico,
                "petId" to petId,
                "clienteId" to clienteId,
                "status" to "ativo",
                "timestamp" to System.currentTimeMillis()
            )

            transaction.set(docRef, dados)
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onError(it.message ?: "Erro ao agendar")
        }
    }

    fun cancelarAgendamento(
        data: String,
        hora: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        db.collection("agendamentos")
            .document("$data-$hora")
            .update("status", "cancelado")
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Erro ao cancelar") }
    }
}