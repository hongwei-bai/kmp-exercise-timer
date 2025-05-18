package com.mikeapp.timer.di

import com.mikeapp.timer.database.DatabaseHelper
import org.koin.dsl.module

val commonModule = module {
    single { DatabaseHelper(get()) }
//    single { MyRepository(get()) }
//    single { SomeUseCase(get()) }
}