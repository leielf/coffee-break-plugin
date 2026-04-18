package org.example.coffeebreakplugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The CoffeeBreakStartupActivity class initializes the Coffee Break plugin when a project is opened in IntelliJ.
 *
 * Implements the {@link ProjectActivity} interface to execute startup logic when the project becomes active.
 * Responsibilities include resetting activity trackers, attaching event listeners, and starting a timer
 * to monitor user activity and trigger reminders.
 */
public class CoffeeBreakStartupActivity implements ProjectActivity {

    /**
     * Executes the startup activity for the Coffee Break plugin.
     *
     * This method is called when a project is launched and performs the following:
     * - Resets the activity tracker to clear any previous session data.
     * - Attaches the typing tracker to monitor user edits in the active project.
     * - Starts the Coffee Break timer, which periodically checks user activity and sends notifications.
     *
     * @param project      The IntelliJ project instance being started.
     * @param continuation A {@link Continuation} used for asynchronous workflows; unused here.
     * @return Always returns {@code null} as no asynchronous operations are performed.
     */
    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super kotlin.Unit> continuation) {
        System.out.println("CoffeeBreak running");
        ActivityTracker.reset();
        TypingTracker.attach(project);
        CoffeeBreakTimer.start(project);

        Notifications.Bus.notify(
                new Notification(
                        "CoffeeBreak",
                        "Plugin Connected",
                        "Coffee Break plugin is running!",
                        NotificationType.INFORMATION
                ),
                project
        );
        return null;
    }
}