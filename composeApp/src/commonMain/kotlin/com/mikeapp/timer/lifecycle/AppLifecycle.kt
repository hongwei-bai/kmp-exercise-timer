package com.mikeapp.timer.lifecycle

expect class AppLifecycle() {
    suspend fun observeLifecycle(onEnterForeground: () -> Unit, onEnterBackground: () -> Unit)
}