package com.example.testapprandomusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.testapprandomusers.screens.main_elements.MainScreen
import com.example.testapprandomusers.ui.theme.TestAppRandomUsersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppRandomUsersTheme {
                MainScreen()
            }
        }
    }
}