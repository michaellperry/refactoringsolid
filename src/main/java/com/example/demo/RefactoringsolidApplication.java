package com.example.demo;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * Main application class that demonstrates the implementation
 * of the report generation system after applying the Single Responsibility Principle,
 * the Open/Closed Principle, and now the Dependency Inversion Principle.
 */
@SpringBootApplication
public class RefactoringsolidApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefactoringsolidApplication.class, args);
	}

	/**
	 * Creates a CommandLineRunner bean that will run when the application starts.
	 * This demonstrates the functionality of our ReportService after applying
	 * the Single Responsibility Principle, Open/Closed Principle, and Dependency Inversion Principle.
	 */
	@Bean
	public CommandLineRunner demoReportService(
			ApplicationContext context,
			ReportService reportService,
			@Qualifier("pdfReportFormatter") ReportFormatterInterface pdfFormatter,
			@Qualifier("fileReportSender") ReportSenderInterface fileSender) {
		
		return args -> {
			System.out.println("Starting Report Generation Demo...");
			System.out.println("This demonstrates an implementation after applying the Single Responsibility Principle, Open/Closed Principle, and Dependency Inversion Principle.");
			
			// Get the current month and year
			LocalDate now = LocalDate.now();
			int currentMonth = now.getMonthValue();
			int currentYear = now.getYear();
			
			// DEMO 1: Using default implementations with Spring DI
			System.out.println("\n===========================================");
			System.out.println("DEMO 1: Using default implementations with Spring DI");
			System.out.println("===========================================");
			
			// The ReportService is now injected by Spring with default implementations
			System.out.println("\nGenerating report for " + now.getMonth() + " " + currentYear + " using default implementations...\n");
			reportService.generateMonthlyReport(currentMonth, currentYear, "manager@example.com");
			
			// DEMO 2: Using alternative implementations with Spring DI
			System.out.println("\n===========================================");
			System.out.println("DEMO 2: Using alternative implementations with Spring DI");
			System.out.println("===========================================");
			
			// Get alternative implementation from Spring context
			ReportService alternativeReportService = context.getBean(ReportService.class);
			
			// Demonstrate the problematic approach (without DIP)
			System.out.println("\n===========================================");
			System.out.println("DEMO 3: Showing the problematic approach (without DIP)");
			System.out.println("===========================================");
			
			ReportServiceWithoutDIP problematicService = new ReportServiceWithoutDIP();
			System.out.println("\nGenerating report using the problematic approach (tightly coupled)...\n");
			problematicService.generateMonthlyReport(currentMonth, currentYear, "reports/problematic_report.txt");
			
			// Generate a report for the current month using PDF formatter and file sender
			System.out.println("\n===========================================");
			System.out.println("DEMO 4: Using specific implementations by qualifier");
			System.out.println("===========================================");
			
			// Create a custom service with specific implementations
			// This demonstrates how easily we can swap implementations with Spring
			ReportService customService = new ReportService(
				context.getBean(DataFetcherInterface.class),  // Default data fetcher
				pdfFormatter,                                 // PDF formatter (injected by qualifier)
				fileSender                                    // File sender (injected by qualifier)
			);
			
			System.out.println("\nGenerating report for " + now.getMonth() + " " + currentYear + " using PDF formatter and file sender...\n");
			customService.generateMonthlyReport(currentMonth, currentYear, "reports/monthly_sales_report.pdf");
			
			System.out.println("\nReport generation completed.");
			System.out.println("\nImprovements after applying the Dependency Inversion Principle:");
			System.out.println("1. Added Spring annotations (@Service, @Autowired) to enable dependency injection");
			System.out.println("2. Created a Spring configuration class to define and manage beans");
			System.out.println("3. Updated the application to use Spring's ApplicationContext");
			System.out.println("4. Removed direct instantiation of dependencies in ReportService");
			System.out.println("5. Demonstrated how dependencies can be easily swapped");
			
			System.out.println("\nThis demonstrates the Dependency Inversion Principle:");
			System.out.println("- High-level modules (ReportService) depend on abstractions (interfaces)");
			System.out.println("- Low-level modules (implementations) also depend on abstractions");
			System.out.println("- Dependencies are injected, not created internally");
			System.out.println("- Both high and low-level modules are decoupled from each other");
			
			System.out.println("\nBenefits of applying the Dependency Inversion Principle:");
			System.out.println("1. Flexibility: We can easily swap implementations without changing the high-level module");
			System.out.println("2. Testability: We can provide mock implementations for testing");
			System.out.println("3. Maintainability: Dependencies are managed in one place");
			System.out.println("4. Loose Coupling: Changes to one component don't affect others");
		};
	}
}
