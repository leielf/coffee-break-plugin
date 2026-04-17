package org.example.coffeebreakplugin;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ide.BrowserUtil;
import org.jetbrains.annotations.NotNull;

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

    private static void openCoffeeBreak() {
        BrowserUtil.browse("https://en.wikipedia.org/wiki/Special:Random");
    }
}