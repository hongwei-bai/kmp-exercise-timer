package com.mikeapp.timer.sound

import android.content.Context
import android.media.MediaPlayer
import com.mikeapp.timer.R
import com.mikeapp.timer.alarm.AlarmSound

actual object SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual fun playSound(sound: AlarmSound) {
        val koin = org.koin.core.context.GlobalContext.getOrNull()
        if (koin != null) {
            val context: Context = koin.get()

            val resId = when (sound) {
                AlarmSound.Alarm_996 -> R.raw.alarm_996
                AlarmSound.Alarm_buzzer_992 -> R.raw.alarm_buzzer_992
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