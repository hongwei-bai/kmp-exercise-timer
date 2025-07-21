package com.mikeapp.timer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

fun isiOS() =  getPlatform().name.contains("iOS")

fun isAndroid() =  getPlatform().name.contains("Android")