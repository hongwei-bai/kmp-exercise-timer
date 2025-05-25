package com.mikeapp.timer

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mikeapp.timer.di.commonModule
import com.mikeapp.timer.di.platformModule
import com.mikeapp.timer.lifecycle.AppLifecycleHandlers
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    startKoin {
        modules(platformModule, commonModule)
    }

    val onExit: () -> Unit = {
        AppLifecycleHandlers.onEnterBackground()
    }

    Window(
        onCloseRequest = {
            onExit()             // <-- your exit logic here
            exitApplication()    // then actually exit
        },
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
