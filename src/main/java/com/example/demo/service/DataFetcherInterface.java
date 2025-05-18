package com.example.demo.service;

import com.example.demo.model.SalesData;

import java.util.List;

/**
 * Interface for data fetching operations.
 * This interface defines the contract for classes that retrieve sales data.
 * 
 * This is part of the Open/Closed Principle implementation:
 * - Open for extension: New data sources can be added by creating new implementations
 * - Closed for modification: Existing code doesn't need to change to support new data sources
 */
public interface DataFetcherInterface {
    
    /**
     * Fetches sales data for the specified month and year.
     * 
     * @param month The month for which to fetch data (1-12)
     * @param year The year for which to fetch data
     * @return A list of SalesData objects
     */
    List<SalesData> fetchSalesData(int month, int year);
}