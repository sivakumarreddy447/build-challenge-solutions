package com.example.sales;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/** Integration tests for App - validates end-to-end application flow. */
@DisplayName("App Integration Tests")
class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Test running app with test data file
    @Test
    @DisplayName("Should run main with test data successfully")
    void testMainWithTestData() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        assertDoesNotThrow(() -> App.main(args));

        String output = outContent.toString();

        assertTrue(output.contains("Total Revenue"));
        assertTrue(output.contains("Total Orders"));
        assertTrue(output.contains("Total Quantity"));
        assertTrue(output.contains("Average Order Value"));
        assertTrue(output.contains("Revenue by Year"));
        assertTrue(output.contains("Revenue by Region"));
        assertTrue(output.contains("Top 10 Products"));
        assertTrue(output.contains("Top 10 Customers"));
        assertTrue(output.contains("Orders by Status"));
        assertTrue(output.contains("Orders by DealSize"));
        assertTrue(output.contains("Partition High Value Orders"));
        assertTrue(output.contains("Monthly Revenue"));
        assertTrue(output.contains("Data Quality Checks"));
    }

    // Test running app with default CSV path
    @Test
    @DisplayName("Should run main with default CSV path")
    void testMainWithDefaultPath() {
        String[] args = {};

        assertDoesNotThrow(() -> App.main(args));

        String output = outContent.toString();
        assertFalse(output.isEmpty());
        assertTrue(output.contains("Total Revenue"));
    }

    // Test app throws exception for non-existent file
    @Test
    @DisplayName("Should handle non-existent file gracefully")
    void testMainWithNonExistentFile() {
        String[] args = {"non_existent_file.csv"};

        assertThrows(RuntimeException.class, () -> App.main(args));
    }

    // Test currency values are formatted
    @Test
    @DisplayName("Should display formatted currency values")
    void testCurrencyFormatting() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        App.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("$"));
    }

    // Test all analytics sections are displayed
    @Test
    @DisplayName("Should display all analytical results")
    void testAllAnalyticsDisplayed() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        App.main(args);

        String output = outContent.toString();

        assertTrue(output.contains("2023"));
        assertTrue(output.contains("2024"));
        assertTrue(output.contains("NA"));
        assertTrue(output.contains("Shipped"));
        assertTrue(output.contains("Small"));
        assertTrue(output.contains("Medium"));
        assertTrue(output.contains("Large"));
    }

    // Test partition results are displayed
    @Test
    @DisplayName("Should display partition results")
    void testPartitionDisplay() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        App.main(args);

        String output = outContent.toString();

        assertTrue(output.contains("Partition High Value Orders"));
        assertTrue(output.contains("500"));
        assertTrue(output.contains("true"));
        assertTrue(output.contains("false"));
    }

    // Test monthly revenue format YYYY-MM
    @Test
    @DisplayName("Should display monthly revenue in YYYY-MM format")
    void testMonthlyRevenueFormat() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        App.main(args);

        String output = outContent.toString();
        assertTrue(output.matches("(?s).*\\d{4}-\\d{2}.*"));
    }

    // Test data quality metrics are displayed
    @Test
    @DisplayName("Should display data quality metrics")
    void testDataQualityMetrics() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        App.main(args);

        String output = outContent.toString();

        assertTrue(output.contains("rows"));
        assertTrue(output.contains("missingOrderDate"));
        assertTrue(output.contains("missingSalesValue"));
        assertTrue(output.contains("duplicateOrderNumbers"));
    }

    // Test processing large CSV file efficiently
    @Test
    @DisplayName("Should process large CSV file efficiently")
    void testLargeCsvProcessing() {
        String[] args = {"data/sales_data_sample.csv"};

        long startTime = System.currentTimeMillis();
        assertDoesNotThrow(() -> App.main(args));
        long endTime = System.currentTimeMillis();

        assertTrue(endTime - startTime < 5000);

        String output = outContent.toString();
        assertFalse(output.isEmpty());
    }

    // Test handling empty CSV file
    @Test
    @DisplayName("Should handle empty CSV file")
    void testEmptyCsvFile() {
        String[] args = {"src/test/resources/empty_sales_data.csv"};

        assertDoesNotThrow(() -> App.main(args));

        String output = outContent.toString();
        assertTrue(output.contains("Total Revenue"));
    }

    // Test functional programming patterns work
    @Test
    @DisplayName("Should use functional programming patterns")
    void testFunctionalProgramming() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        assertDoesNotThrow(() -> App.main(args));

        String output = outContent.toString();
        assertTrue(output.contains("==="));
        assertTrue(output.contains(":"));
    }

    // Test stream supplier reusability
    @Test
    @DisplayName("Should demonstrate stream reusability")
    void testStreamReusability() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        assertDoesNotThrow(() -> App.main(args));

        String output = outContent.toString();

        long sectionCount = output.lines()
                .filter(line -> line.contains("==="))
                .count();

        assertTrue(sectionCount >= 10);
    }

    // Test handling CSV with invalid data
    @Test
    @DisplayName("Should handle CSV with invalid data")
    void testInvalidDataHandling() {
        String[] args = {"src/test/resources/invalid_sales_data.csv"};

        assertDoesNotThrow(() -> App.main(args));

        String output = outContent.toString();
        assertFalse(output.isEmpty());
    }

    // Test number formatting
    @Test
    @DisplayName("Should format numbers correctly")
    void testNumberFormatting() {
        String[] args = {"src/test/resources/test_sales_data.csv"};

        App.main(args);

        String output = outContent.toString();
        assertTrue(output.matches("(?s).*\\$[\\d,]+\\.\\d{2}.*"));
    }
}
