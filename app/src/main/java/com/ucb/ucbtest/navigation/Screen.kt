package com.ucb.ucbtest.navigation

sealed class Screen(val route: String) {
    object GitaliasScreen : Screen("gitlab")
    object TakePhotoScreen: Screen("takephoto")
    object MenuScreen: Screen("menu")
    object LoginScreen: Screen("login")
}