package com.company.GameStore.controller;

import com.company.GameStore.DTO.Invoice;
import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if (serviceLayer.getInvoiceById(id).orElse(null) == null) {
            throw new QueryNotFoundException("No game with that title exists in our inventory.");
        }
        return serviceLayer.getInvoiceById(id);
    }

    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice createInvoice(@Valid @RequestBody Invoice invoice) {
        return serviceLayer.addInvoice(invoice);
    }

}
