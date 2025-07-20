package com.mikeapp.timer.di

import org.koin.dsl.module

val platformModule = module {
    single { Factory() } // iOS version of Factory
}