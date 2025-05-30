package com.example.demo.service;

import com.example.demo.model.SalesData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class has been refactored to follow the Single Responsibility Principle,
 * the Open/Closed Principle, and now the Dependency Inversion Principle.
 *
 * It delegates specific responsibilities to specialized interfaces:
 * - DataFetcherInterface: Handles data retrieval
 * - ReportFormatterInterface: Handles report formatting
 * - ReportSenderInterface: Handles report delivery
 *
 * This demonstrates the Dependency Inversion Principle by:
 * 1. High-level module (ReportService) depends on abstractions (interfaces)
 * 2. Low-level modules (implementations) also depend on the same abstractions
 * 3. Dependencies are injected by the Spring framework, not created internally
 * 4. Both high and low-level modules are decoupled from each other
 */
@Service
public class ReportService {
    private final DataFetcherInterface dataFetcher;
    private final ReportFormatterInterface reportFormatter;
    private final ReportSenderInterface reportSender;
    private List<SalesData> salesData;
    
    /**
     * Constructor with @Autowired annotation for dependency injection.
     * Spring will automatically inject the appropriate implementations.
     *
     * This demonstrates the Dependency Inversion Principle:
     * - ReportService depends on abstractions (interfaces), not concrete implementations
     * - Dependencies are provided from outside, not created internally
     * - This allows for loose coupling and easier testing
     */
    @Autowired
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