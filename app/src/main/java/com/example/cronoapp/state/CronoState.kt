package com.example.cronoapp.state

data class CronoState(
    val cronoActive: Boolean = false,
    val showSaveButton: Boolean = false,
    val showTextField: Boolean = false,
    val title: String = "",
)
