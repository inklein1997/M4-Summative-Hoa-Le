package com.company.GameStore.controller;

import com.company.GameStore.service.ServiceLayer;
import com.company.GameStore.service.TaxServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadData {

    @Autowired
    TaxServiceLayer serviceLayer;

    @PostMapping("/loadData")
    @ResponseStatus(HttpStatus.CREATED)
    public void seedData() {
        serviceLayer.loadTaxes();
    }
}
