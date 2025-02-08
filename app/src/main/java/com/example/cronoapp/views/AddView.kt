package com.example.cronoapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cronoapp.R
import com.example.cronoapp.components.CircleButton
import com.example.cronoapp.components.MainIconButton
import com.example.cronoapp.components.MainTextField
import com.example.cronoapp.components.MainTitle
import com.example.cronoapp.components.formatTime
import com.example.cronoapp.model.Cronos
import com.example.cronoapp.viewModels.CronometroViewModel
import com.example.cronoapp.viewModels.CronosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
    navController: NavController,
    cronometroVM: CronometroViewModel,
    cronosVM: CronosViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Add Crono") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.AutoMirrored.Filled.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        ContentAddView(it, navController, cronometroVM, cronosVM)
    }
}

@Composable
fun ContentAddView(
    it: PaddingValues,
    navController: NavController,
    cronometroVM: CronometroViewModel,
    cronosVM: CronosViewModel
) {

    val state = cronometroVM.state

    LaunchedEffect(state.cronoActive) {
        cronometroVM.cronos()
    }

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatTime(cronometroVM.time),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            //start
            CircleButton(
                icon = painterResource(id = R.drawable.start), //Icons.Default.PlayArrow,
                enable = !state.cronoActive,
            ) {
                cronometroVM.start()
            }
            //pause
            CircleButton(
                icon = painterResource(id = R.drawable.pause),
                enable = state.cronoActive,
            ) {
                cronometroVM.pause()
            }
            //stop
            CircleButton(
                icon = painterResource(id = R.drawable.stop),
                enable = !state.cronoActive,
            ) {
                cronometroVM.stop()
            }
            //view save
            CircleButton(
                icon = painterResource(id = R.drawable.save),
                enable = state.showSaveButton,
            ) {
                cronometroVM.showTextField()
            }
        }

        if (state.showTextField) {
            var isError by remember { mutableStateOf(false) }
            MainTextField(
                value = state.title,
                onValueChange = {
                    cronometroVM.onValue(it)
                    isError = it.isBlank()
                },
                label = "Title"
            )
            if (isError) {
                Text(
                    text = "El título no puede estar vacío",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            Button(
                onClick = {
                    if (state.title.isBlank()) { // Validar antes de ejecutar la acción
                        isError = true
                    } else {
                        cronosVM.addCrono(
                            Cronos(
                                title = state.title,
                                crono = cronometroVM.time
                            )
                        )
                        cronometroVM.stop()
                        cronometroVM.onValue("")
                        isError = false
                        navController.popBackStack()
                    }

                }
            ) {
                Text(
                    text = "Guardar",
                    color = Color.White
                )
            }
        }
    }
}