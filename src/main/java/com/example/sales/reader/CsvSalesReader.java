package com.example.sales.reader;

import com.example.sales.model.SalesRecord;
import com.example.sales.util.DateUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Reads CSV files and provides reusable stream supplier for sales data.
 * Uses Supplier pattern since streams can only be consumed once.
 */
public final class CsvSalesReader {

    private CsvSalesReader() {}

    /**
     * Creates a supplier that generates fresh streams from CSV file.
     * Stream auto-closes resources on termination.
     */
    public static Supplier<Stream<SalesRecord>> streamSupplier(Path csvPath) {
        return () -> {
            try {
                BufferedReader reader = Files.newBufferedReader(csvPath);
                
                @SuppressWarnings("deprecation")
                CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withTrim());
                
                Iterable<CSVRecord> iterable = parser::iterator;
                
                Stream<SalesRecord> stream = StreamSupport.stream(iterable.spliterator(), false)
                        .map(CsvSalesReader::toSalesRecord)
                        .onClose(() -> {
                            try {
                                parser.close();
                                reader.close();
                            } catch (IOException ignored) {}
                        });
                
                return stream;
            } catch (IOException e) {
                throw new RuntimeException("Failed to open CSV: " + csvPath, e);
            }
        };
    }

    /**
     * Converts CSV record to SalesRecord with safe parsing and default values.
     */
    private static SalesRecord toSalesRecord(CSVRecord rec) {
        String orderNumber = rec.get("ORDERNUMBER");
        String orderDateRaw = rec.get("ORDERDATE");
        java.time.LocalDate date = DateUtils.parseToLocalDate(orderDateRaw);
        String customerName = rec.get("CUSTOMERNAME");
        String status = rec.get("STATUS");
        
        int qty = parseIntSafe(rec, "QUANTITYORDERED", 0);
        double priceEach = parseDoubleSafe(rec, "PRICEEACH", 0.0);
        double sales = parseDoubleSafe(rec, "SALES", qty * priceEach);
        
        String productLine = rec.isMapped("PRODUCTLINE") ? rec.get("PRODUCTLINE") : "";
        String productCode = rec.isMapped("PRODUCTCODE") ? rec.get("PRODUCTCODE") : "";
        String country = rec.isMapped("COUNTRY") ? rec.get("COUNTRY") : "";
        String city = rec.isMapped("CITY") ? rec.get("CITY") : "";
        String territory = rec.isMapped("TERRITORY") ? rec.get("TERRITORY") : "";
        String dealSize = rec.isMapped("DEALSIZE") ? rec.get("DEALSIZE") : "";

        return new SalesRecord(orderNumber, date, customerName, status, qty, priceEach,
                sales, productLine, productCode, country, city, territory, dealSize);
    }

    /**
     * Safely parses integer from CSV column, returns default on error.
     */
    private static int parseIntSafe(CSVRecord rec, String col, int defaultVal) {
        try {
            if (rec.isMapped(col) && !rec.get(col).isBlank()) {
                return Integer.parseInt(rec.get(col).trim());
            }
        } catch (Exception ignored) {}
        return defaultVal;
    }

    /**
     * Safely parses double from CSV column, returns default on error.
     */
    private static double parseDoubleSafe(CSVRecord rec, String col, double defaultVal) {
        try {
            if (rec.isMapped(col) && !rec.get(col).isBlank()) {
                return Double.parseDouble(rec.get(col).trim());
            }
        } catch (Exception ignored) {}
        return defaultVal;
    }
}
