package com.company.GameStore.controller;

import com.company.GameStore.DTO.Invoice;
import com.company.GameStore.exception.NotEnoughInStockException;
import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.service.ServiceLayer;
import com.company.GameStore.service.TaxServiceLayer;
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
    @Autowired
    TaxServiceLayer taxServiceLayer;

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
        if (taxServiceLayer.findSalesTaxRateByState(invoice.getState()) == null) {
            throw new QueryNotFoundException(invoice.getState() + " is not a valid state code");
        }
        if (invoice.getQuantity() <= 0) {
            throw new IllegalArgumentException("You must purchase at least 1 item");
        }
        if (invoice.getQuantity() > serviceLayer.getItemQuantity(invoice)) {
            throw new NotEnoughInStockException("You cannot buy that many.  There are currently only " + serviceLayer.getItemQuantity(invoice) + " units avaiable.");
        }
        return serviceLayer.addInvoice(invoice);
    }

}
