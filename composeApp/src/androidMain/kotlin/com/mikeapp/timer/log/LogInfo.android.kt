package com.mikeapp.timer.log

actual object LogInfo {
    actual fun getThreadName(): String {
        return Thread.currentThread().name
    }
}