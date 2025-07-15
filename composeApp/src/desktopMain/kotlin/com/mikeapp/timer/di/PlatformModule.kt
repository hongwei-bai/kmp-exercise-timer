package com.mikeapp.timer.di

import com.mikeapp.timer.interop.NativeInterface
import org.koin.dsl.module

val platformModule = module {
    single<NativeInterface> { NativeInterface() }
}