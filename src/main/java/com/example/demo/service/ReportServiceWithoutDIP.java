package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class demonstrates how the service would look WITHOUT applying
 * the Dependency Inversion Principle (DIP).
 *
 * ISSUES WITH THIS APPROACH:
 * 1. High-level module (ReportServiceWithoutDIP) directly depends on low-level modules
 *    (concrete implementations like DataFetcher, ReportFormatter, EmailReportSender)
 * 2. Direct instantiation creates tight coupling between classes
 * 3. Makes testing difficult as we cannot easily substitute implementations
 * 4. Changes to low-level modules may require changes to this class
 * 5. Violates DIP: "High-level modules should not depend on low-level modules.
 *    Both should depend on abstractions."
 */
public class ReportServiceWithoutDIP {
    // Direct instantiation of concrete classes - this violates DIP
    private final DataFetcher dataFetcher = new DataFetcher();
    private final ReportFormatter reportFormatter = new ReportFormatter();
    private final EmailReportSender reportSender = new EmailReportSender();
    private List<SalesData> salesData;
    
    /**
     * Generates and sends a monthly sales report.
     * This method is tightly coupled to specific implementations.
     */
    public void generateMonthlyReport(int month, int year, String recipient) {
        // Directly using concrete implementations
        salesData = dataFetcher.fetchSalesData(month, year);
        
        // Calculate metrics
        double totalSales = calculateTotalSales();
        Map<String, Double> growthPercentages = calculateGrowthPercentages();
        
        // Using concrete implementation
        String report = reportFormatter.formatReport(salesData, totalSales, growthPercentages);
        
        // Using concrete implementation
        reportSender.sendReport(report, recipient);
    }
    
    /**
     * Calculates the total sales amount from the sales data.
     */
    private double calculateTotalSales() {
        if (salesData == null || salesData.isEmpty()) {
            return 0.0;
        }
        
        return salesData.stream()
                .mapToDouble(SalesData::getAmount)
                .sum();
    }
    
    /**
     * Calculates growth percentages by product category.
     * In a real application, this would compare with previous periods.
     * For this demo, it generates random growth percentages.
     */
    private Map<String, Double> calculateGrowthPercentages() {
        if (salesData == null || salesData.isEmpty()) {
            return Collections.emptyMap();
        }
        
        // Group sales by category
        Map<String, Double> totalsByCategory = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesData::getProductCategory,
                        Collectors.summingDouble(SalesData::getAmount)
                ));
        
        // In a real app, we would compare with previous period
        // For demo, generate random growth percentages
        Map<String, Double> growthPercentages = new HashMap<>();
        Random random = new Random(42); // Fixed seed for reproducibility
        
        for (String category : totalsByCategory.keySet()) {
            // Generate a random growth percentage between -20% and +50%
            double growthPercentage = -20 + random.nextDouble() * 70;
            growthPercentages.put(category, growthPercentage);
        }
        
        return growthPercentages;
    }
    
    /**
     * PROBLEMS WITH THIS DESIGN:
     * 
     * 1. Testability Issues:
     *    - Cannot easily mock dependencies for unit testing
     *    - Must test entire chain of dependencies
     * 
     * 2. Flexibility Issues:
     *    - Cannot easily swap implementations
     *    - Changes to dependencies may require changes to this class
     * 
     * 3. Maintainability Issues:
     *    - Tight coupling increases complexity
     *    - Changes ripple through the system
     */
}