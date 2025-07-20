//
//  NotificationDelegate.swift
//  iosApp
//
//  Created by Xiaojuan Gao on 11/7/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import UserNotifications
import UIKit

class NotificationDelegate: NSObject, UNUserNotificationCenterDelegate {
    // Called when a notification is delivered while app is in foreground
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
//        completionHandler([.banner, .sound]) // 👈 show alert + play sound
        completionHandler([.banner]) // 👈 show alert only
    }
}
