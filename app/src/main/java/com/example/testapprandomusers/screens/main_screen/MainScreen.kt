package com.example.testapprandomusers.screens.main_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.plusAssign
import com.example.testapprandomusers.screens.NavGraphs
import com.example.testapprandomusers.screens.destinations.SettingsScreenDestination
import com.example.testapprandomusers.ui.theme.AppTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator
    AppTheme {
        val systemUIController = rememberSystemUiController()
        systemUIController.setStatusBarColor(AppTheme.colors.black)
        val settingsScreen: DirectionDestinationSpec = SettingsScreenDestination
        ModalBottomSheetLayout(
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(AppTheme.padding.default),
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    TopBar(
                        navController = navController,
                        settingsOnClick = {
                            navController.navigate(settingsScreen.route)
                        },
                        homeOnClick = {
                            navController.popBackStack()
                        }
                    )
                },
                backgroundColor = AppTheme.colors.black,
                drawerBackgroundColor = AppTheme.colors.black,
            ) {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    engine = rememberAnimatedNavHostEngine(
                        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                    ),
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                )
            }
        }
    }
}