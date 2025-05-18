package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class has been refactored to follow both the Single Responsibility Principle
 * and the Open/Closed Principle.
 *
 * It now delegates specific responsibilities to specialized interfaces:
 * - DataFetcherInterface: Handles data retrieval
 * - ReportFormatterInterface: Handles report formatting
 * - ReportSenderInterface: Handles report delivery
 *
 * This demonstrates the Open/Closed Principle by:
 * 1. Using interfaces instead of concrete classes
 * 2. Being open for extension (can use different implementations)
 * 3. Being closed for modification (core logic doesn't need to change)
 */
public class ReportService {
    private final DataFetcherInterface dataFetcher;
    private final ReportFormatterInterface reportFormatter;
    private final ReportSenderInterface reportSender;
    private List<SalesData> salesData;
    
    /**
     * Constructor that initializes with default implementations.
     */
    public ReportService() {
        // Initialize with default implementations
        this.dataFetcher = new DataFetcher();
        this.reportFormatter = new ReportFormatter();
        this.reportSender = new EmailReportSender();
    }
    
    /**
     * Constructor that allows dependency injection of specific implementations.
     * This demonstrates the Open/Closed Principle by allowing different implementations
     * to be used without modifying the ReportService class.
     */
    public ReportService(
            DataFetcherInterface dataFetcher,
            ReportFormatterInterface reportFormatter,
            ReportSenderInterface reportSender) {
        this.dataFetcher = dataFetcher;
        this.reportFormatter = reportFormatter;
        this.reportSender = reportSender;
    }
    
    /**
     * Generates and sends a monthly sales report.
     * This method now orchestrates the process by delegating to specialized classes.
     */
    public void generateMonthlyReport(int month, int year, String recipient) {
        // Fetch data using DataFetcherInterface
        salesData = dataFetcher.fetchSalesData(month, year);
        
        // Calculate metrics
        double totalSales = calculateTotalSales();
        Map<String, Double> growthPercentages = calculateGrowthPercentages();
        
        // Format report using ReportFormatterInterface
        String report = reportFormatter.formatReport(salesData, totalSales, growthPercentages);
        
        // Send report using ReportSenderInterface
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
}