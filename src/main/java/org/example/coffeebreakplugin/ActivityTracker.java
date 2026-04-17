package org.example.coffeebreakplugin;

public class ActivityTracker {
    public static int edits = 0;
    public static long startTime = System.currentTimeMillis();

    public static void reset() {
        edits = 0;
        startTime = System.currentTimeMillis();
    }
}
