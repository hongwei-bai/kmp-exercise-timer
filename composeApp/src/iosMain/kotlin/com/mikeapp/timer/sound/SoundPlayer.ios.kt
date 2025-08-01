// iosMain/src/iosMain/kotlin/com/mikeapp/timer/sound/SoundPlayer.kt
package com.mikeapp.timer.sound

import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.alarm.toFileName

actual object SoundPlayer {
    var delegatePlaySound: ((String) -> Unit)? = null
    var delegateStopSound: (() -> Unit)? = null

    actual suspend fun playSound(sound: AlarmSound) {
        delegatePlaySound?.invoke(sound.toFileName())
    }

    actual suspend fun stopSound() {
        delegateStopSound?.invoke()
    }
}
