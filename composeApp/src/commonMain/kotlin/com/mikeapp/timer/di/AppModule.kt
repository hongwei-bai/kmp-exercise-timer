package com.mikeapp.timer.di

import com.mikeapp.timer.data.TimerRepository
import com.mikeapp.timer.data.room.TimerRoomDatabase
import com.mikeapp.timer.ui.TimerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val commonModule = module {
    single {
        CoroutineScope(Dispatchers.Default + SupervisorJob())
    }
    single<TimerRoomDatabase> { get<Factory>().createRoomDatabase() }
    single { TimerRepository(get(), get()) }
    single { TimerViewModel(get()) }
}
