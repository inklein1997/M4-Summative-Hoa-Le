package com.company.GameStore.service;

import com.company.GameStore.DTO.*;

import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
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
    SalesTaxRateRepository salesTaxRateRepository;

    @Autowired
    public ServiceLayer(GameRepository gameRepository, ConsoleRepository consoleRepository, TshirtRepository tshirtRepository,InvoiceRepository invoiceRepository, SalesTaxRateRepository salesTaxRateRepository) {

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

    public Invoice addInvoice(Invoice invoice) {
        Invoice updatedInvoice = invoice;
        updatedInvoice.setTax(applyTaxRate(invoice));

        return invoiceRepository.save(updatedInvoice);
    }

    public double applyTaxRate(Invoice invoice) {
        double priceBeforeTax = invoice.getQuantity() * invoice.getUnit_price();
        System.out.println(priceBeforeTax);
        System.out.println(salesTaxRateRepository.findByState("TX").getRate());
        double taxRate = salesTaxRateRepository.findByState(invoice.getState()).getRate();
        System.out.println(taxRate);
        return priceBeforeTax * taxRate;
    }

}

