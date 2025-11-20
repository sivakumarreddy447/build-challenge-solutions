package com.example.sales.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for SalesRecord - validates record creation and revenue calculation. */
@DisplayName("SalesRecord Tests")
class SalesRecordTest {

    // Test creating record and verifying all fields are set correctly
    @Test
    @DisplayName("Should create SalesRecord with all fields")
    void testCreateSalesRecord() {
        LocalDate date = LocalDate.of(2023, 6, 15);
        SalesRecord record = new SalesRecord(
                "10001",
                date,
                "Test Customer",
                "Shipped",
                10,
                100.0,
                1000.0,
                "Motorcycles",
                "M001",
                "USA",
                "New York",
                "NA",
                "Small"
        );

        assertEquals("10001", record.orderNumber());
        assertEquals(date, record.orderDate());
        assertEquals("Test Customer", record.customerName());
        assertEquals("Shipped", record.status());
        assertEquals(10, record.quantityOrdered());
        assertEquals(100.0, record.priceEach());
        assertEquals(1000.0, record.sales());
        assertEquals("Motorcycles", record.productLine());
        assertEquals("M001", record.productCode());
        assertEquals("USA", record.country());
        assertEquals("New York", record.city());
        assertEquals("NA", record.territory());
        assertEquals("Small", record.dealSize());
    }

    // Test revenue uses sales field when positive
    @Test
    @DisplayName("Should calculate revenue from sales when sales is positive")
    void testRevenue_FromSales() {
        SalesRecord record = new SalesRecord(
                "10001", LocalDate.now(), "Customer", "Shipped",
                10, 100.0, 1000.0, "Product", "P001",
                "USA", "NYC", "NA", "Small"
        );

        assertEquals(1000.0, record.revenue());
    }

    // Test revenue calculates from quantity * price when sales is zero
    @Test
    @DisplayName("Should calculate revenue from quantity * price when sales is zero")
    void testRevenue_FromQuantityAndPrice() {
        SalesRecord record = new SalesRecord(
                "10001", LocalDate.now(), "Customer", "Shipped",
                10, 100.0, 0.0, "Product", "P001",
                "USA", "NYC", "NA", "Small"
        );

        assertEquals(1000.0, record.revenue());
    }

    // Test revenue prioritizes sales field over calculated value
    @Test
    @DisplayName("Should use sales value when both sales and calculated values exist")
    void testRevenue_PrioritizeSales() {
        SalesRecord record = new SalesRecord(
                "10001", LocalDate.now(), "Customer", "Shipped",
                10, 100.0, 1500.0, "Product", "P001",
                "USA", "NYC", "NA", "Small"
        );

        assertEquals(1500.0, record.revenue());
    }

    // Test revenue with zero values
    @Test
    @DisplayName("Should handle zero quantity and price")
    void testRevenue_ZeroValues() {
        SalesRecord record = new SalesRecord(
                "10001", LocalDate.now(), "Customer", "Cancelled",
                0, 0.0, 0.0, "Product", "P001",
                "USA", "NYC", "NA", "Small"
        );

        assertEquals(0.0, record.revenue());
    }

    // Test record handles null date
    @Test
    @DisplayName("Should handle null date")
    void testSalesRecord_NullDate() {
        SalesRecord record = new SalesRecord(
                "10001", null, "Customer", "Shipped",
                10, 100.0, 1000.0, "Product", "P001",
                "USA", "NYC", "NA", "Small"
        );

        assertNull(record.orderDate());
        assertEquals(1000.0, record.revenue());
    }

    // Test record handles empty strings
    @Test
    @DisplayName("Should handle empty strings")
    void testSalesRecord_EmptyStrings() {
        SalesRecord record = new SalesRecord(
                "", LocalDate.now(), "", "",
                0, 0.0, 0.0, "", "",
                "", "", "", ""
        );

        assertEquals("", record.orderNumber());
        assertEquals("", record.customerName());
        assertEquals(0.0, record.revenue());
    }
}
