package com.company.GameStore.repository;

import com.company.GameStore.DTO.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameRepositoryTest {
    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() {
        gameRepository.deleteAll();
    }

    /* ------------------ Happy Paths ------------------ */
    @Test
    public void addGetDeleteGame() {
        Game game = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        gameRepository.save(game);
        Optional<Game> game1 = gameRepository.findById(game.getGame_id());

        assertEquals(game1, game);

        gameRepository.deleteById(game.getGame_id());

        game1 = gameRepository.findById(game.getGame_id());

        assertFalse(game1.isPresent());
    }

    @Test
    public void updateGame() {
        Game game = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        game = gameRepository.save(game);

        game.setDescription("New description");

        gameRepository.save(game);

        Optional<Game> game1 = gameRepository.findById(game.getGame_id());
        assertEquals(game1.get(), game);
    }

    @Test
    public void getAllGames() {
        Game game = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game anotherGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        game = gameRepository.save(game);
        anotherGame = gameRepository.save(anotherGame);

        List<Game> gameList = gameRepository.findAll();
        assertEquals(gameList.size(), 2);
    }

    @Test
    public void getGamesByEsrbRating() {
        Game game = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game anotherGame = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        game = gameRepository.save(game);
        anotherGame = gameRepository.save(anotherGame);

        List<Game> expectedGameList = new ArrayList<>();

        expectedGameList.add(game);
        expectedGameList.add(anotherGame);


        List<Game> actualGameList = gameRepository.findByEsrbRating("E (Everyone)");

        assertEquals(expectedGameList, actualGameList);

    }

    @Test
    public void getGamesByStudio() {
        Game game = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game anotherGame = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        game = gameRepository.save(game);
        anotherGame = gameRepository.save(anotherGame);

        List<Game> expectedGameList = new ArrayList<>();

        expectedGameList.add(game);
        expectedGameList.add(anotherGame);


        List<Game> actualGameList = gameRepository.findByStudio("Nintendo");

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void getGamesByEsrbRatingAndStudio() {
        Game game = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game anotherGame = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        game = gameRepository.save(game);
        anotherGame = gameRepository.save(anotherGame);

        List<Game> expectedGameList = new ArrayList<>();

        expectedGameList.add(game);
        expectedGameList.add(anotherGame);


        List<Game> actualGameList = gameRepository.findByStudioAndEsrbRating("Nintendo", "E (Everyone)");

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void getGameByTitle() {
        Game game = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        game = gameRepository.save(game);
        
        Optional<Game> actualGame = gameRepository.findByTitle("Nintendo Switch Sports");

        assertEquals(game, actualGame);
    }

}