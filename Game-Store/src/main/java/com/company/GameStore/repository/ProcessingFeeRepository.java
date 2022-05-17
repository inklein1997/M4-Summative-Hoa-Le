package com.company.GameStore.repository;

import com.company.GameStore.DTO.ProcessingFee;
import com.company.GameStore.DTO.SalesTaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingFeeRepository extends JpaRepository<ProcessingFee,String> {
    ProcessingFee findByProductType(String productType);
}
