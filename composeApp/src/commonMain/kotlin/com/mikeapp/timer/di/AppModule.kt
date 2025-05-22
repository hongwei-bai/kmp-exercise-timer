package com.mikeapp.timer.di

import com.mikeapp.timer.data.TimerRepository
import com.mikeapp.timer.database.DatabaseHelper
import com.mikeapp.timer.ui.TimerViewModel
import org.koin.dsl.module

val commonModule = module {
    single { DatabaseHelper(get()) }
    single { TimerRepository(get()) }
    factory { TimerViewModel(get()) }
}