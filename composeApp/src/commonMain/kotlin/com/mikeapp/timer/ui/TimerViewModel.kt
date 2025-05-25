package com.mikeapp.timer.ui

import com.mikeapp.timer.data.TimerRepository
import com.mikeapp.timer.ui.base.BaseViewModel
import com.mikeapp.timer.ui.util.CommonFlow
import com.mikeapp.timer.ui.util.asCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow

// commonMain
class TimerViewModel(
    private val repository: TimerRepository
) : BaseViewModel() {
    private val _timeRecords = MutableStateFlow<List<Long>>(emptyList())

    val timeRecords: CommonFlow<List<Long>> = _timeRecords.asCommonFlow()

    private val _reps = MutableStateFlow<List<Long>>(emptyList())

    val reps: CommonFlow<List<Long>> = _reps.asCommonFlow()

    init {
//        observeTimeRecords()
//        observeReps()
    }

//    fun addTimeRecord(time: Long) {
//        repository.insertTimeRecord(time)
//    }
//
//    fun removeLastTimeRecord() {
//        repository.deleteLastTimeRecord()
//    }
//
//    fun clearAllTimeRecords() {
//        repository.clearAllTimeRecords()
//    }
//
//    fun clearReps() {
//        repository.clearAllReps()
//    }
//
//    fun addRep(rep: Long) {
//        repository.insertRep(rep)
//    }
//
//    fun removeRep(rep: Long) {
//        repository.deleteRepById(rep)
//    }
//
//    private fun observeReps() {
//        viewModelScope.launch {
//            repository.observeAllReps().collect {
//                _reps.value = it
//            }
//        }
//    }
//
//    private fun observeTimeRecords() {
//        viewModelScope.launch {
//            repository.observeAllTimeRecords().collect {
//                _timeRecords.value = it
//            }
//        }
//    }
}