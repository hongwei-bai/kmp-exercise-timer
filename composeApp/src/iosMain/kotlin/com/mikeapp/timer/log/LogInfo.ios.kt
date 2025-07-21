package com.mikeapp.timer.log

import platform.Foundation.NSThread

actual object LogInfo {
    actual fun getThreadName(): String {
        return NSThread.currentThread().toString()
    }
}