package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.util.List;
import java.util.Map;

/**
 * Interface for report formatting operations.
 * This interface defines the contract for classes that format sales reports.
 * 
 * This is part of the Open/Closed Principle implementation:
 * - Open for extension: New report formats can be added by creating new implementations
 * - Closed for modification: Existing code doesn't need to change to support new formats
 */
public interface ReportFormatterInterface {
    
    /**
     * Formats sales data into a report.
     * 
     * @param salesData The sales data to include in the report
     * @param totalSales The total sales amount
     * @param growthPercentages Map of growth percentages by product category
     * @return A formatted report as a String
     */
    String formatReport(List<SalesData> salesData, double totalSales, Map<String, Double> growthPercentages);
}