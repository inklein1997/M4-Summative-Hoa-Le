package com.company.GameStore.repository;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.DTO.SalesTaxRate;
import com.company.GameStore.service.TaxServiceLayer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SalesTaxRateRepositoryTest {

    @Autowired
    SalesTaxRateRepository salesTaxRateRepository;

    @Before
    public void setUp() {
        salesTaxRateRepository.deleteAll();

        salesTaxRateRepository.save(new SalesTaxRate("AZ",.04));
    }

    @Test
    public void shouldFindSalesTaxByState() {
        SalesTaxRate expectedSalesTaxRate = new SalesTaxRate("AZ",.04);
        SalesTaxRate actualSalesTaxRate = salesTaxRateRepository.findByState("AZ");

        assertEquals(expectedSalesTaxRate, actualSalesTaxRate);
    }
}