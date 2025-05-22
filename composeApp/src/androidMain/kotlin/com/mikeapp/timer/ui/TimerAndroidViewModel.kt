package com.mikeapp.timer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TimerAndroidViewModel(
    private val timerViewModel: TimerViewModel
) : ViewModel() {
    // Convert CommonFlow to StateFlow using Android's scope
    val reps: StateFlow<List<Long>> = timerViewModel.reps
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun clearReps() = timerViewModel.clearReps()
    fun addRep(rep: Long) = timerViewModel.addRep(rep)
    fun removeRep(rep: Long) = timerViewModel.removeRep(rep)

    override fun onCleared() {
        super.onCleared()
        timerViewModel.onCleared()
    }
}