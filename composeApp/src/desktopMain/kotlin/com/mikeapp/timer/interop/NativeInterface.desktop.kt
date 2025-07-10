package com.mikeapp.timer.interop

actual class NativeInterface {
    actual fun greet(name: String): String {
        return "Hello from Desktop $name!"
    }
}