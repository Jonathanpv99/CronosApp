package com.example.cronoapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cronoapp.model.Cronos
import com.example.cronoapp.reporitory.CronosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CronosViewModel @Inject constructor(private val cronosRepository: CronosRepository): ViewModel() {

    private val _cronosList = MutableStateFlow<List<Cronos>>(emptyList())
    val cronosList = _cronosList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO ) {
            cronosRepository.getAllCronos().collect(){item ->
                if(item.isNullOrEmpty()){
                    _cronosList.value = emptyList()
                }else{
                    _cronosList.value = item
                }
            }
        }
    }

    fun addCrono(crono: Cronos) = viewModelScope.launch {
        cronosRepository.addCrono(crono)
    }

    fun updateCrono(crono: Cronos) = viewModelScope.launch {
        cronosRepository.updateCrono(crono)
    }

    fun deleteCrono(crono: Cronos) = viewModelScope.launch {
        cronosRepository.deleteCrono(crono)
    }


}