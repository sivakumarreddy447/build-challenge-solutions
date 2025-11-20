package com.example.sales.output;

import java.util.Map;
import java.util.Objects;

/**
 * Simple console reporter for displaying analytics results.
 * Uses System.out/err without logging frameworks.
 */
public final class ConsoleReporter {

    /** Prints informational message. */
    public void info(String msg) {
        System.out.println(msg);
    }

    /** Prints titled section with map data. */
    public void info(String title, Map<?,?> map) {
        System.out.println("=== " + Objects.toString(title, "") + " ===");
        if (map == null || map.isEmpty()) {
            System.out.println("  (no data)");
            return;
        }
        map.forEach((k, v) -> System.out.println("  " + k + " : " + v));
    }

    /** Prints titled section with single value. */
    public void info(String title, Object value) {
        System.out.println("=== " + Objects.toString(title, "") + " ===");
        System.out.println("  " + Objects.toString(value, ""));
    }

    /** Prints warning message. */
    public void warn(String msg) {
        System.out.println("WARNING: " + msg);
    }

    /** Prints error message to stderr. */
    public void error(String msg) {
        System.err.println("ERROR: " + msg);
    }

    /** Prints error message with stack trace to stderr. */
    public void error(String msg, Throwable t) {
        System.err.println("ERROR: " + msg);
        if (t != null) {
            t.printStackTrace(System.err);
        }
    }
}
