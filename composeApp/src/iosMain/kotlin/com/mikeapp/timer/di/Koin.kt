package com.mikeapp.timer.di

import org.koin.core.context.startKoin

fun initKoinIos() {
    startKoin {
        modules(platformModule, commonModule)
    }
}