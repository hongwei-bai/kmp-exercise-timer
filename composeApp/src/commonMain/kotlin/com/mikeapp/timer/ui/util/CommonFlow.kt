package com.mikeapp.timer.ui.util

// commonMain
import kotlinx.coroutines.flow.Flow

interface CommonFlow<T> : Flow<T>

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = object : CommonFlow<T>, Flow<T> by this {}