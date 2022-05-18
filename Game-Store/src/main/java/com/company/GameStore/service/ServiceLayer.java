package com.company.GameStore.service;

import com.company.GameStore.DTO.*;

import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.repository.*;

//import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
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
    ProcessingFeeRepository processingFeeRepository;

    int availableAmount;
    int updatedAmount;

    @Autowired
    public ServiceLayer(GameRepository gameRepository, ConsoleRepository consoleRepository, TshirtRepository tshirtRepository,InvoiceRepository invoiceRepository, SalesTaxRateRepository salesTaxRateRepository,ProcessingFeeRepository processingFeeRepository) {
        this.gameRepository = gameRepository;
        this.consoleRepository = consoleRepository;
        this.tshirtRepository = tshirtRepository;
        this.invoiceRepository = invoiceRepository;
        this.salesTaxRateRepository = salesTaxRateRepository;
        this.processingFeeRepository = processingFeeRepository;
    }

    // CLEAR DATABASE
    public void clearDatabase() {
        gameRepository.deleteAll();
        tshirtRepository.deleteAll();
        consoleRepository.deleteAll();
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
        consoleRepository.save(console);
    }

    public void deleteConsole(int id) {
        consoleRepository.deleteById(id);
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
        double salesTax = applyTaxRate(invoice);
        double processingFee = applyProcessingFee(invoice);
        double subtotal = calculateSubtotal(invoice);
        double total = calculateTotal(subtotal, processingFee, salesTax);

        updatedInvoice.setTax(salesTax);
        updatedInvoice.setProcessing_fee(processingFee);
        updatedInvoice.setSubtotal(subtotal);
        updatedInvoice.setTotal(total);

        decreaseItemQuantity(updatedInvoice);
        return invoiceRepository.save(updatedInvoice);
    }


    public double formatDouble(double d) {
        return Double.parseDouble(String.format("%,.2f", d));
    }

    public double applyTaxRate(Invoice invoice) {
        double priceBeforeTax = invoice.getQuantity() * invoice.getUnit_price();
        double taxRate = salesTaxRateRepository.findByState(invoice.getState()).getRate();
        return formatDouble(priceBeforeTax * taxRate);
    }

    public double applyProcessingFee(Invoice invoice){
        double processingFee = processingFeeRepository.findByProductType(invoice.getItem_type()).getFee();
        if (invoice.getQuantity() >10 ){
            processingFee += 15.49;
        }
        return  formatDouble(processingFee);
    }

    public double calculateTotal(double subtotal, double processingFee, double salesTax) {
        return formatDouble(subtotal + processingFee + salesTax);
    }

    public double calculateSubtotal(Invoice invoice) {
        return formatDouble(invoice.getQuantity() * invoice.getUnit_price());
    }

    public int checkQuantity(int requestedAmount, int availableAmount) {
        if (availableAmount > requestedAmount) {
            return availableAmount - requestedAmount;
        } else {
            throw new DataIntegrityViolationException("Unfortunately, there is not enough of that item in stock to purchase that many.  Please add to stock or order less");
        }
    }

    public int getItemQuantity(Invoice invoice) {
        int itemId = invoice.getItem_id();

        switch (invoice.getItem_type()) {
            case "Games" :
                return getSingleGame(itemId).get().getQuantity();
            case "T-shirts" :
                return getSingleTshirt(itemId).get().getQuantity();
            case "Consoles" :
                return getSingleConsole(itemId).get().getQuantity();
            default:
                return -1;
        }
    }

    @Transactional
    public void decreaseItemQuantity(Invoice invoice) {

        int requestedAmount = invoice.getQuantity();

        switch (invoice.getItem_type()) {
            case "Games":
                Game game = getSingleGame(invoice.getItem_id()).get();
                availableAmount = game.getQuantity();
                updatedAmount = checkQuantity(requestedAmount, availableAmount);
                game.setQuantity(updatedAmount);
                updateGame(game);
                break;
            case "Consoles":
                Console console = getSingleConsole(invoice.getItem_id()).get();
                availableAmount = console.getQuantity();
                updatedAmount = availableAmount - requestedAmount;
                console.setQuantity(updatedAmount);
                updateConsole(console);
                break;
            case "T-shirts":
                Tshirt tshirt = getSingleTshirt(invoice.getItem_id()).get();
                availableAmount = tshirt.getQuantity();
                updatedAmount = availableAmount - requestedAmount;
                tshirt.setQuantity(updatedAmount);
                updateTshirt(tshirt);
                break;
        }
    }

}


