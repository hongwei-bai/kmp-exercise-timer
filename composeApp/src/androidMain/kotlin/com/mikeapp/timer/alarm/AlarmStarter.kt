package com.mikeapp.timer.alarm

import android.content.Context
import java.time.*

class AlarmStarter {
    fun setAlarm(context: Context, dateStr: String, timeStr: String) {
        val ldt = LocalDateTime.of(LocalDate.parse(dateStr), LocalTime.parse(timeStr))
        val instant: Instant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        AlarmManager().setAlarm(context, instant)
    }
}