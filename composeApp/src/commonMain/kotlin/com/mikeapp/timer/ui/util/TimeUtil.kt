package com.mikeapp.timer.ui.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentTimeLong(): Long = Clock.System.now().toEpochMilliseconds()

fun formatMillisTo24hTime(millis: Long, timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localTime = instant.toLocalDateTime(timeZone).time

    val hour = localTime.hour.toString().padStart(2, '0')
    val minute = localTime.minute.toString().padStart(2, '0')
    val second = localTime.second.toString().padStart(2, '0')

    return "$hour:$minute:$second"
}

fun getAmPmFromTimeMs(timeMs: Long): String {
    val instant = Instant.fromEpochMilliseconds(timeMs)
    val localTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val hour = localTime.hour
    return if (hour < 12) "AM" else "PM"
}

const val MS_PER_MINUTE = 60 * 1000