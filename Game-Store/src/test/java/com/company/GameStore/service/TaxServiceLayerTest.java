package com.company.GameStore.service;

import com.company.GameStore.DTO.SalesTaxRate;
import com.company.GameStore.repository.SalesTaxRateRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TaxServiceLayerTest {
    @Autowired
    SalesTaxRateRepository salesTaxRateRepository;

    @Before
    public void setUp() throws Exception {
        salesTaxRateRepository = mock(SalesTaxRateRepository.class);

        when(salesTaxRateRepository.findByState("TX")).thenReturn(new SalesTaxRate("TX",.03));
    }

    @Test
    public void shouldReturnSalesTaxRateWhenFindingSalesTaxByState() {
        SalesTaxRate expectedSalesTaxRate = new SalesTaxRate("TX",.03);
        SalesTaxRate actualSalesTaxRate = salesTaxRateRepository.findByState("TX");

        assertEquals(expectedSalesTaxRate, actualSalesTaxRate);
    }
}