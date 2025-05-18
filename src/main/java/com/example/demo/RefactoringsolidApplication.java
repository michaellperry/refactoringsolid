package com.example.demo;

import com.example.demo.service.ReportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * Main application class that demonstrates the implementation
 * of the report generation system after applying the Single Responsibility Principle.
 */
@SpringBootApplication
public class RefactoringsolidApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefactoringsolidApplication.class, args);
	}

	/**
	 * Creates a CommandLineRunner bean that will run when the application starts.
	 * This demonstrates the functionality of our ReportService after applying the Single Responsibility Principle.
	 */
	@Bean
	public CommandLineRunner demoReportService() {
		return args -> {
			System.out.println("Starting Report Generation Demo...");
			System.out.println("This demonstrates an implementation after applying the Single Responsibility Principle.");
			
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
			System.out.println("\nImprovements after applying the Single Responsibility Principle:");
			System.out.println("1. Each class has a single responsibility and a single reason to change");
			System.out.println("2. DataFetcher is responsible only for retrieving data");
			System.out.println("3. ReportFormatter is responsible only for formatting reports");
			System.out.println("4. EmailSender is responsible only for sending emails");
			System.out.println("5. ReportService now orchestrates these specialized classes");
			System.out.println("\nRemaining issues to address in future refactorings:");
			System.out.println("1. Open/Closed Principle: Classes should be open for extension but closed for modification");
			System.out.println("2. Dependency Inversion: High-level modules should not depend on low-level modules directly");
		};
	}
}
