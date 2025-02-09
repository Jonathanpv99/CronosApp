package com.example.cronoapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cronoapp.components.Alert
import com.example.cronoapp.components.CronoCard
import com.example.cronoapp.components.FloatButton
import com.example.cronoapp.components.MainTitle
import com.example.cronoapp.components.formatTime
import com.example.cronoapp.viewModels.CronosViewModel
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    cronosVM: CronosViewModel
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle( title = "Crono App") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")
            }
        }
    ) {
        ContentHomeView(it, navController, cronosVM)
    }
}

@Composable
fun ContentHomeView(
    it: PaddingValues,
    navController: NavController,
    cronosVM: CronosViewModel
    ){
    Column(
        modifier = Modifier.padding(it)
    ) {
        val cronosList by cronosVM.cronosList.collectAsState()

        LazyColumn {
            items(cronosList){ item ->
                val corrutinescope = rememberCoroutineScope()
                var showAlert by remember { mutableStateOf(false) }

                val delet = SwipeAction(
                    icon = rememberVectorPainter( Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = { showAlert = true }
                )
                if(showAlert){
                    Alert(
                        title = "Eliminar Registro",
                        message = "Estas Seguro Que Quieres Eliminar Este Registro : ${item.title}?",
                        confirmText = "SI, Eliminar",
                        onConfirmClick = {
                            corrutinescope.launch {
                                cronosVM.deleteCrono(item)
                            }
                            showAlert = false
                        },
                        onDismissClick = { showAlert = false}
                    )
                }

                SwipeableActionsBox(
                    endActions = listOf(delet),
                    swipeThreshold = 150.dp
                ) {
                    CronoCard(
                        title = item.title,
                        crono = formatTime(item.crono),

                        ) {
                        navController.navigate("EditView/${item.id}")
                    }
                }
            }
        }
    }
}