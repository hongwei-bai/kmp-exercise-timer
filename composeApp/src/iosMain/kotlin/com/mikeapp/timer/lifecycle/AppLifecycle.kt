package com.mikeapp.timer.lifecycle

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.darwin.NSObject

private var retainedObserver: NSObject? = null

actual class AppLifecycle {
    @OptIn(ExperimentalForeignApi::class)
    actual fun observeLifecycle(
        onEnterForeground: () -> Unit,
        onEnterBackground: () -> Unit
    ) {
        val observer = object : NSObject() {
            @ObjCAction
            fun handleEnterForeground() {
                println("🔄 AppLifecycle: enter foreground")
                onEnterForeground()
            }

            @ObjCAction
            fun handleEnterBackground() {
                println("🔄 AppLifecycle: enter background")
                onEnterBackground()
            }
        }

        val center = NSNotificationCenter.defaultCenter
        center.addObserver(
            observer,
            selector = NSSelectorFromString("handleEnterForeground"),
            name = UIApplicationWillEnterForegroundNotification,
            `object` = null
        )
        center.addObserver(
            observer,
            selector = NSSelectorFromString("handleEnterBackground"),
            name = UIApplicationDidEnterBackgroundNotification,
            `object` = null
        )

        retainedObserver = observer

        // ✅ First-time launch trigger
        println("🚀 AppLifecycle: first-time foreground trigger")
        onEnterForeground()
    }
}