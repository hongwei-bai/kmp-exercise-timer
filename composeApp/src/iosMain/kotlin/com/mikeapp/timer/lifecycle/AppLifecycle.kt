package com.mikeapp.timer.lifecycle

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.darwin.NSObject

// 🔐 Retain this so it doesn't get garbage collected
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
                onEnterForeground()
            }

            @ObjCAction
            fun handleEnterBackground() {
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

        // ✅ This prevents GC cleanup
        retainedObserver = observer
    }
}