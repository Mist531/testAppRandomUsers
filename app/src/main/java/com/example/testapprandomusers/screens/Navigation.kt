package com.example.testapprandomusers.screens

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

typealias NavGraphGenerated = com.example.testapprandomusers.screens.main.NavGraphs

@RootNavGraph(start = true)
@NavGraph
annotation class MainNavGraph(
    val start: Boolean = false
)