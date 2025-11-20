package com.example.sales;

import com.example.sales.model.SalesRecord;
import com.example.sales.output.ConsoleReporter;
import com.example.sales.reader.CsvSalesReader;
import com.example.sales.service.AnalyticsService;

import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Sales Data Analytics - Main application entry point.
 * Demonstrates Java Streams API and functional programming with:
 * - Lambda expressions and method references
 * - Stream operations (map, filter, reduce, collect)
 * - Collectors (groupingBy, partitioningBy, summingDouble)
 * - Supplier pattern for reusable streams
 */
public final class App {

    public static void main(String[] args) {
        // Determine CSV file path (use argument or default)
        Path csv = args.length > 0 
                ? Path.of(args[0]) 
                : Path.of("data/sales_data_sample.csv");

        // Create stream supplier for reusable data access
        Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csv);

        // Initialize service and reporter
        AnalyticsService svc = new AnalyticsService(supplier);
        ConsoleReporter rpt = new ConsoleReporter();
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

        // Basic financial metrics
        rpt.info("Total Revenue", nf.format(svc.totalRevenue()));
        rpt.info("Total Orders", svc.totalOrders());
        rpt.info("Total Quantity", svc.totalQuantity());
        rpt.info("Average Order Value", nf.format(svc.averageOrderValue()));

        // Temporal analysis
        rpt.info("Revenue by Year", svc.revenueByYear());

        // Geographic analysis
        rpt.info("Revenue by Region", svc.revenueByRegion());

        // Top performers
        rpt.info("Top 10 Products by Revenue", svc.topProducts(10));
        rpt.info("Top 10 Customers by Revenue", svc.topCustomers(10));

        // Operational metrics
        rpt.info("Orders by Status", svc.ordersByStatus());
        rpt.info("Orders by DealSize", svc.ordersByDealSize());

        // Advanced analytics
        double threshold = 500.0;
        rpt.info("Partition High Value Orders (> " + threshold + ")", 
                svc.partitionHighValue(threshold));
        rpt.info("Monthly Revenue (YYYY-MM)", svc.monthlyRevenue());

        // Data quality checks
        rpt.info("Data Quality Checks", svc.dataQualityChecks());
    }
}
