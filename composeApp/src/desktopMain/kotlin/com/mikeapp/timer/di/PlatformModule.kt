package com.mikeapp.timer.di

import com.mikeapp.timer.database.DatabaseDriverFactory
import com.mikeapp.timer.database.DatabaseHelper
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory() }
    single { DatabaseHelper(get()) }
}