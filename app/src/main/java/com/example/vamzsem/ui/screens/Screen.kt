package com.example.vamzsem.ui.screens

sealed class Screen(val route: String) {
    object Calories : Screen("calories")
    object Sports : Screen("sports")
    object Calendar : Screen("calendar")
    object Settings : Screen("settings")
}
