package org.example.coffeebreakplugin;

import com.intellij.openapi.project.Project;

import java.util.Timer;
import java.util.TimerTask;

public class CoffeeBreakTimer {

    public static void start(Project project) {
        Timer timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                long minutes =
                        (System.currentTimeMillis() - ActivityTracker.startTime) / 60000;

                if (minutes >= 1 || ActivityTracker.edits > 500) {
                    System.out.println("🔥 TRIGGER FIRED");
                    String message = AIClient.getBreakMessage(
                            ActivityTracker.edits,
                            minutes
                    );

                    CoffeeBreakNotification.show(project, message);
                    ActivityTracker.reset();
                }
            }
        }, 0, 1000);
    }
}