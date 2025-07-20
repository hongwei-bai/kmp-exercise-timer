package com.mikeapp.timer.sound

import java.io.File
import javax.sound.sampled.AudioSystem

actual object SoundPlayer {
    private var clip: javax.sound.sampled.Clip? = null

    actual fun playSound(soundFileName: String) {
        val resource = this::class.java.getResource("/$soundFileName") ?: return
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