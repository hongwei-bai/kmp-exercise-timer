package com.mikeapp.timer.data.model

sealed class RunningTimer {
    data class MinimalismTimer(val minimalism: String) : RunningTimer()
}