import ComposeApp // the Kotlin framework
import Foundation
import AVFoundation

class SwiftGreeter {
    private var player: AVAudioPlayer?
    
    func greet(withName name: String) -> String {
        let sound = "alarm_996.wav"

        guard let url = Bundle.main.url(forResource: sound, withExtension: nil) else {
            print("🔴 Sound file \(sound) not found")
            return ""
        }

        do {
            player = try AVAudioPlayer(contentsOf: url)
            player?.prepareToPlay()
            player?.play()
            print("▶️ Playing: \(sound)")
        } catch {
            print("❌ Failed to play sound: \(error.localizedDescription)")
        }
        return "Hi \(name) from Swift"
    }
}
