package com.example.sales.service;

import com.example.sales.model.SalesRecord;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Analytics service using Java Streams for sales data processing.
 * Demonstrates functional programming with lambdas, method references, and collectors.
 */
public final class AnalyticsService {

    private final Supplier<Stream<SalesRecord>> streamSupplier;

    public AnalyticsService(Supplier<Stream<SalesRecord>> streamSupplier) {
        this.streamSupplier = streamSupplier;
    }

    /** Calculates total revenue using mapToDouble + sum. */
    public double totalRevenue() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.mapToDouble(SalesRecord::revenue).sum();
        }
    }

    /** Counts total orders. */
    public long totalOrders() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.count();
        }
    }

    /** Sums total quantity ordered. */
    public long totalQuantity() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.mapToLong(SalesRecord::quantityOrdered).sum();
        }
    }

    /** Calculates average order value. */
    public double averageOrderValue() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.mapToDouble(SalesRecord::revenue).average().orElse(0.0);
        }
    }

    /** Groups revenue by year using groupingBy + summingDouble. */
    public Map<Integer, Double> revenueByYear() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.filter(r -> r.orderDate() != null)
                    .collect(Collectors.groupingBy(r -> r.orderDate().getYear(),
                            Collectors.summingDouble(SalesRecord::revenue)));
        }
    }

    /** Groups revenue by territory/region. */
    public Map<String, Double> revenueByRegion() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.collect(Collectors.groupingBy(r -> Optional.ofNullable(r.territory()).orElse("UNKNOWN"),
                    Collectors.summingDouble(SalesRecord::revenue)));
        }
    }

    /** Returns top N products by revenue (sorted descending). */
    public LinkedHashMap<String, Double> topProducts(int topN) {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            Map<String, Double> m = s.collect(Collectors.groupingBy(
                    r -> Optional.ofNullable(r.productLine()).orElse("UNKNOWN"),
                    Collectors.summingDouble(SalesRecord::revenue)));
            return m.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .limit(topN)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (a,b)->a, LinkedHashMap::new));
        }
    }

    /** Returns top N customers by revenue (sorted descending). */
    public LinkedHashMap<String, Double> topCustomers(int topN) {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            Map<String, Double> m = s.collect(Collectors.groupingBy(
                    r -> Optional.ofNullable(r.customerName()).orElse("UNKNOWN"),
                    Collectors.summingDouble(SalesRecord::revenue)));
            return m.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .limit(topN)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (a,b)->a, LinkedHashMap::new));
        }
    }

    /** Counts orders by status using groupingBy + counting. */
    public Map<String, Long> ordersByStatus() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.collect(Collectors.groupingBy(r -> Optional.ofNullable(r.status()).orElse("UNKNOWN"),
                    Collectors.counting()));
        }
    }

    /** Counts orders by deal size (Small/Medium/Large). */
    public Map<String, Long> ordersByDealSize() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.collect(Collectors.groupingBy(r -> Optional.ofNullable(r.dealSize()).orElse("UNKNOWN"),
                    Collectors.counting()));
        }
    }

    /** Partitions orders into high/low value based on threshold. */
    public Map<Boolean, Long> partitionHighValue(double threshold) {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.collect(Collectors.partitioningBy(r -> r.revenue() > threshold, Collectors.counting()));
        }
    }

    /** Calculates monthly revenue in YYYY-MM format. */
    public Map<String, Double> monthlyRevenue() {
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            return s.filter(r -> r.orderDate() != null)
                    .collect(Collectors.groupingBy(
                            r -> String.format("%d-%02d", r.orderDate().getYear(), r.orderDate().getMonthValue()),
                            Collectors.summingDouble(SalesRecord::revenue)
                    ));
        }
    }

    /** Performs data quality checks: missing values, duplicates, etc. */
    public Map<String, Object> dataQualityChecks() {
        Map<String, Object> out = new LinkedHashMap<>();
        try (Stream<SalesRecord> s = streamSupplier.get()) {
            List<SalesRecord> list = s.collect(Collectors.toList());
            long missingDates = list.stream().filter(r -> r.orderDate() == null).count();
            long missingSales = list.stream().filter(r -> r.revenue() == 0.0).count();
            long duplicates = list.size() - list.stream().map(SalesRecord::orderNumber).distinct().count();
            out.put("rows", list.size());
            out.put("missingOrderDate", missingDates);
            out.put("missingSalesValue", missingSales);
            out.put("duplicateOrderNumbers", duplicates);
        }
        return out;
    }
}
