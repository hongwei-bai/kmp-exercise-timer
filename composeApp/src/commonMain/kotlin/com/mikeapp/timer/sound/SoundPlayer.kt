package com.mikeapp.timer.sound

expect object SoundPlayer {
    fun playSound(soundFileName: String)

    fun stopSound()
}