package com.example.testapprandomusers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Colors(
    val black: Color = Color.Black,
    val white: Color = Color.White,
    val grey1: Color = Color(0xFFE5E5E5),
    val grey2: Color = Color(0xFF4D4D4F),
    val red: Color = Color(0xFFE53935),
    val darkGrey: Color = Color(0xFF142330),
    val grey: Color = Color(0xFFC6C7C8),
)

val LocalColors = staticCompositionLocalOf {
    Colors()
}

@Immutable
data class Paddings(
    val small: Dp = 10.dp,
    val default: Dp = 15.dp,
)

val LocalPadding = staticCompositionLocalOf {
    Paddings()
}

@Immutable
data class Shapes(
    val default: Dp = 15.dp,
    val small: Dp = 10.dp,
)

val LocalShapes = staticCompositionLocalOf {
    Shapes()
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides Colors(),
        LocalPadding provides Paddings(),
        content = content
    )
}

object AppTheme {

    val shapes: Shapes
        @Composable
        get() = LocalShapes.current

    val colors: Colors
        @Composable
        get() = LocalColors.current

    val padding: Paddings
        @Composable
        get() = LocalPadding.current
}