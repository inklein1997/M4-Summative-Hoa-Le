package com.company.GameStore.controller;

import com.company.GameStore.DTO.Tshirt;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
//import org.springframework.http.converter.InputMismatchException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@RestController
public class TshirtController {

    @Autowired
    ServiceLayer service;

    //GET ALL
    @GetMapping("/tshirts")
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getAllTshirt(){
        return service.getAllTshirt();
    }

    //GET by param id
    @GetMapping("/tshirts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Tshirt> getTshirtById(@PathVariable int id){
        return service.getSingleTshirt(id);
    }

    //POST/CREATE Tshirt
    @PostMapping("/tshirts")
    @ResponseStatus(HttpStatus.CREATED)
    public  Tshirt createTshirt(@RequestBody @Valid Tshirt tshirt){

        try{return service.addTshirt(tshirt);}catch (InputMismatchException e){
            throw e;
        }
    }

    @PutMapping("/tshirts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTshirt (@RequestBody Tshirt tshirt, @PathVariable int id){
        if (tshirt.getId() == null){
            tshirt.setId(id);
        }
        if(id != tshirt.getId()){
            throw new DataIntegrityViolationException("Id does not match @PathVariable id");
        }
        service.updateTshirt(tshirt);
    }

    @DeleteMapping("/tshirts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@PathVariable int id){
        service.deleteTshirt(id);
    }
}
