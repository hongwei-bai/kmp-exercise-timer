package com.mikeapp.timer.ui

import com.mikeapp.timer.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}