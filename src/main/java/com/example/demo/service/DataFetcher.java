package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Responsible for fetching or generating sales data.
 * This class demonstrates the Single Responsibility Principle by focusing only on data retrieval.
 */
public class DataFetcher {
    
    /**
     * Fetches sales data for the specified month and year.
     * In a real application, this would fetch from a database.
     * For this demo, it generates sample data.
     */
    public List<SalesData> fetchSalesData(int month, int year) {
        // In a real app, this would fetch from a database
        // For demo, generate sample sales data
        List<SalesData> salesData = new ArrayList<>();
        
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
        
        return salesData;
    }
}