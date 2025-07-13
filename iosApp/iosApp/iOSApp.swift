import SwiftUI
import ComposeApp
import UserNotifications

// ✅ Global strong reference
let notificationDelegate = NotificationDelegate()

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoinIos()
        UNUserNotificationCenter.current().delegate = notificationDelegate
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
