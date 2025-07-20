// iosMain/src/iosMain/kotlin/com/mikeapp/timer/sound/SoundPlayer.kt
package com.mikeapp.timer.sound

actual object SoundPlayer {
    actual fun playSound(soundFileName: String) {
        SoundPlayer.playSound(soundFileName)
    }

    actual fun stopSound() {
        SoundPlayer.stopSound()
    }
}
