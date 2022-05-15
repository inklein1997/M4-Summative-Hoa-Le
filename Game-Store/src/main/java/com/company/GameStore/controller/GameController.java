package com.company.GameStore.controller;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    ServiceLayer service;

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames(@RequestParam(required = false) String studio) {
        if (studio != null) {
            return service.getGamesByStudio(studio);
        }
        return service.getAllGames();
    }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Game> getGame(@PathVariable int id) {
        return service.getSingleGame(id);
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        System.out.println(game);
        return service.addGame(game);
    }

    @PutMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody Game game, @PathVariable int id) {
        if (game.getGame_id() == null) {
            game.setGame_id(id);
        }
        if (id != game.getGame_id()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable id");
        }
        service.updateGame(game);
    }

    @DeleteMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        service.deleteGame(id);
    }
}
