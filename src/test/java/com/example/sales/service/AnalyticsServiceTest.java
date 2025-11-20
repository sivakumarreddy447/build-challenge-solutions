package com.example.sales.service;

import com.example.sales.model.SalesRecord;
import com.example.sales.reader.CsvSalesReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for AnalyticsService - validates all analytics methods and stream operations. */
@DisplayName("AnalyticsService Tests")
class AnalyticsServiceTest {

    private AnalyticsService service;
    private Supplier<Stream<SalesRecord>> testDataSupplier;

    @BeforeEach
    void setUp() {
        Path csvPath = Path.of("src/test/resources/test_sales_data.csv");
        testDataSupplier = CsvSalesReader.streamSupplier(csvPath);
        service = new AnalyticsService(testDataSupplier);
    }

    // Test total revenue calculation using mapToDouble + sum
    @Test
    @DisplayName("Should calculate total revenue correctly")
    void testTotalRevenue() {
        double totalRevenue = service.totalRevenue();
        assertEquals(12265.0, totalRevenue, 0.01);
    }

    // Test counting total number of orders
    @Test
    @DisplayName("Should count total orders correctly")
    void testTotalOrders() {
        long totalOrders = service.totalOrders();
        assertEquals(10, totalOrders);
    }

    // Test summing total quantity ordered
    @Test
    @DisplayName("Should calculate total quantity correctly")
    void testTotalQuantity() {
        long totalQuantity = service.totalQuantity();
        assertEquals(153, totalQuantity);
    }

    // Test calculating average order value
    @Test
    @DisplayName("Should calculate average order value correctly")
    void testAverageOrderValue() {
        double avgOrderValue = service.averageOrderValue();
        assertEquals(1226.5, avgOrderValue, 0.01);
    }

    // Test grouping revenue by year
    @Test
    @DisplayName("Should group revenue by year correctly")
    void testRevenueByYear() {
        Map<Integer, Double> revenueByYear = service.revenueByYear();

        assertNotNull(revenueByYear);
        assertTrue(revenueByYear.containsKey(2023));
        assertTrue(revenueByYear.containsKey(2024));
        assertEquals(4875.0, revenueByYear.get(2023), 0.01);
        assertEquals(7390.0, revenueByYear.get(2024), 0.01);
    }

    // Test grouping revenue by territory
    @Test
    @DisplayName("Should group revenue by region correctly")
    void testRevenueByRegion() {
        Map<String, Double> revenueByRegion = service.revenueByRegion();

        assertNotNull(revenueByRegion);
        assertTrue(revenueByRegion.containsKey("NA"));
        assertEquals(12265.0, revenueByRegion.get("NA"), 0.01);
    }

    // Test getting top N products sorted by revenue
    @Test
    @DisplayName("Should return top products by revenue")
    void testTopProducts() {
        LinkedHashMap<String, Double> topProducts = service.topProducts(5);

        assertNotNull(topProducts);
        assertFalse(topProducts.isEmpty());
        assertTrue(topProducts instanceof LinkedHashMap);

        List<String> productKeys = new ArrayList<>(topProducts.keySet());
        assertEquals("Motorcycles", productKeys.get(0));
        assertEquals(5125.0, topProducts.get("Motorcycles"), 0.01);
    }

    // Test limiting results to top N products
    @Test
    @DisplayName("Should limit top products to specified number")
    void testTopProducts_Limit() {
        LinkedHashMap<String, Double> top3 = service.topProducts(3);
        assertEquals(3, top3.size());
    }

    // Test getting top N customers sorted by revenue
    @Test
    @DisplayName("Should return top customers by revenue")
    void testTopCustomers() {
        LinkedHashMap<String, Double> topCustomers = service.topCustomers(10);

        assertNotNull(topCustomers);
        assertFalse(topCustomers.isEmpty());
        assertTrue(topCustomers.containsKey("Test Customer A"));
        assertEquals(5125.0, topCustomers.get("Test Customer A"), 0.01);
    }

    // Test limiting results to top N customers
    @Test
    @DisplayName("Should limit top customers to specified number")
    void testTopCustomers_Limit() {
        LinkedHashMap<String, Double> top5 = service.topCustomers(5);
        assertTrue(top5.size() <= 5);
    }

    // Test grouping orders by status
    @Test
    @DisplayName("Should group orders by status correctly")
    void testOrdersByStatus() {
        Map<String, Long> ordersByStatus = service.ordersByStatus();

        assertNotNull(ordersByStatus);
        assertTrue(ordersByStatus.containsKey("Shipped"));
        assertEquals(8, ordersByStatus.get("Shipped"));
        assertEquals(1, ordersByStatus.get("Cancelled"));
        assertEquals(1, ordersByStatus.get("On Hold"));
    }

    // Test grouping orders by deal size
    @Test
    @DisplayName("Should group orders by deal size correctly")
    void testOrdersByDealSize() {
        Map<String, Long> ordersByDealSize = service.ordersByDealSize();

        assertNotNull(ordersByDealSize);
        assertTrue(ordersByDealSize.containsKey("Small"));
        assertTrue(ordersByDealSize.containsKey("Medium"));
        assertTrue(ordersByDealSize.containsKey("Large"));
        assertEquals(4, ordersByDealSize.get("Small"));
        assertEquals(3, ordersByDealSize.get("Medium"));
        assertEquals(3, ordersByDealSize.get("Large"));
    }

    // Test partitioning orders by value threshold
    @Test
    @DisplayName("Should partition high value orders correctly")
    void testPartitionHighValue() {
        double threshold = 1500.0;
        Map<Boolean, Long> partition = service.partitionHighValue(threshold);

        assertNotNull(partition);
        assertTrue(partition.containsKey(true));
        assertTrue(partition.containsKey(false));
        assertEquals(1, partition.get(true));
        assertEquals(9, partition.get(false));
    }

    // Test partitioning with different thresholds
    @Test
    @DisplayName("Should partition with different threshold values")
    void testPartitionHighValue_DifferentThresholds() {
        Map<Boolean, Long> partition1000 = service.partitionHighValue(1000.0);
        assertTrue(partition1000.get(true) > 0);

        Map<Boolean, Long> partition0 = service.partitionHighValue(0.0);
        assertEquals(10, partition0.get(true));

        Map<Boolean, Long> partition10000 = service.partitionHighValue(10000.0);
        assertEquals(10, partition10000.get(false));
    }

    // Test grouping revenue by month
    @Test
    @DisplayName("Should calculate monthly revenue correctly")
    void testMonthlyRevenue() {
        Map<String, Double> monthlyRevenue = service.monthlyRevenue();

        assertNotNull(monthlyRevenue);
        assertFalse(monthlyRevenue.isEmpty());
        assertTrue(monthlyRevenue.keySet().stream().allMatch(key -> key.matches("\\d{4}-\\d{2}")));
        assertTrue(monthlyRevenue.containsKey("2023-01"));
        assertTrue(monthlyRevenue.containsKey("2024-06"));
    }

    // Test data quality checks
    @Test
    @DisplayName("Should perform data quality checks correctly")
    void testDataQualityChecks() {
        Map<String, Object> qualityChecks = service.dataQualityChecks();

        assertNotNull(qualityChecks);
        assertTrue(qualityChecks.containsKey("rows"));
        assertTrue(qualityChecks.containsKey("missingOrderDate"));
        assertTrue(qualityChecks.containsKey("missingSalesValue"));
        assertTrue(qualityChecks.containsKey("duplicateOrderNumbers"));

        assertEquals(10, qualityChecks.get("rows"));
        assertEquals(0L, qualityChecks.get("missingOrderDate"));
        assertEquals(0L, qualityChecks.get("missingSalesValue"));
        assertEquals(0L, qualityChecks.get("duplicateOrderNumbers"));
    }

    // Test handling empty dataset
    @Test
    @DisplayName("Should handle empty dataset gracefully")
    void testEmptyDataset() {
        Path emptyPath = Path.of("src/test/resources/empty_sales_data.csv");
        Supplier<Stream<SalesRecord>> emptySupplier = CsvSalesReader.streamSupplier(emptyPath);
        AnalyticsService emptyService = new AnalyticsService(emptySupplier);

        assertEquals(0.0, emptyService.totalRevenue());
        assertEquals(0, emptyService.totalOrders());
        assertEquals(0, emptyService.totalQuantity());
        assertEquals(0.0, emptyService.averageOrderValue());

        Map<Integer, Double> revenueByYear = emptyService.revenueByYear();
        assertTrue(revenueByYear.isEmpty());

        LinkedHashMap<String, Double> topProducts = emptyService.topProducts(5);
        assertTrue(topProducts.isEmpty());
    }

    // Test handling invalid data
    @Test
    @DisplayName("Should handle invalid data gracefully")
    void testInvalidData() {
        Path invalidPath = Path.of("src/test/resources/invalid_sales_data.csv");
        Supplier<Stream<SalesRecord>> invalidSupplier = CsvSalesReader.streamSupplier(invalidPath);
        AnalyticsService invalidService = new AnalyticsService(invalidSupplier);

        assertDoesNotThrow(() -> {
            invalidService.totalRevenue();
            invalidService.totalOrders();
            invalidService.revenueByYear();
            invalidService.dataQualityChecks();
        });
    }

    // Test stream supplier reusability
    @Test
    @DisplayName("Should reuse stream supplier multiple times")
    void testStreamSupplierReuse() {
        double revenue1 = service.totalRevenue();
        long orders = service.totalOrders();
        double revenue2 = service.totalRevenue();

        assertEquals(revenue1, revenue2);
        assertEquals(10, orders);
    }

    // Test with custom in-memory dataset
    @Test
    @DisplayName("Should handle custom dataset with lambda expressions")
    void testCustomDataset() {
        Supplier<Stream<SalesRecord>> customSupplier = () -> Stream.of(
                new SalesRecord("1", LocalDate.now(), "Customer A", "Shipped",
                        10, 100.0, 1000.0, "Product1", "P1", "USA", "NYC", "NA", "Small"),
                new SalesRecord("2", LocalDate.now(), "Customer B", "Shipped",
                        20, 50.0, 1000.0, "Product2", "P2", "USA", "LA", "NA", "Medium"),
                new SalesRecord("3", LocalDate.now(), "Customer A", "Shipped",
                        5, 200.0, 1000.0, "Product1", "P1", "USA", "NYC", "NA", "Large")
        );

        AnalyticsService customService = new AnalyticsService(customSupplier);

        assertEquals(3000.0, customService.totalRevenue());
        assertEquals(3, customService.totalOrders());
        assertEquals(35, customService.totalQuantity());

        LinkedHashMap<String, Double> topCustomers = customService.topCustomers(5);
        assertEquals(2000.0, topCustomers.get("Customer A"));
    }

    // Test method references work correctly
    @Test
    @DisplayName("Should use method references correctly")
    void testMethodReferences() {
        double totalRevenue = service.totalRevenue();
        assertTrue(totalRevenue > 0);

        long totalQuantity = service.totalQuantity();
        assertTrue(totalQuantity > 0);
    }

    // Test stream filtering operations
    @Test
    @DisplayName("Should handle stream operations with filter")
    void testStreamFiltering() {
        Map<Integer, Double> revenueByYear = service.revenueByYear();

        assertNotNull(revenueByYear);
        revenueByYear.forEach((year, revenue) -> {
            assertTrue(year > 2000);
            assertTrue(revenue > 0);
        });
    }

    // Test collectors work correctly
    @Test
    @DisplayName("Should handle collectors properly")
    void testCollectors() {
        Map<String, Long> ordersByStatus = service.ordersByStatus();
        assertNotNull(ordersByStatus);

        Map<String, Double> revenueByRegion = service.revenueByRegion();
        assertNotNull(revenueByRegion);

        Map<String, Long> ordersByDealSize = service.ordersByDealSize();
        assertNotNull(ordersByDealSize);

        Map<Boolean, Long> partition = service.partitionHighValue(1000.0);
        assertNotNull(partition);
    }

    // Test sort order in top products
    @Test
    @DisplayName("Should maintain sort order in top products")
    void testTopProductsSortOrder() {
        LinkedHashMap<String, Double> topProducts = service.topProducts(10);

        List<Double> revenues = new ArrayList<>(topProducts.values());
        for (int i = 0; i < revenues.size() - 1; i++) {
            assertTrue(revenues.get(i) >= revenues.get(i + 1));
        }
    }

    // Test sort order in top customers
    @Test
    @DisplayName("Should maintain sort order in top customers")
    void testTopCustomersSortOrder() {
        LinkedHashMap<String, Double> topCustomers = service.topCustomers(10);

        List<Double> revenues = new ArrayList<>(topCustomers.values());
        for (int i = 0; i < revenues.size() - 1; i++) {
            assertTrue(revenues.get(i) >= revenues.get(i + 1));
        }
    }
}
