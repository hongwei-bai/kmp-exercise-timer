import Foundation
import AVFoundation
import ComposeApp

class SwiftSoundPlayer {
    private static var audioPlayer: AVAudioPlayer?

    static func playSound(withName soundFileName: String) {
        guard let url = Bundle.main.url(forResource: soundFileName, withExtension: nil) else {
            print("❌ Sound file not found: \(soundFileName)")
            return
        }

        do {
            audioPlayer = try AVAudioPlayer(contentsOf: url)
            audioPlayer?.prepareToPlay()
            audioPlayer?.play()
            print("🔊 Playing: \(soundFileName)")
        } catch {
            print("❌ Error playing sound: \(error)")
        }
    }

    static func stopSound() {
        if let player = audioPlayer, player.isPlaying {
            player.stop()
            print("🔇 Sound stopped")
        }
    }
}