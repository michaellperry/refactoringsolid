package com.example.demo.config;

import com.example.demo.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Spring configuration class for the reporting system.
 * This class demonstrates the Dependency Inversion Principle by:
 * 
 * 1. Defining beans for our interfaces
 * 2. Allowing easy swapping of implementations
 * 3. Centralizing dependency configuration
 * 
 * This approach decouples high-level modules from low-level modules,
 * as both depend on abstractions (interfaces) rather than concrete implementations.
 */
@Configuration
public class ReportingConfig {

    /**
     * Provides the default DataFetcherInterface implementation.
     * The @Primary annotation indicates this is the default implementation
     * to use when multiple beans of the same type exist.
     */
    @Bean
    @Primary
    public DataFetcherInterface defaultDataFetcher() {
        return new DataFetcher();
    }
    
    /**
     * Provides the default ReportFormatterInterface implementation.
     * The @Primary annotation indicates this is the default implementation
     * to use when multiple beans of the same type exist.
     */
    @Bean
    @Primary
    public ReportFormatterInterface defaultReportFormatter() {
        return new ReportFormatter();
    }
    
    /**
     * Provides an alternative PDF implementation of ReportFormatterInterface.
     * This bean can be injected by name when needed.
     */
    @Bean
    public ReportFormatterInterface pdfReportFormatter() {
        return new PDFReportFormatter();
    }
    
    /**
     * Provides the default ReportSenderInterface implementation.
     * The @Primary annotation indicates this is the default implementation
     * to use when multiple beans of the same type exist.
     */
    @Bean
    @Primary
    public ReportSenderInterface emailReportSender() {
        return new EmailReportSender();
    }
    
    /**
     * Provides an alternative file-based implementation of ReportSenderInterface.
     * This bean can be injected by name when needed.
     */
    @Bean
    public ReportSenderInterface fileReportSender() {
        return new FileReportSender();
    }
    
    /**
     * BENEFITS OF THIS APPROACH:
     * 
     * 1. Flexibility: We can easily swap implementations by changing this configuration
     * 2. Testability: We can provide mock implementations for testing
     * 3. Maintainability: Dependencies are managed in one place
     * 4. Loose Coupling: High-level modules depend on abstractions, not implementations
     * 
     * This demonstrates the Dependency Inversion Principle:
     * "Depend upon abstractions, not concretions."
     */
}