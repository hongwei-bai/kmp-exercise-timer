package com.mikeapp.timer.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

actual class AppLifecycle {
    actual fun observeLifecycle(onEnterForeground: () -> Unit, onEnterBackground: () -> Unit) {
        val lifecycleOwner = ProcessLifecycleOwner.get()
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                onEnterForeground()
            }

            override fun onPause(owner: LifecycleOwner) {
                onEnterBackground()
            }
        })
    }
}