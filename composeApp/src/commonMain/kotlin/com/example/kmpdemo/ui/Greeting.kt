package com.example.kmpdemo.ui

import com.example.kmpdemo.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}