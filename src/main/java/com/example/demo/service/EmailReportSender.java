package com.example.demo.service;

/**
 * Responsible for sending reports via email.
 * This class demonstrates the Single Responsibility Principle by focusing only on email delivery.
 * 
 * It now implements ReportSenderInterface as part of the Open/Closed Principle implementation,
 * allowing for different report delivery methods without modifying existing code.
 */
public class EmailReportSender implements ReportSenderInterface {
    
    /**
     * Sends the report by email.
     * Implementation of the sendReport method from ReportSenderInterface.
     * 
     * In a real application, this would send an actual email.
     * For this demo, it prints to the console.
     */
    @Override
    public void sendReport(String report, String recipient) {
        // In a real app, this would send an email
        // For demo, just print to console
        System.out.println("\n===========================================");
        System.out.println("Sending report by EMAIL to: " + recipient);
        System.out.println("===========================================\n");
        System.out.println(report);
    }
}