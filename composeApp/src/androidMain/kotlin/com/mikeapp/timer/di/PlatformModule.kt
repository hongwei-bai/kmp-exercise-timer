package com.mikeapp.timer.di

import android.content.Context
import com.mikeapp.timer.database.DatabaseDriverFactory
import com.mikeapp.timer.database.DatabaseHelper
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single { DatabaseDriverFactory(context) }
    single { DatabaseHelper(get()) }
    single<Context> { context.applicationContext }
}