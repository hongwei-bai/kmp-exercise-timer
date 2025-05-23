package com.mikeapp.timer

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mikeapp.timer.di.commonModule
import com.mikeapp.timer.di.platformModule
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    startKoin {
        modules(platformModule, commonModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-exercise-timer",
        state = rememberWindowState(
            width = 412.dp,
            height = 915.dp
        ),
        icon = painterResource("icon.ico")
    ) {
        App()
    }
}
