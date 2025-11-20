package com.example.sales.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility for parsing various date formats from CSV files.
 * Supports: M/d/yyyy H:mm, yyyy-MM-dd, M/d/yyyy
 */
public final class DateUtils {
    
    private static final DateTimeFormatter F1 = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
    private static final DateTimeFormatter F2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter F3 = DateTimeFormatter.ofPattern("M/d/yyyy");

    private DateUtils() {}

    /**
     * Parses date string using multiple formats. Returns null if parsing fails.
     */
    public static LocalDate parseToLocalDate(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        
        raw = raw.trim();
        
        // Try each formatter
        for (DateTimeFormatter formatter : new DateTimeFormatter[]{F1, F2, F3}) {
            try {
                return LocalDate.parse(raw, formatter);
            } catch (DateTimeParseException ignored) {}
        }
        
        // Fallback: try first 10 chars as yyyy-MM-dd
        if (raw.length() >= 10) {
            try {
                return LocalDate.parse(raw.substring(0, 10), F2);
            } catch (Exception ignored) {}
        }
        
        return null;
    }
}
