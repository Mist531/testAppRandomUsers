package com.example.testapprandomusers.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.testapprandomusers.ui.theme.TestAppRandomUsersTheme
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    TestAppRandomUsersTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),

            )
        }
    }
}