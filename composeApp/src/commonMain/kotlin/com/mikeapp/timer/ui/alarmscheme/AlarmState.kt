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

    companion object {
        const val nameInactive = "Inactive"
        const val nameAlarming = "Alarming"
        const val namePaused = "Paused"
        const val nameActive = "Active"

        fun AlarmState.getName(): String {
            return when (this) {
                Inactive -> nameInactive
                Alarming -> nameAlarming
                is Paused -> namePaused
                is Active -> nameActive
            }
        }

        fun AlarmState.getAlarmTime(): Long {
            return when (this) {
                is Paused -> this.alarmTime
                is Active -> this.alarmTime
                else -> 0
            }
        }
    }
}