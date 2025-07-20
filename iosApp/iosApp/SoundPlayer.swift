import Foundation
import AVFoundation

@objc class SoundPlayer: NSObject {
    static var audioPlayer: AVAudioPlayer?

    @objc static func playSound(_ fileName: String) {
        guard let url = Bundle.main.url(forResource: fileName, withExtension: nil) else {
            print("⚠️ Sound file not found: \(fileName)")
            return
        }

        do {
            audioPlayer = try AVAudioPlayer(contentsOf: url)
            audioPlayer?.prepareToPlay()
            audioPlayer?.play()
        } catch {
            print("⚠️ Failed to play sound: \(error)")
        }
    }

    @objc static func stopSound() {
        audioPlayer?.stop()
        audioPlayer = nil
    }
}
