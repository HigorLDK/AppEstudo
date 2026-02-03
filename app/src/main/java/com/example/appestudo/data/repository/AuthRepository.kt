package com.example.appestudo.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

object AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun cadastrarUsuario(
        email: String,
        senha: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener { result ->
                val uid = result.user!!.uid

                val dados = hashMapOf(
                    "email" to email,
                    "criadoEm" to System.currentTimeMillis()
                )

                db.collection("usuarios").document(uid)
                    .set(dados)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener {
                        onError(it.message ?: "Erro ao salvar usuário")
                    }
            }
            .addOnFailureListener {
                onError(it.message ?: "Erro ao cadastrar")
            }
    }

    fun loginEmailSenha(
        email: String,
        senha: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onError(it.message ?: "Erro ao fazer login")
            }
    }

    fun loginComGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onError(it.message ?: "Erro ao logar com Google")
            }
    }
}