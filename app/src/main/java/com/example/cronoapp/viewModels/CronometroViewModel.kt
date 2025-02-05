package com.example.cronoapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cronoapp.state.CronoState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CronometroViewModel: ViewModel() {

    var state by mutableStateOf(CronoState())
        private set

    var cronoJob by mutableStateOf<Job?>(value = null)
        private set

    var time by mutableStateOf(0L)

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