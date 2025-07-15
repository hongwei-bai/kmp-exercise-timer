package com.mikeapp.timer.di

import com.mikeapp.timer.interop.NativeInterface
import org.koin.dsl.module

val platformModule = module {
    single { NativeInterface() } // iOS version of Factory

//    factory {
//        val greeter: NativeGreeter = NativeGreeter
//        greeter
//    }
}