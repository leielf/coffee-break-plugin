package org.example.coffeebreakplugin;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;

/**
 * Handles displaying notifications for the Coffee Break plugin.
 *
 * This class creates and displays notifications using IntelliJ's notification system,
 * informing the user about the need for a coffee break.
 */
public class CoffeeBreakNotification {

    public static void show(Project project, String message) {

        NotificationGroup group =
                NotificationGroupManager.getInstance()
                        .getNotificationGroup("CoffeeBreak");

        Notification notification =
                group.createNotification(
                        "☕ Coffee Break",
                        message,
                        NotificationType.INFORMATION
                );

        Notifications.Bus.notify(notification, project);
    }
}