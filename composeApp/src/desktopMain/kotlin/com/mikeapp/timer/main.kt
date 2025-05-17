package com.mikeapp.timer

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-exercise-timer",
        state = rememberWindowState(
            width = 412.dp,
            height = 915.dp
        )
    ) {
        App()
    }
}