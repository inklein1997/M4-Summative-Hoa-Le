package com.company.GameStore.service;

import com.company.GameStore.DTO.*;

import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer {

    TshirtRepository tshirtRepository;
    GameRepository gameRepository;
    ConsoleRepository consoleRepository;
    InvoiceRepository invoiceRepository;
    @Autowired
    SalesTaxRateRepository salesTaxRateRepository;

    @Autowired
    public ServiceLayer(GameRepository gameRepository, ConsoleRepository consoleRepository, TshirtRepository tshirtRepository,InvoiceRepository invoiceRepository) {

        this.gameRepository = gameRepository;
        this.consoleRepository = consoleRepository;
        this.tshirtRepository = tshirtRepository;
        this.invoiceRepository = invoiceRepository;
    }

    // CLEAR DATABASE
    public void clearDatabase() {
        gameRepository.deleteAll();
        tshirtRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

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

    //Jpa Searches
    public List<Tshirt> getTshirtByColor(String color){return tshirtRepository.findByColor(color);}
    public List<Tshirt> getTshirtBySize(String size){return tshirtRepository.findBySize(size);}
    public List<Tshirt> getTshirtByColorAndSize(String color, String size){return tshirtRepository.findByColorAndSize(color,size);}

    //TShirt CRUD
    public List<Tshirt> getAllTshirt(){return tshirtRepository.findAll();}
    public Optional<Tshirt> getSingleTshirt(int id) {
        Optional<Tshirt> tshirt = tshirtRepository.findById(id);
        return tshirt.isPresent()? Optional.of(tshirt.get()) : null;
    }
    public Tshirt addTshirt(Tshirt tshirt) {return tshirtRepository.save(tshirt);}

    public void updateTshirt(Tshirt tshirt) {tshirtRepository.save(tshirt);}

    public void deleteTshirt(int id) {tshirtRepository.deleteById(id);}

    // GAME CRUD OPERATIONS
    public List<Game> getAllGames() {return gameRepository.findAll();}

    public List<Game> getGamesByStudio(String studio) { return gameRepository.findByStudio(studio); }

    public List<Game> getGamesByEsrbRating(String esrbRating) { return gameRepository.findByEsrbRating(esrbRating); }

    public List<Game> getGamesByStudioAndEsrbRating(String studio, String esrbRating) { return gameRepository.findByStudioAndEsrbRating(studio, esrbRating); }

    public Optional<Game> getGameByTitle(String title) { return gameRepository.findByTitle(title); }

    public Optional<Game> getSingleGame(int id) { return gameRepository.findById(id); }

    public Game addGame(Game game) { return gameRepository.save(game); }

    public void updateGame(Game game) { gameRepository.save(game); }

    public void deleteGame(int id) { gameRepository.deleteById(id); }

    // CONSOLE CRUD OPERATIONS  
    public List<Console> getConsolesByManufacturer(String manufacturer) {
        return consoleRepository.findByManufacturer(manufacturer);
    }

    public List<Console> getAllConsoles(){
        return consoleRepository.findAll();
    }

    public Optional<Console> getSingleConsole(int id) {
        return consoleRepository.findById(id);
    }

    public Console addConsole(Console console) {
        return consoleRepository.save(console);
    }

    public void updateConsole(Console console) {
    }

    public void deleteConsole(int id) {
    }



    // Invoice CRUD -- Do not need to update / delete

    public List<Invoice> getAllInvoices() { return invoiceRepository.findAll(); }

    public Optional<Invoice> getInvoiceById(int id) {
        if (invoiceRepository.findById(id).orElse(null) == null) {
            throw new QueryNotFoundException("An invoice with that ID does not exist yet.");
        }
        return invoiceRepository.findById(id);
    }

    public Invoice addInvoice(Invoice invoice) { return invoiceRepository.save(invoice); }

}

//    public void clearDatabase() {
//    }
//
//    public Object getAllConsoles() {
//    }

