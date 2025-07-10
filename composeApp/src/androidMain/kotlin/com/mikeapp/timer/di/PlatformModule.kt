package com.mikeapp.timer.di

import android.content.Context
import com.mikeapp.timer.alarm.AlarmHelper
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single<Factory> { Factory(get()) }
    single<Context> { context.applicationContext }
    single { AlarmHelper(get()) }
}