package com.mikeapp.timer.di

import com.mikeapp.timer.database.DatabaseDriverFactory
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoinIos(driverFactory: DatabaseDriverFactory) {
    startKoin {
        modules(
            module {
                single { driverFactory }
            },
            commonModule
        )
    }
}