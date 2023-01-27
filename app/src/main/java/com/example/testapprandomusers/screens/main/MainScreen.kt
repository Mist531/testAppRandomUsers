package com.example.testapprandomusers.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.testapprandomusers.screens.MainNavGraph
import com.example.testapprandomusers.ui.theme.TestAppRandomUsersTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    //val systemUIController = rememberSystemUiController()
    //systemUIController.setStatusBarColor(GazTheme.colors.orange)
    TestAppRandomUsersTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            /*DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                //startRoute = Route()
            )*/
            Box(
                modifier = Modifier.padding(it)
            ){
                Text(text = "GAYYY")
            }
        }
    }
}

@MainNavGraph(start = true)
@Destination
@Composable
fun MainContentMock(navigator: DestinationsNavigator) {
    Text(text = "JAVA")
}

@MainNavGraph
@Destination
@Composable
fun MainContentMock1(navigator: DestinationsNavigator) {
    First(navigator = navigator)
}

@MainNavGraph
@Destination
@Composable
fun First(
    navigator: DestinationsNavigator
) {
    Text(
        text = "Mock Screen",
        modifier = Modifier.clickable {
        /*navigator.navigate(SecondDestination)*/ })
}

@MainNavGraph
@Destination
@Composable
fun Second(
    navigator: DestinationsNavigator
) {
    Text(
        text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        modifier = Modifier.clickable { navigator.navigateUp() })
}