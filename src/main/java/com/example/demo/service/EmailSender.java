package com.example.demo.service;

/**
 * Responsible for sending reports via email.
 * This class demonstrates the Single Responsibility Principle by focusing only on email delivery.
 */
public class EmailSender {
    
    /**
     * Sends the report by email.
     * In a real application, this would send an actual email.
     * For this demo, it prints to the console.
     */
    public void sendReportByEmail(String report, String recipient) {
        // In a real app, this would send an email
        // For demo, just print to console
        System.out.println("\n===========================================");
        System.out.println("Sending report to: " + recipient);
        System.out.println("===========================================\n");
        System.out.println(report);
    }
}