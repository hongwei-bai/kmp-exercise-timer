package com.mikeapp.timer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform