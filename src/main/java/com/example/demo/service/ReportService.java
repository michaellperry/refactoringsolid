package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class intentionally violates SOLID principles by handling multiple responsibilities:
 * - Data fetching
 * - Business logic calculations
 * - Report formatting
 * - Email sending
 */
public class ReportService {
    private List<SalesData> salesData;
    
    /**
     * Generates and sends a monthly sales report.
     * This method handles the entire process from data fetching to email sending.
     */
    public void generateMonthlyReport(int month, int year, String recipient) {
        // Fetch data
        fetchSalesData(month, year);
        
        // Calculate metrics
        double totalSales = calculateTotalSales();
        Map<String, Double> growthPercentages = calculateGrowthPercentages();
        
        // Format report
        String report = formatReportAsText(totalSales, growthPercentages);
        
        // Send report
        sendReportByEmail(report, recipient);
    }
    
    /**
     * Fetches sales data for the specified month and year.
     * In a real application, this would fetch from a database.
     * For this demo, it generates sample data.
     */
    private void fetchSalesData(int month, int year) {
        // In a real app, this would fetch from a database
        // For demo, generate sample sales data
        salesData = new ArrayList<>();
        
        // Create a YearMonth object for the specified month and year
        YearMonth yearMonth = YearMonth.of(year, month);
        
        // Get the number of days in the month
        int daysInMonth = yearMonth.lengthOfMonth();
        
        // Generate random sales data for each day of the month
        Random random = new Random(42); // Fixed seed for reproducibility
        String[] categories = {"Electronics", "Clothing", "Food", "Books"};
        
        for (int day = 1; day <= daysInMonth; day++) {
            for (String category : categories) {
                // Generate a random amount between 100 and 1000
                double amount = 100 + random.nextDouble() * 900;
                
                // Create a LocalDate for this day
                LocalDate date = LocalDate.of(year, month, day);
                
                // Add to sales data
                salesData.add(new SalesData(category, amount, date));
            }
        }
        
        System.out.println("Fetched " + salesData.size() + " sales records for " + 
                           yearMonth.getMonth() + " " + year);
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
     * Formats the report as text.
     */
    private String formatReportAsText(double totalSales, Map<String, Double> growthPercentages) {
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
    
    /**
     * Sends the report by email.
     * In a real application, this would send an actual email.
     * For this demo, it prints to the console.
     */
    private void sendReportByEmail(String report, String recipient) {
        // In a real app, this would send an email
        // For demo, just print to console
        System.out.println("\n===========================================");
        System.out.println("Sending report to: " + recipient);
        System.out.println("===========================================\n");
        System.out.println(report);
    }
}