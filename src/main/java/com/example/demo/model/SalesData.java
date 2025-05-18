package com.example.demo.model;

import java.time.LocalDate;

/**
 * Represents sales data for a specific product category on a specific date.
 */
public class SalesData {
    private String productCategory;
    private double amount;
    private LocalDate date;

    public SalesData(String productCategory, double amount, LocalDate date) {
        this.productCategory = productCategory;
        this.amount = amount;
        this.date = date;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "SalesData{" +
                "productCategory='" + productCategory + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}