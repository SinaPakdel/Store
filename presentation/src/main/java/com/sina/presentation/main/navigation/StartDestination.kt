package com.sina.presentation.main.navigation

import com.sina.presentation.R


sealed class StartDestination(var destination: Int) {
    object Login : StartDestination(R.id.login_nav)
    object Main : StartDestination(R.id.main_nav)
}