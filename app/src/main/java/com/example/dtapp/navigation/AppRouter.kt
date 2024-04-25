package com.example.dtapp.navigation

private object Route {
    const val ABOUT = "ABOUT"
    const val HOME = "HOME"
    const val NET = "NET"
    const val EDIT = "EIT/{habitId}"
}

sealed class Screen(val route: String) {
    data object About : Screen(Route.ABOUT)
    data object Home : Screen(Route.HOME)
    data object Net : Screen(Route.NET)
    data object Edit : Screen(Route.EDIT) {
        fun createRoute(habitId: Int = -1) = Route.EDIT.replace("{habitId}", habitId.toString())
    }
}