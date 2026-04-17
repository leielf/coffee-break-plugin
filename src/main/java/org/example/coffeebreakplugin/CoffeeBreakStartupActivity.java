package org.example.coffeebreakplugin;

import com.github.weisj.jsvg.geometry.size.Unit;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.startup.StartupActivity;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoffeeBreakStartupActivity implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super kotlin.Unit> continuation) {
        System.out.println("CoffeeBreak running");
        ActivityTracker.reset();
        TypingTracker.attach(project);
        CoffeeBreakTimer.start(project);

        Notifications.Bus.notify(
                new Notification(
                        "CoffeeBreak",
                        "Plugin Debug",
                        "Coffee Break plugin is running!",
                        NotificationType.INFORMATION
                ),
                project
        );
        return null;
    }
}