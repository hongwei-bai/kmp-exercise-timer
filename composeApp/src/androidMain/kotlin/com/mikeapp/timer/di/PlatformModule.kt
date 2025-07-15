package com.mikeapp.timer.di

import android.content.Context
import com.mikeapp.timer.alarm.AlarmHelper
import com.mikeapp.timer.interop.NativeInterface
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single<NativeInterface> { NativeInterface(get()) }
    single<Context> { context.applicationContext }
    single { AlarmHelper(get()) }
}