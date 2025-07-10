package com.mikeapp.timer.interop

actual object NativeInterface {
    var delegate: ((String) -> String)? = null

    actual fun greet(name: String): String {
        return delegate?.invoke(name) ?: "No delegate"
    }
}