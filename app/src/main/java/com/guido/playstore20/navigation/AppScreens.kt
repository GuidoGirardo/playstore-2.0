package com.guido.playstore20.navigation

sealed class AppScreens(val route: String){

    object HomeScreen : AppScreens("home_screen")

}