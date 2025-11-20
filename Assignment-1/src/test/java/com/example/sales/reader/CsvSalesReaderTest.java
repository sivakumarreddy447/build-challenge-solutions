package com.example.sales.reader;

import com.example.sales.model.SalesRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for CsvSalesReader - validates CSV parsing and stream supplier pattern. */
@DisplayName("CsvSalesReader Tests")
class CsvSalesReaderTest {

    // Test reading valid CSV with 10 records
    @Test
    @DisplayName("Should read valid CSV file successfully")
    void testReadValidCsv() {
        Path csvPath = Path.of("src/test/resources/test_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        try (Stream<SalesRecord> stream = supplier.get()) {
            List<SalesRecord> records = stream.toList();
            assertEquals(10, records.size());

            SalesRecord first = records.get(0);
            assertEquals("10001", first.orderNumber());
            assertEquals("Test Customer A", first.customerName());
            assertEquals("Shipped", first.status());
            assertEquals(10, first.quantityOrdered());
            assertEquals(100.0, first.priceEach());
            assertEquals(1000.0, first.sales());
            assertEquals("Motorcycles", first.productLine());
            assertEquals("M001", first.productCode());
            assertEquals("USA", first.country());
            assertEquals("NA", first.territory());
            assertEquals("Small", first.dealSize());
        }
    }

    // Test handling empty CSV file
    @Test
    @DisplayName("Should handle empty CSV file")
    void testReadEmptyCsv() {
        Path csvPath = Path.of("src/test/resources/empty_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        try (Stream<SalesRecord> stream = supplier.get()) {
            List<SalesRecord> records = stream.toList();
            assertEquals(0, records.size());
        }
    }

    // Test parsing CSV with invalid data gracefully
    @Test
    @DisplayName("Should handle CSV with invalid data gracefully")
    void testReadInvalidCsv() {
        Path csvPath = Path.of("src/test/resources/invalid_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        try (Stream<SalesRecord> stream = supplier.get()) {
            List<SalesRecord> records = stream.toList();
            assertEquals(2, records.size());

            SalesRecord first = records.get(0);
            assertEquals("10011", first.orderNumber());
            assertEquals(0, first.quantityOrdered()); // Invalid number defaults to 0
            assertNull(first.orderDate()); // Invalid date should be null

            SalesRecord second = records.get(1);
            assertEquals("10012", second.orderNumber());
            assertEquals(0, second.quantityOrdered()); // Empty defaults to 0
        }
    }

    // Test exception thrown for non-existent file
    @Test
    @DisplayName("Should throw exception for non-existent file")
    void testReadNonExistentFile() {
        Path csvPath = Path.of("src/test/resources/non_existent.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        assertThrows(RuntimeException.class, supplier::get);
    }

    // Test supplier creates multiple independent streams
    @Test
    @DisplayName("Should allow multiple stream creations from supplier")
    void testMultipleStreamCreations() {
        Path csvPath = Path.of("src/test/resources/test_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        long count1;
        try (Stream<SalesRecord> stream1 = supplier.get()) {
            count1 = stream1.count();
        }

        long count2;
        try (Stream<SalesRecord> stream2 = supplier.get()) {
            count2 = stream2.count();
        }

        assertEquals(count1, count2);
        assertEquals(10, count1);
    }

    // Test date parsing works correctly in CSV context
    @Test
    @DisplayName("Should parse dates correctly")
    void testDateParsing() {
        Path csvPath = Path.of("src/test/resources/test_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        try (Stream<SalesRecord> stream = supplier.get()) {
            List<SalesRecord> records = stream.toList();

            SalesRecord first = records.get(0);
            assertNotNull(first.orderDate());
            assertEquals(2023, first.orderDate().getYear());
            assertEquals(1, first.orderDate().getMonthValue());
            assertEquals(15, first.orderDate().getDayOfMonth());
        }
    }

    // Test revenue calculation in parsed records
    @Test
    @DisplayName("Should calculate revenue correctly")
    void testRevenueCalculation() {
        Path csvPath = Path.of("src/test/resources/test_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        try (Stream<SalesRecord> stream = supplier.get()) {
            List<SalesRecord> records = stream.toList();
            records.forEach(record -> assertTrue(record.revenue() > 0));
        }
    }

    // Test handling missing optional fields
    @Test
    @DisplayName("Should handle missing optional fields")
    void testMissingOptionalFields() {
        Path csvPath = Path.of("src/test/resources/invalid_sales_data.csv");
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

        try (Stream<SalesRecord> stream = supplier.get()) {
            List<SalesRecord> records = stream.toList();
            assertFalse(records.isEmpty());
            records.forEach(record -> {
                assertNotNull(record.orderNumber());
                assertNotNull(record.productLine());
                assertNotNull(record.country());
            });
        }
    }

    // Test resources are closed properly
    @Test
    @DisplayName("Should close resources properly")
    void testResourceClosure(@TempDir Path tempDir) throws IOException {
        Path tempCsv = tempDir.resolve("temp.csv");
        String content = """
                ORDERNUMBER,QUANTITYORDERED,PRICEEACH,SALES,ORDERDATE,STATUS,PRODUCTLINE,PRODUCTCODE,CUSTOMERNAME,COUNTRY,CITY,TERRITORY,DEALSIZE
                10001,10,100.0,1000.0,1/1/2023 0:00,Shipped,Motorcycles,M001,Customer,USA,NYC,NA,Small
                """;
        Files.writeString(tempCsv, content);

        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(tempCsv);

        try (Stream<SalesRecord> stream = supplier.get()) {
            stream.count();
        }

        // Should be able to read again (resources were closed properly)
        try (Stream<SalesRecord> stream = supplier.get()) {
            long count = stream.count();
            assertEquals(1, count);
        }
    }
}
