package com.example.appestudo.ui.screens.cadastro

import androidx.lifecycle.ViewModel
import com.example.appestudo.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CadastroViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    fun cadastrar(email: String, senha: String, onSuccess: () -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            _erro.value = "Preencha todos os campos"
            return
        }

        _loading.value = true
        _erro.value = null

        AuthRepository.cadastrarUsuario(
            email = email,
            senha = senha,
            onSuccess = {
                _loading.value = false
                onSuccess()
            },
            onError = { mensagem ->
                _loading.value = false
                _erro.value = mensagem
            }
        )
    }
}
