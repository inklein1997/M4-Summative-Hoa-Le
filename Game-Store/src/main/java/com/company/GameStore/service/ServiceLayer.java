package com.company.GameStore.service;

import com.company.GameStore.DTO.Console;
import com.company.GameStore.DTO.Game;
import com.company.GameStore.DTO.Tshirt;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.TshirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer {

    TshirtRepository tshirtRepository;
    GameRepository gameRepository;

    @Autowired
    public void ServiceLayer(GameRepository gameRepository, TshirtRepository tshirtRepository) {
        this.gameRepository = gameRepository;
        this.tshirtRepository = tshirtRepository;
    }

    // CLEAR DATABASE
    public void clearDatabase() {
        gameRepository.deleteAll();
        tshirtRepository.deleteAll();
    }

    //Jpa Searches
    public List<Tshirt> getTshirtByColor(String color){return tshirtRepository.findByColor(color);}
    public List<Tshirt> getTshirtBySize(String size){return tshirtRepository.findBySize(size);}
    public List<Tshirt> getTshirtByColorAndSize(String color, String size){return tshirtRepository.findByColorAndSize(color,size);}

    //TShirt CRUD
    public List<Tshirt> getAllTshirt(){return tshirtRepository.findAll();}
    public Optional<Tshirt> getSingleTshirt(int id) {return tshirtRepository.findById(id);}
    public Tshirt addTshirt(Tshirt tshirt) {return tshirtRepository.save(tshirt);}

    public void updateTshirt(Tshirt tshirt) {tshirtRepository.save(tshirt);}

    public void deleteTshirt(int id) {tshirtRepository.deleteById(id);}
    // GAME CRUD OPERATIONS
    public List<Game> getAllGames() {return gameRepository.findAll();}

    public List<Game> getGamesByStudio(String studio) { return gameRepository.findByStudio(studio); }

    public List<Game> getGamesByEsrbRating(String esrbRating) { return gameRepository.findByEsrbRating(esrbRating); }

    public List<Game> getGamesByStudioAndEsrbRating(String studio, String esrbRating) { return gameRepository.findByStudioAndEsrbRating(studio, esrbRating); }

    public Optional<Game> getGameByTitle(String title) { return gameRepository.findByTitle(title); }

    public Optional<Game> getSingleGame(int id) {
        return gameRepository.findById(id);
    }

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }

    // CONSOLE CRUD OPERATIONS
    public Optional<Console> getConsoleByManufacturer(String manufacturer) {
        return null;
    }

    public List<Console> getConsolesByManufacturer(String manufacturer) {
        return null;
    }

    public List<Console> getAllConsoles() {
        return null;
    }

    public Optional<Console> getSingleConsole(int id) {
        return null;
    }

    public Console addConsole(Console console) {
        return console;
    }

    public void updateConsole(Console console) {
    }

    public void deleteConsole(int id) {
    }
}
