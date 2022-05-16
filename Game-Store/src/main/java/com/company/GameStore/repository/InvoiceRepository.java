package com.company.GameStore.repository;

import com.company.GameStore.DTO.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
