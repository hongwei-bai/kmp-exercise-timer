package com.mikeapp.timer.ui

import androidx.lifecycle.ViewModel

class TimerAndroidViewModel(
    private val timerViewModel: TimerViewModel
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        timerViewModel.onCleared()
    }
}