package com.mikeapp.timer.di

import com.mikeapp.timer.database.DatabaseHelper
import com.mikeapp.timer.domain.TimerUseCase
import org.koin.dsl.module

val commonModule = module {
    single { DatabaseHelper(get()) }
    single { TimerUseCase(get()) }
}