package com.mikeapp.timer.sound

import com.mikeapp.timer.alarm.AlarmSound

expect object SoundPlayer {
    fun playSound(sound: AlarmSound)

    fun stopSound()
}