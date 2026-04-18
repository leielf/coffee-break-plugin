package org.example.coffeebreakplugin;

import com.intellij.openapi.project.Project;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The `CoffeeBreakTimer` class manages periodic checks on user activity and triggers break notifications
 * when predefined conditions are met.
 *
 * It schedules a timer to monitor user edits and activity duration, ensuring timely breaks
 * to enhance productivity and maintain healthy work habits.
 */
public class CoffeeBreakTimer {

    private static final long CHECK_INTERVAL_MS = 10 * 60 * 1000; // For 10 minutes

    public static void start(Project project) {
        Timer timer = new Timer(true);

        // Schedule a task to run every 10 minutes
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Calculate the number of minutes since the last reset
                long minutes =
                        (System.currentTimeMillis() - ActivityTracker.startTime) / 60000;

                if (minutes >= 50 || ActivityTracker.edits >= 30000) {
                    System.out.println("🔥 TRIGGER FIRED");
                    String message = AIClient.getBreakMessage(
                            ActivityTracker.edits,
                            minutes
                    );

                    CoffeeBreakNotification.show(project, message);
                    // Reset the tracker for a fresh activity session
                    ActivityTracker.reset();
                }
            }
        }, 0, CHECK_INTERVAL_MS);
    }
}