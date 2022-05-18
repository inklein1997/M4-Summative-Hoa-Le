package com.company.GameStore.service;

import com.company.GameStore.DTO.ProcessingFee;
import com.company.GameStore.DTO.SalesTaxRate;
import com.company.GameStore.repository.ProcessingFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessingFeeLayer {
    @Autowired
    ProcessingFeeRepository processingFeeRepository;

    public void loadFee(){
        processingFeeRepository.deleteAll();
        processingFeeRepository.save(new ProcessingFee("Consoles", 14.99));
        processingFeeRepository.save(new ProcessingFee("T-shirts", 1.98));
        processingFeeRepository.save(new ProcessingFee("Games", 1.49));
    }
}
