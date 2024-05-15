package com.guido.playstore20.navigation

sealed class AppScreens(val route: String){

    object HomeScreen : AppScreens("home_screen")
    object ProfileScreen : AppScreens("profile_screen")

    object LoginScreen : AppScreens("login_screen")

    object RegisterScreen : AppScreens("register_screen")

}