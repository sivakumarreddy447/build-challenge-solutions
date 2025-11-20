package com.example.sales.model;

import java.time.LocalDate;

/**
 * Immutable record representing a sales transaction.
 * Automatically provides getters, equals(), hashCode(), toString().
 */
public record SalesRecord(
        String orderNumber,
        LocalDate orderDate,
        String customerName,
        String status,
        int quantityOrdered,
        double priceEach,
        double sales,
        String productLine,
        String productCode,
        String country,
        String city,
        String territory,
        String dealSize
) {
    /**
     * Returns revenue: uses sales field if positive, otherwise calculates quantity * price.
     */
    public double revenue() {
        if (sales > 0.0) {
            return sales;
        }
        return quantityOrdered * priceEach;
    }
}
