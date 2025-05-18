package com.example.demo.service;

import com.example.demo.model.SalesData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This test class demonstrates how the Dependency Inversion Principle
 * improves testability by allowing us to easily mock dependencies.
 */
public class ReportServiceTest {

    @Mock
    private DataFetcherInterface mockDataFetcher;
    
    @Mock
    private ReportFormatterInterface mockReportFormatter;
    
    @Mock
    private ReportSenderInterface mockReportSender;
    
    private ReportService reportService;
    
    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        
        // Create ReportService with mocked dependencies
        reportService = new ReportService(mockDataFetcher, mockReportFormatter, mockReportSender);
    }
    
    /**
     * Test that demonstrates how we can verify the interactions between
     * the ReportService and its dependencies.
     * 
     * This is only possible because we've applied the Dependency Inversion Principle,
     * which allows us to inject mock implementations for testing.
     */
    @Test
    public void testGenerateMonthlyReport() {
        // Arrange
        int month = 5;
        int year = 2023;
        String recipient = "test@example.com";
        
        // Create sample data
        List<SalesData> sampleData = new ArrayList<>();
        sampleData.add(new SalesData("Electronics", 500.0, LocalDate.of(year, month, 15)));
        
        // Create sample growth percentages
        Map<String, Double> growthPercentages = new HashMap<>();
        growthPercentages.put("Electronics", 10.5);
        
        // Mock behavior
        when(mockDataFetcher.fetchSalesData(month, year)).thenReturn(sampleData);
        when(mockReportFormatter.formatReport(any(), anyDouble(), any())).thenReturn("Test Report");
        
        // Act
        reportService.generateMonthlyReport(month, year, recipient);
        
        // Assert - verify interactions
        verify(mockDataFetcher).fetchSalesData(month, year);
        verify(mockReportFormatter).formatReport(eq(sampleData), anyDouble(), any());
        verify(mockReportSender).sendReport("Test Report", recipient);
    }
    
    /**
     * BENEFITS OF DIP FOR TESTING:
     * 
     * 1. Isolation: We can test the ReportService in isolation from its dependencies
     * 2. Control: We can control the behavior of dependencies through mocking
     * 3. Verification: We can verify interactions between components
     * 4. Speed: Tests run faster without real implementations
     * 5. Reliability: Tests don't depend on external systems
     * 
     * Without DIP, we would have to test the entire chain of dependencies,
     * making tests slower, more complex, and less reliable.
     */
}