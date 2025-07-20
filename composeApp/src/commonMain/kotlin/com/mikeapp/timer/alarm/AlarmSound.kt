package com.mikeapp.timer.alarm

enum class AlarmSound {
    Alarm_996,
    Alarm_buzzer_992;
}

fun AlarmSound.toFileName(): String = when (this) {
    AlarmSound.Alarm_996 -> "alarm_996.wav"
    AlarmSound.Alarm_buzzer_992 -> "alarm_buzzer_992.wav"
}