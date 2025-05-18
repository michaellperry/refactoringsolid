package com.example.demo.service;

/**
 * Interface for report sending operations.
 * This interface defines the contract for classes that send reports.
 * 
 * This is part of the Open/Closed Principle implementation:
 * - Open for extension: New delivery methods can be added by creating new implementations
 * - Closed for modification: Existing code doesn't need to change to support new delivery methods
 */
public interface ReportSenderInterface {
    
    /**
     * Sends a report to the specified recipient.
     * 
     * @param report The formatted report content
     * @param recipient The recipient identifier (could be email, file path, etc.)
     */
    void sendReport(String report, String recipient);
}