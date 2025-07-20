// iosMain/src/iosMain/kotlin/com/mikeapp/timer/sound/SoundPlayer.kt
package com.mikeapp.timer.sound

import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.alarm.toFileName

actual object SoundPlayer {
    actual fun playSound(sound: AlarmSound) {
        SoundPlayer.playSound(sound.toFileName())
    }

    actual fun stopSound() {
        SoundPlayer.stopSound()
    }
}
