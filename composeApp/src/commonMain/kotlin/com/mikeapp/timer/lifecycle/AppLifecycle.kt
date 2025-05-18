package com.mikeapp.timer.lifecycle

expect class AppLifecycle() {
    fun observeLifecycle(onEnterForeground: () -> Unit, onEnterBackground: () -> Unit)
}