package com.example.sales.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for DateUtils - validates date parsing for multiple formats. */
@DisplayName("DateUtils Tests")
class DateUtilsTest {

    // Test parsing date with time format (M/d/yyyy H:mm)
    @Test
    @DisplayName("Should parse date in M/d/yyyy H:mm format")
    void testParseDate_Format1() {
        LocalDate result = DateUtils.parseToLocalDate("2/24/2003 0:00");
        assertNotNull(result);
        assertEquals(2003, result.getYear());
        assertEquals(2, result.getMonthValue());
        assertEquals(24, result.getDayOfMonth());
    }

    // Test parsing ISO date format (yyyy-MM-dd)
    @Test
    @DisplayName("Should parse date in yyyy-MM-dd format")
    void testParseDate_Format2() {
        LocalDate result = DateUtils.parseToLocalDate("2023-06-15");
        assertNotNull(result);
        assertEquals(2023, result.getYear());
        assertEquals(6, result.getMonthValue());
        assertEquals(15, result.getDayOfMonth());
    }

    // Test parsing date without time (M/d/yyyy)
    @Test
    @DisplayName("Should parse date in M/d/yyyy format")
    void testParseDate_Format3() {
        LocalDate result = DateUtils.parseToLocalDate("12/31/2024");
        assertNotNull(result);
        assertEquals(2024, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(31, result.getDayOfMonth());
    }

    // Test parsing with single digit month and day
    @Test
    @DisplayName("Should handle single digit month and day")
    void testParseDate_SingleDigits() {
        LocalDate result = DateUtils.parseToLocalDate("1/5/2023 0:00");
        assertNotNull(result);
        assertEquals(2023, result.getYear());
        assertEquals(1, result.getMonthValue());
        assertEquals(5, result.getDayOfMonth());
    }

    // Test null input returns null
    @Test
    @DisplayName("Should return null for null input")
    void testParseDate_NullInput() {
        LocalDate result = DateUtils.parseToLocalDate(null);
        assertNull(result);
    }

    // Test blank input returns null
    @Test
    @DisplayName("Should return null for blank input")
    void testParseDate_BlankInput() {
        LocalDate result = DateUtils.parseToLocalDate("   ");
        assertNull(result);
    }

    // Test empty string returns null
    @Test
    @DisplayName("Should return null for empty input")
    void testParseDate_EmptyInput() {
        LocalDate result = DateUtils.parseToLocalDate("");
        assertNull(result);
    }

    // Test invalid format returns null
    @Test
    @DisplayName("Should return null for invalid date format")
    void testParseDate_InvalidFormat() {
        LocalDate result = DateUtils.parseToLocalDate("invalid-date");
        assertNull(result);
    }

    // Test date with leading/trailing whitespace
    @Test
    @DisplayName("Should handle date with trailing whitespace")
    void testParseDate_WithWhitespace() {
        LocalDate result = DateUtils.parseToLocalDate("  2/24/2003 0:00  ");
        assertNotNull(result);
        assertEquals(2003, result.getYear());
        assertEquals(2, result.getMonthValue());
        assertEquals(24, result.getDayOfMonth());
    }

    // Test fallback parsing for date prefix in longer string
    @Test
    @DisplayName("Should parse date with prefix yyyy-MM-dd in longer string")
    void testParseDate_PrefixFallback() {
        LocalDate result = DateUtils.parseToLocalDate("2023-06-15 extra text");
        assertNotNull(result);
        assertEquals(2023, result.getYear());
        assertEquals(6, result.getMonthValue());
        assertEquals(15, result.getDayOfMonth());
    }
}
