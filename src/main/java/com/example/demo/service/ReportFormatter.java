package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Responsible for formatting sales data into a readable report.
 * This class demonstrates the Single Responsibility Principle by focusing only on report formatting.
 */
public class ReportFormatter {
    
    /**
     * Formats the report as text.
     */
    public String formatReportAsText(List<SalesData> salesData, double totalSales, Map<String, Double> growthPercentages) {
        if (salesData == null || salesData.isEmpty()) {
            return "No sales data available for the specified period.";
        }
        
        // Get the month and year from the first sales data entry
        LocalDate firstDate = salesData.get(0).getDate();
        String monthYear = firstDate.getMonth() + " " + firstDate.getYear();
        
        // Format the report as text
        StringBuilder report = new StringBuilder();
        report.append("Monthly Sales Report - ").append(monthYear).append("\n");
        report.append("===========================================\n\n");
        
        // Add total sales
        report.append(String.format("Total Sales: $%.2f\n\n", totalSales));
        
        // Add sales by category
        report.append("Sales by Category:\n");
        report.append("------------------\n");
        
        Map<String, Double> totalsByCategory = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesData::getProductCategory,
                        Collectors.summingDouble(SalesData::getAmount)
                ));
        
        for (Map.Entry<String, Double> entry : totalsByCategory.entrySet()) {
            report.append(String.format("%-12s: $%.2f\n", entry.getKey(), entry.getValue()));
        }
        
        report.append("\n");
        
        // Add growth percentages
        report.append("Growth Percentages (compared to previous period):\n");
        report.append("------------------------------------------------\n");
        
        for (Map.Entry<String, Double> entry : growthPercentages.entrySet()) {
            report.append(String.format("%-12s: %+.2f%%\n", entry.getKey(), entry.getValue()));
        }
        
        return report.toString();
    }
}