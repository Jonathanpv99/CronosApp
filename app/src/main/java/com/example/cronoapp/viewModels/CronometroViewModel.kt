package com.example.cronoapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cronoapp.reporitory.CronosRepository
import com.example.cronoapp.state.CronoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CronometroViewModel @Inject constructor(private val  reporsitory: CronosRepository) : ViewModel() {

    var state by mutableStateOf(CronoState())
        private set

    var cronoJob by mutableStateOf<Job?>(value = null)
        private set

    var time by mutableStateOf(0L)
        private set

    fun getCronoById(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            reporsitory.getById(id).collect{ item ->
                if(item != null){
                    time = item.crono
                    state = state.copy(title = item.title)
                }else{
                    Log.d("Error", "El Objeto Crono es Nulo")
                }
            }
        }
    }

    fun onValue(value: String){
        state = state.copy(title = value)
    }

    fun start(){
        state = state.copy(
            cronoActive = true
        )
    }

    fun pause(){
        state = state.copy(
            cronoActive = false,
            showSaveButton = true
        )
    }

    fun stop(){
        cronoJob?.cancel()
        time = 0
        state = state.copy(
            cronoActive = false,
            showSaveButton = false,
            showTextField = false
        )
    }

    fun showTextField(){
        state = state.copy(
            showTextField = true
        )
    }

    fun cronos(){
        if(state.cronoActive){
            cronoJob?.cancel()
            cronoJob = viewModelScope.launch {
                while (true){
                    delay(1000)
                    time += 1000
                }
            }
        }else{
            cronoJob?.cancel()
        }
    }
}