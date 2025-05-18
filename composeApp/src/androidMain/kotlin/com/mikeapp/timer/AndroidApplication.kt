package com.mikeapp.timer

import android.app.Application
import com.mikeapp.timer.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AndroidApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApplication)
            modules(platformModule(this@AndroidApplication))
        }
    }
}