package com.example.demo;

import com.example.demo.service.ReportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * Main application class that demonstrates the non-SOLID implementation
 * of the report generation system.
 */
@SpringBootApplication
public class RefactoringsolidApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefactoringsolidApplication.class, args);
	}

	/**
	 * Creates a CommandLineRunner bean that will run when the application starts.
	 * This demonstrates the functionality of our non-SOLID ReportService.
	 */
	@Bean
	public CommandLineRunner demoReportService() {
		return args -> {
			System.out.println("Starting Report Generation Demo...");
			System.out.println("This demonstrates a non-SOLID implementation with multiple responsibilities in one class.");
			
			// Create a new ReportService
			ReportService reportService = new ReportService();
			
			// Get the current month and year
			LocalDate now = LocalDate.now();
			int currentMonth = now.getMonthValue();
			int currentYear = now.getYear();
			
			// Generate a report for the current month
			System.out.println("\nGenerating report for " + now.getMonth() + " " + currentYear + "...\n");
			reportService.generateMonthlyReport(currentMonth, currentYear, "manager@example.com");
			
			System.out.println("\nReport generation completed.");
			System.out.println("\nIssues with this non-SOLID implementation:");
			System.out.println("1. Single Responsibility Principle violation: ReportService handles data access, business logic, formatting, and email sending");
			System.out.println("2. Open/Closed Principle violation: Adding new report formats or delivery methods requires modifying the existing class");
			System.out.println("3. Dependency Inversion Principle violation: High-level modules depend on low-level modules directly");
			System.out.println("4. Tight coupling between components makes the code hard to test and maintain");
		};
	}
}
