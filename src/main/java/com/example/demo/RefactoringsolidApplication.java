package com.example.demo;

import com.example.demo.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * Main application class that demonstrates the implementation
 * of the report generation system after applying the Single Responsibility Principle
 * and the Open/Closed Principle.
 */
@SpringBootApplication
public class RefactoringsolidApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefactoringsolidApplication.class, args);
	}

	/**
	 * Creates a CommandLineRunner bean that will run when the application starts.
	 * This demonstrates the functionality of our ReportService after applying
	 * the Single Responsibility Principle and the Open/Closed Principle.
	 */
	@Bean
	public CommandLineRunner demoReportService() {
		return args -> {
			System.out.println("Starting Report Generation Demo...");
			System.out.println("This demonstrates an implementation after applying the Single Responsibility Principle and Open/Closed Principle.");
			
			// Get the current month and year
			LocalDate now = LocalDate.now();
			int currentMonth = now.getMonthValue();
			int currentYear = now.getYear();
			
			// DEMO 1: Using default implementations
			System.out.println("\n===========================================");
			System.out.println("DEMO 1: Using default implementations");
			System.out.println("===========================================");
			
			// Create a new ReportService with default implementations
			ReportService defaultReportService = new ReportService();
			
			// Generate a report for the current month
			System.out.println("\nGenerating report for " + now.getMonth() + " " + currentYear + " using default implementations...\n");
			defaultReportService.generateMonthlyReport(currentMonth, currentYear, "manager@example.com");
			
			// DEMO 2: Using alternative implementations
			System.out.println("\n===========================================");
			System.out.println("DEMO 2: Using alternative implementations");
			System.out.println("===========================================");
			
			// Create a new ReportService with alternative implementations
			ReportService alternativeReportService = new ReportService(
				new DataFetcher(),              // Same data fetcher
				new PDFReportFormatter(),       // PDF formatter instead of text
				new FileReportSender()          // File sender instead of email
			);
			
			// Generate a report for the current month
			System.out.println("\nGenerating report for " + now.getMonth() + " " + currentYear + " using alternative implementations...\n");
			alternativeReportService.generateMonthlyReport(currentMonth, currentYear, "reports/monthly_sales_report.pdf");
			
			System.out.println("\nReport generation completed.");
			System.out.println("\nImprovements after applying the Open/Closed Principle:");
			System.out.println("1. Created interfaces for each responsibility (DataFetcherInterface, ReportFormatterInterface, ReportSenderInterface)");
			System.out.println("2. Made existing classes implement these interfaces");
			System.out.println("3. Updated ReportService to depend on interfaces rather than concrete classes");
			System.out.println("4. Created alternative implementations (PDFReportFormatter, FileReportSender)");
			System.out.println("5. Demonstrated how the system can be extended without modifying existing code");
			System.out.println("\nThis demonstrates the Open/Closed Principle:");
			System.out.println("- Open for extension: We can add new implementations of our interfaces");
			System.out.println("- Closed for modification: We don't need to modify existing code to add new functionality");
			System.out.println("\nRemaining issues to address in future refactorings:");
			System.out.println("1. Dependency Inversion: High-level modules should not depend on low-level modules directly");
		};
	}
}
