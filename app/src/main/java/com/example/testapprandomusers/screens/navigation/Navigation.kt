package com.example.testapprandomusers.screens.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class MainNavGraph(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class SettingsNavGraph(
    val start: Boolean = false
)
