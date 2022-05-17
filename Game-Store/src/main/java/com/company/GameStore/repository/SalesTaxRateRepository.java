package com.company.GameStore.repository;

import com.company.GameStore.DTO.SalesTaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Because the unique index is a String, we pass String as the second generic.
@Repository
public interface SalesTaxRateRepository extends JpaRepository<SalesTaxRate, String> {
    SalesTaxRate findByState(String state);
}
