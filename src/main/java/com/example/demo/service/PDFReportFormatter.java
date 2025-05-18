package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A PDF implementation of the ReportFormatterInterface.
 * This class demonstrates the Open/Closed Principle by extending the system's functionality
 * without modifying existing code.
 */
public class PDFReportFormatter implements ReportFormatterInterface {
    
    /**
     * Formats the report as if it were a PDF.
     * In a real application, this would generate an actual PDF file.
     * For this demo, it returns a string representation of what the PDF would contain.
     */
    @Override
    public String formatReport(List<SalesData> salesData, double totalSales, Map<String, Double> growthPercentages) {
        if (salesData == null || salesData.isEmpty()) {
            return "[PDF] No sales data available for the specified period.";
        }
        
        // Get the month and year from the first sales data entry
        LocalDate firstDate = salesData.get(0).getDate();
        String monthYear = firstDate.getMonth() + " " + firstDate.getYear();
        
        // Format the report as a PDF (simulated with text)
        StringBuilder report = new StringBuilder();
        report.append("[PDF REPORT]\n");
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
        
        // Add PDF-specific footer
        report.append("\n===========================================\n");
        report.append("This PDF report was generated on ").append(LocalDate.now()).append("\n");
        report.append("===========================================\n");
        
        return report.toString();
    }
}