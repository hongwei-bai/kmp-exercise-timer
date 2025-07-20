import SwiftUI
import ComposeApp
import UserNotifications
import UIKit

// ✅ Global strong reference
let notificationDelegate = NotificationDelegate()

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoinIos()
        UNUserNotificationCenter.current().delegate = notificationDelegate
        SoundPlayer.shared.delegatePlaySound = { soundFileName in
            SwiftSoundPlayer.playSound(withName: soundFileName)
        }
        SoundPlayer.shared.delegateStopSound = {
            SwiftSoundPlayer.stopSound()
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
