package com.mikeapp.timer.interop

object NativeGreeter {
    var delegate: ((String) -> String)? = null

    fun greet(name: String): String {
        return delegate?.invoke(name) ?: "No delegate"
    }
}