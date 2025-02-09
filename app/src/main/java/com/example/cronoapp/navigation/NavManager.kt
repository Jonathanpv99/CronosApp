package com.example.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cronoapp.viewModels.CronometroViewModel
import com.example.cronoapp.viewModels.CronosViewModel
import com.example.cronoapp.views.AddView
import com.example.cronoapp.views.EditView
import com.example.cronoapp.views.HomeView

@Composable
fun NavManager(cronometroVM: CronometroViewModel, cronosVM: CronosViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){
        composable("Home"){
            HomeView(navController, cronosVM)
        }
        composable("AddView"){
            AddView(navController, cronometroVM, cronosVM)
        }
        composable("EditView/{id}", arguments = listOf(
            navArgument("id"){ type = NavType.LongType}
        )){
            val id = it.arguments?.getLong("id") ?: 0
            EditView(navController, cronometroVM, cronosVM, id)
        }
    }
}