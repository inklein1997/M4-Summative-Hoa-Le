package com.company.GameStore.repository;

import com.company.GameStore.DTO.ProcessingFee;
import com.company.GameStore.DTO.SalesTaxRate;
import org.apache.tomcat.jni.Proc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProcessingFeeRepositoryTest {

    @Autowired
    ProcessingFeeRepository processingFeeRepository;

    @Before
    public void setUp() {
        processingFeeRepository.deleteAll();

        processingFeeRepository.save(new ProcessingFee("Games",1.49));
        processingFeeRepository.save(new ProcessingFee("T-shirts", 1.98));
        processingFeeRepository.save(new ProcessingFee("Consoles", 14.99));
    }

    @Test
    public void shouldFindSalesTaxByState() {
        ProcessingFee expectedProcessingFeeGames = new ProcessingFee("Games",1.49);
        ProcessingFee actualProcessingFeeGames = processingFeeRepository.findByProductType("Games");

        ProcessingFee expectedProcessingFeeTshirts = new ProcessingFee("T-shirts",1.98);
        ProcessingFee actualProcessingFeeTshirts = processingFeeRepository.findByProductType("T-shirts");

        ProcessingFee expectedProcessingFeeConsoles = new ProcessingFee("Consoles",14.99);
        ProcessingFee actualProcessingFeeConsoles = processingFeeRepository.findByProductType("Consoles");

        assertEquals(expectedProcessingFeeGames, actualProcessingFeeGames);
        assertEquals(expectedProcessingFeeTshirts, actualProcessingFeeTshirts);
        assertEquals(expectedProcessingFeeConsoles, actualProcessingFeeConsoles);

    }
}