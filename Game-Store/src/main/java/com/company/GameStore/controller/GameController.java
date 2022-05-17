package com.company.GameStore.controller;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.exception.QueryNotFoundException;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    ServiceLayer service;

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames(@RequestParam(required = false) String studio, @RequestParam(required = false) String esrbRating) {
        if (studio != null && esrbRating != null) {
            return service.getGamesByStudioAndEsrbRating(studio, esrbRating);
        } else if (studio != null) {
            return service.getGamesByStudio(studio);
        } else if (esrbRating != null) {
            return service.getGamesByEsrbRating(esrbRating);
        }
        return service.getAllGames();
    }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Game> getGame(@PathVariable int id, @RequestParam(required = false) String title) {
        if (title != null) {
            return service.getGameByTitle(title);
        }
        if (service.getSingleGame(id).orElse(null) == null) {
            throw new QueryNotFoundException("No game with that title exists in our inventory.");
        }
        return service.getSingleGame(id);
    }

    @GetMapping("/games/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Game> getGameByTitle(@PathVariable String title) {
        if (service.getGameByTitle(title).orElse(null) == null) {
            throw new QueryNotFoundException("No game with that title exists in our inventory.");
        }
        return service.getGameByTitle(title);
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@Valid @RequestBody Game game) {
        System.out.println(game);
        return service.addGame(game);
    }

    @PutMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@Valid @RequestBody Game game, @PathVariable int id) {
        if (game.getGame_id() == null) {
            game.setGame_id(id);
        }
        if (id != game.getGame_id()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable id");
        }
        if (service.getSingleGame(id).orElse(null) == null) {
            throw new QueryNotFoundException("No game with that title exists in our inventory.");
        }

        service.updateGame(game);
    }

    @DeleteMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        if (service.getSingleGame(id).orElse(null) == null) {
            throw new QueryNotFoundException("No game with that title exists in our inventory.");
        }
        service.deleteGame(id);
    }
}
