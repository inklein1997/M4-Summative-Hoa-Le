package com.company.GameStore.service;

import com.company.GameStore.DTO.SalesTaxRate;
import com.company.GameStore.repository.SalesTaxRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceLayer {

    @Autowired
    SalesTaxRateRepository salesTaxRateRepository;

    public void loadTaxes() {
        salesTaxRateRepository.deleteAll();
        salesTaxRateRepository.save(new SalesTaxRate("AL", .05));
        salesTaxRateRepository.save(new SalesTaxRate("AK", .06));
        salesTaxRateRepository.save(new SalesTaxRate("AZ", .04));
        salesTaxRateRepository.save(new SalesTaxRate("AR", .06));
        salesTaxRateRepository.save(new SalesTaxRate("CA", .06));
        salesTaxRateRepository.save(new SalesTaxRate("CO", .04));
        salesTaxRateRepository.save(new SalesTaxRate("CT", .03));
        salesTaxRateRepository.save(new SalesTaxRate("DE", .05));
        salesTaxRateRepository.save(new SalesTaxRate("FL", .06));
        salesTaxRateRepository.save(new SalesTaxRate("GA", .07));
        salesTaxRateRepository.save(new SalesTaxRate("HI", .05));
        salesTaxRateRepository.save(new SalesTaxRate("ID", .03));
        salesTaxRateRepository.save(new SalesTaxRate("IL", .05));
        salesTaxRateRepository.save(new SalesTaxRate("IN", .05));
        salesTaxRateRepository.save(new SalesTaxRate("IA", .04));
        salesTaxRateRepository.save(new SalesTaxRate("KS", .06));
        salesTaxRateRepository.save(new SalesTaxRate("KY", .04));
        salesTaxRateRepository.save(new SalesTaxRate("LA", .05));
        salesTaxRateRepository.save(new SalesTaxRate("ME", .03));
        salesTaxRateRepository.save(new SalesTaxRate("MD", .07));
        salesTaxRateRepository.save(new SalesTaxRate("MA", .05));
        salesTaxRateRepository.save(new SalesTaxRate("MI", .06));
        salesTaxRateRepository.save(new SalesTaxRate("MN", .06));
        salesTaxRateRepository.save(new SalesTaxRate("MS", .05));
        salesTaxRateRepository.save(new SalesTaxRate("MO", .03));
        salesTaxRateRepository.save(new SalesTaxRate("MT", .03));
        salesTaxRateRepository.save(new SalesTaxRate("NE", .04));
        salesTaxRateRepository.save(new SalesTaxRate("NV", .04));
        salesTaxRateRepository.save(new SalesTaxRate("NH", .06));
        salesTaxRateRepository.save(new SalesTaxRate("NJ", .05));
        salesTaxRateRepository.save(new SalesTaxRate("NM", .05));
        salesTaxRateRepository.save(new SalesTaxRate("NY", .06));
        salesTaxRateRepository.save(new SalesTaxRate("NC", .05));
        salesTaxRateRepository.save(new SalesTaxRate("ND", .05));
        salesTaxRateRepository.save(new SalesTaxRate("OH", .04));
        salesTaxRateRepository.save(new SalesTaxRate("OK", .04));
        salesTaxRateRepository.save(new SalesTaxRate("OR", .07));
        salesTaxRateRepository.save(new SalesTaxRate("PA", .06));
        salesTaxRateRepository.save(new SalesTaxRate("RI", .06));
        salesTaxRateRepository.save(new SalesTaxRate("SC", .06));
        salesTaxRateRepository.save(new SalesTaxRate("SD", .06));
        salesTaxRateRepository.save(new SalesTaxRate("TN", .05));
        salesTaxRateRepository.save(new SalesTaxRate("TX", .03));
        salesTaxRateRepository.save(new SalesTaxRate("UT", .04));
        salesTaxRateRepository.save(new SalesTaxRate("VT", .07));
        salesTaxRateRepository.save(new SalesTaxRate("VA", .06));
        salesTaxRateRepository.save(new SalesTaxRate("WA", .05));
        salesTaxRateRepository.save(new SalesTaxRate("WV", .05));
        salesTaxRateRepository.save(new SalesTaxRate("WI", .03));
        salesTaxRateRepository.save(new SalesTaxRate("WY", .04));
    }

}
