package com.mikeapp.timer.sound

import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.alarm.toFileName
import java.io.File
import javax.sound.sampled.AudioSystem

actual object SoundPlayer {
    private var clip: javax.sound.sampled.Clip? = null

    actual fun playSound(sound: AlarmSound) {
        val resource = this::class.java.getResource("/" + sound.toFileName()) ?: return
        val audioInputStream = AudioSystem.getAudioInputStream(File(resource.toURI()))
        clip = AudioSystem.getClip()
        clip?.open(audioInputStream)
        clip?.start()
    }

    actual fun stopSound() {
        clip?.stop()
        clip?.close()
        clip = null
    }
}