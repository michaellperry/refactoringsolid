package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A file-based implementation of the ReportSenderInterface.
 * This class demonstrates the Open/Closed Principle by extending the system's functionality
 * without modifying existing code.
 */
public class FileReportSender implements ReportSenderInterface {
    
    /**
     * Sends the report by saving it to a file.
     * In a real application, this would save the report to an actual file.
     * For this demo, it prints to the console.
     */
    @Override
    public void sendReport(String report, String filePath) {
        // In a real app, this would save to a file
        // For demo, just print to console
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        System.out.println("\n===========================================");
        System.out.println("Saving report to FILE: " + filePath);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("===========================================\n");
        System.out.println(report);
        System.out.println("\n===========================================");
        System.out.println("File successfully saved to: " + filePath);
        System.out.println("===========================================\n");
    }
}