package com.example.sales.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for ConsoleReporter - validates output formatting and error handling. */
@DisplayName("ConsoleReporter Tests")
class ConsoleReporterTest {

    private ConsoleReporter reporter;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        reporter = new ConsoleReporter();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Test printing simple message
    @Test
    @DisplayName("Should print info message")
    void testInfo() {
        reporter.info("Test message");
        String output = outContent.toString();
        assertTrue(output.contains("Test message"));
    }

    // Test printing map with title
    @Test
    @DisplayName("Should print info with map")
    void testInfoWithMap() {
        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("key1", 100);
        data.put("key2", 200);

        reporter.info("Test Title", data);

        String output = outContent.toString();
        assertTrue(output.contains("=== Test Title ==="));
        assertTrue(output.contains("key1 : 100"));
        assertTrue(output.contains("key2 : 200"));
    }

    // Test printing empty map shows "(no data)"
    @Test
    @DisplayName("Should print info with empty map")
    void testInfoWithEmptyMap() {
        Map<String, Integer> data = new LinkedHashMap<>();

        reporter.info("Test Title", data);

        String output = outContent.toString();
        assertTrue(output.contains("=== Test Title ==="));
        assertTrue(output.contains("(no data)"));
    }

    // Test null map shows "(no data)"
    @Test
    @DisplayName("Should print info with null map")
    void testInfoWithNullMap() {
        reporter.info("Test Title", (Map<?, ?>) null);

        String output = outContent.toString();
        assertTrue(output.contains("=== Test Title ==="));
        assertTrue(output.contains("(no data)"));
    }

    // Test printing single value
    @Test
    @DisplayName("Should print info with object value")
    void testInfoWithObject() {
        reporter.info("Test Title", 12345);

        String output = outContent.toString();
        assertTrue(output.contains("=== Test Title ==="));
        assertTrue(output.contains("12345"));
    }

    // Test printing null value
    @Test
    @DisplayName("Should print info with null object value")
    void testInfoWithNullObject() {
        reporter.info("Test Title", (Object) null);

        String output = outContent.toString();
        assertTrue(output.contains("=== Test Title ==="));
        assertFalse(output.contains("null") && output.lines().count() > 1);
    }

    // Test warning message format
    @Test
    @DisplayName("Should print warning message")
    void testWarn() {
        reporter.warn("Warning message");

        String output = outContent.toString();
        assertTrue(output.contains("WARNING: Warning message"));
    }

    // Test error goes to stderr
    @Test
    @DisplayName("Should print error message to stderr")
    void testError() {
        reporter.error("Error message");

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("ERROR: Error message"));
    }

    // Test error with exception
    @Test
    @DisplayName("Should print error with throwable")
    void testErrorWithThrowable() {
        Exception e = new RuntimeException("Test exception");
        reporter.error("Error occurred", e);

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("ERROR: Error occurred"));
        assertTrue(errorOutput.contains("RuntimeException"));
        assertTrue(errorOutput.contains("Test exception"));
    }

    // Test error with null exception
    @Test
    @DisplayName("Should handle null throwable")
    void testErrorWithNullThrowable() {
        reporter.error("Error occurred", null);

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("ERROR: Error occurred"));
    }

    // Test printing multiple map entries
    @Test
    @DisplayName("Should format multiple map entries correctly")
    void testMultipleMapEntries() {
        Map<String, Double> data = new LinkedHashMap<>();
        data.put("Revenue", 10000.50);
        data.put("Cost", 5000.25);
        data.put("Profit", 5000.25);

        reporter.info("Financial Summary", data);

        String output = outContent.toString();
        assertTrue(output.contains("Revenue : 10000.5"));
        assertTrue(output.contains("Cost : 5000.25"));
        assertTrue(output.contains("Profit : 5000.25"));
    }

    // Test special characters in output
    @Test
    @DisplayName("Should handle special characters in messages")
    void testSpecialCharacters() {
        reporter.info("Test with special chars: @#$%^&*()");

        String output = outContent.toString();
        assertTrue(output.contains("@#$%^&*()"));
    }

    // Test empty string message
    @Test
    @DisplayName("Should handle empty string messages")
    void testEmptyString() {
        reporter.info("");

        String output = outContent.toString();
        assertNotNull(output);
    }

    // Test map with null keys/values
    @Test
    @DisplayName("Should handle map with null keys and values")
    void testMapWithNulls() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("key1", null);
        data.put(null, "value");

        reporter.info("Test", data);

        String output = outContent.toString();
        assertTrue(output.contains("=== Test ==="));
        assertNotNull(output);
    }

    // Test long messages
    @Test
    @DisplayName("Should handle long messages")
    void testLongMessage() {
        String longMessage = "A".repeat(1000);
        reporter.info(longMessage);

        String output = outContent.toString();
        assertTrue(output.contains(longMessage));
    }

    // Test map with different value types
    @Test
    @DisplayName("Should handle map with various value types")
    void testMapWithVariousTypes() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("string", "value");
        data.put("integer", 123);
        data.put("double", 45.67);
        data.put("boolean", true);

        reporter.info("Mixed Types", data);

        String output = outContent.toString();
        assertTrue(output.contains("string : value"));
        assertTrue(output.contains("integer : 123"));
        assertTrue(output.contains("double : 45.67"));
        assertTrue(output.contains("boolean : true"));
    }
}
