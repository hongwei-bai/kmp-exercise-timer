import SwiftUI
import ComposeApp
import UIKit

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoinIos()
        
        NativeGreeter.shared.delegate = { name in
            let greeter = SwiftGreeter()
            let message = greeter.greet(withName: name)
            print("play message: " + message)
            return message
//            SwiftGreeter.greet(withName: name)
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
