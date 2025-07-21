package com.mikeapp.timer.sound

import com.mikeapp.timer.alarm.AlarmSound

expect object SoundPlayer {
    suspend fun playSound(sound: AlarmSound)

    suspend fun stopSound()
}