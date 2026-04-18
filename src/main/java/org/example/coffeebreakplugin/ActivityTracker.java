package org.example.coffeebreakplugin;

/**
 * Tracks user activity during an editing session.
 *
 * The `ActivityTracker` monitors the number of edits performed and the start time of
 * the current session. This information is used for determining when to remind the user
 * to take a break. The tracker is reset periodically to start a new activity session.
 */
public class ActivityTracker {
    public static int edits = 0;
    public static long startTime = System.currentTimeMillis();

    public static void reset() {
        edits = 0;
        startTime = System.currentTimeMillis();
    }

    public static void incrementEdits() {
        edits++;
        System.out.println("New edit! edits: " + edits);
    }
}
