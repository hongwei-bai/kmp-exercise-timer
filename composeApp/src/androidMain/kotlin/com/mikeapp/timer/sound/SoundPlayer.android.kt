package com.mikeapp.timer.sound

import android.content.Context
import android.media.MediaPlayer
import com.mikeapp.timer.R
import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.alarm.toFileName

actual object SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual fun playSound(soundFileName: String) {
        val koin = org.koin.core.context.GlobalContext.getOrNull()
        if (koin != null) {
            val context: Context = koin.get()

            val resId = when (soundFileName) {
                AlarmSound.Alarm_996.toFileName() -> R.raw.alarm_996
                AlarmSound.Alarm_buzzer_992.toFileName() -> R.raw.alarm_buzzer_992
                else -> R.raw.alarm_996
            }

            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, resId)
            mediaPlayer?.start()
        }
    }

    actual fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}