package com.example.appestudo.ui.screens.login


import androidx.lifecycle.ViewModel
import com.example.appestudo.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    fun login(email: String, senha: String, onSuccess: () -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            _erro.value = "Preencha todos os campos"
            return
        }

        _loading.value = true
        _erro.value = null

        AuthRepository.loginEmailSenha(
            email,
            senha,
            onSuccess = {
                _loading.value = false
                onSuccess()
            },
            onError = {
                _loading.value = false
                _erro.value = it
            }
        )
    }

    fun loginGoogle(idToken: String, onSuccess: () -> Unit) {
        _loading.value = true
        _erro.value = null

        AuthRepository.loginComGoogle(
            idToken,
            onSuccess = {
                _loading.value = false
                onSuccess()
            },
            onError = {
                _loading.value = false
                _erro.value = it
            }
        )
    }
}
