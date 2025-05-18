package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class has been refactored to follow the Single Responsibility Principle.
 * It now delegates specific responsibilities to specialized classes:
 * - DataFetcher: Handles data retrieval
 * - ReportFormatter: Handles report formatting
 * - EmailSender: Handles email delivery
 * 
 * This demonstrates the Single Responsibility Principle by ensuring each class
 * has only one reason to change.
 */
public class ReportService {
    private final DataFetcher dataFetcher;
    private final ReportFormatter reportFormatter;
    private final EmailSender emailSender;
    private List<SalesData> salesData;
    
    /**
     * Constructor that initializes the specialized service classes.
     */
    public ReportService() {
        // Initialize the specialized service classes
        this.dataFetcher = new DataFetcher();
        this.reportFormatter = new ReportFormatter();
        this.emailSender = new EmailSender();
    }
    
    /**
     * Generates and sends a monthly sales report.
     * This method now orchestrates the process by delegating to specialized classes.
     */
    public void generateMonthlyReport(int month, int year, String recipient) {
        // Fetch data using DataFetcher
        salesData = dataFetcher.fetchSalesData(month, year);
        
        // Calculate metrics
        double totalSales = calculateTotalSales();
        Map<String, Double> growthPercentages = calculateGrowthPercentages();
        
        // Format report using ReportFormatter
        String report = reportFormatter.formatReportAsText(salesData, totalSales, growthPercentages);
        
        // Send report using EmailSender
        emailSender.sendReportByEmail(report, recipient);
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
}