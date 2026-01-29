package com.example.appestudo.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appestudo.data.model.PetInfo
import com.example.appestudo.data.repository.PetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = PetRepository()

    private val _pets = MutableStateFlow<List<PetInfo>>(emptyList())
    val pets: StateFlow<List<PetInfo>> = _pets

    init {
        carregarPets()
    }

    private fun carregarPets() {
        viewModelScope.launch {
            _pets.value = repository.getPets()
        }
    }
}