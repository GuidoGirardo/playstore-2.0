package com.guido.playstore20.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guido.playstore20.screens.HomeScreen
import com.guido.playstore20.screens.ProfileScreen
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun AppNavigation(viewModel: PlaystoreViewModel, context: Context){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController, viewModel, context)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController, viewModel)
        }
    }

}