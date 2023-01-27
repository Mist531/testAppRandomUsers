package com.example.testapprandomusers.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacerHeight(
    height: Int = 10
) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun SpacerWidth(
    width: Int = 10
) {
    Spacer(modifier = Modifier.width(width.dp))
}