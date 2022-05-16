package com.company.GameStore.controller;

import com.company.GameStore.DTO.Invoice;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping("/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return serviceLayer.getAllInvoices();
    }

    @GetMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Invoice> getSingleInvoice(@PathVariable int id) {
        return serviceLayer.getInvoiceById(id);
    }

    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return serviceLayer.createInvoice(invoice);
    }

}
