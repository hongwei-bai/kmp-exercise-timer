package com.mikeapp.timer.ui.alarmscheme

import com.mikeapp.timer.ui.util.formatMillisTo24hTime

sealed class AlarmState {
    object Inactive : AlarmState()
    object Alarming : AlarmState()
    data class Active(val alarmTime: Long) : AlarmState() {
        override fun toString(): String {
            return "Active(${formatMillisTo24hTime(alarmTime)})"
        }
    }

    data class Paused(val alarmTime: Long) : AlarmState() {
        override fun toString(): String {
            return "Paused(${formatMillisTo24hTime(alarmTime)})"
        }
    }

    override fun toString(): String {
        return when (this) {
            Inactive -> "Inactive"
            Alarming -> "Alarming"
            is Paused -> toString()
            is Active -> toString()
        }
    }
}