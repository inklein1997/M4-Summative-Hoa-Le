package com.company.GameStore.controller;

import com.company.GameStore.DTO.Console;
import com.company.GameStore.DTO.Game;
import com.company.GameStore.DTO.Tshirt;
import com.company.GameStore.service.ProcessingFeeLayer;
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
    TaxServiceLayer taxServiceLayer;

    @Autowired
    ProcessingFeeLayer processingFeeLayer;

    @Autowired
    ServiceLayer serviceLayer;

    @PostMapping("/loadData")
    @ResponseStatus(HttpStatus.CREATED)
    public void seedData() {
        serviceLayer.clearDatabase();
        taxServiceLayer.loadTaxes();
        processingFeeLayer.loadFee();

        serviceLayer.addGame(new Game("Miitopia", "E (Everyone)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 5));
        serviceLayer.addGame(new Game("Animal Crossing: New Horizons", "E (Everyone)", "In New Horizons, the player controls a character who moves to a deserted island after purchasing a getaway package from Tom Nook, accomplishes assigned tasks, and develops the island as they choose. ", 59.99, "Nintendo", 20));
        serviceLayer.addGame(new Game("Pokemon Legends: Arceus", "E (Everyone)", "Pokémon Legends: Arceus is an action role-playing game that preserves the core gameplay of past entries in the main line series.", 59.99, "Game Freak", 10));
        serviceLayer.addGame(new Game("Black Ops 4", "M (Mature)", "Black Ops 4 features gritty, grounded, fluid Multiplayer combat, the biggest Zombies offering ever with four full undead adventures, and Blackout, where the universe of Black Ops comes to life in two massive battle royale experiences.", 39.99, "Treyarch", 8));
        serviceLayer.addGame(new Game("Elden Ring", "M (Mature)", "Elden Ring is an action role-playing game played in a third-person perspective with gameplay focusing on combat and exploration", 59.99, "FromSoftware", 100));
        serviceLayer.addGame(new Game("Spider-Man: Miles Morales", "T (Teen)", "Miles Morales discovers explosive powers that set him apart from his mentor, Peter Parker. Master his unique, bio-electric venom blast attacks and covert camouflage power alongside spectacular web-slinging acrobatics, gadgets and skills.", 59.99, "Insomniac Games", 21));
        serviceLayer.addGame(new Game("Minecraft", "E (Everyone)", "Minecraft is a 3-D computer game where players can build anything. Set in a virtual world the game involves resource gathering, crafting items, building, and combat.", 29.99, "Nintendo", 51));
        serviceLayer.addGame(new Game("Lego Star Wars: The Skywalker Sage", "E (Everyone)", "Lego themed game articulating the story from Star Wars", 59.99, "Interactive Entertainment", 15));

        serviceLayer.addConsole(new Console("Playstation 5", "Sony", "1TB", "AMD Zen 2 CPU", 579.99, 20));
        serviceLayer.addConsole(new Console("Playstation 4 Pro", "Sony", "1TB", "AMD x86-64 Jaguar", 399.99, 15));
        serviceLayer.addConsole(new Console("Nintendo Switch", "Nintendo", "32GB", "Quad-core ARM Cortex", 299.99, 60));
        serviceLayer.addConsole(new Console("Xbox Series X", "Microsoft", "1TB", "AMD Zen 2 CPU", 499.99, 55));
        serviceLayer.addConsole(new Console("Xbox Series S", "Microsoft", "512GB", "AMD Zen 2 CPU", 399.99, 75));

        serviceLayer.addTshirt(new Tshirt("small","red","A lovely red T-shirt",9.99,8));
        serviceLayer.addTshirt(new Tshirt("medium","red","A lovely red T-shirt",9.99,11));
        serviceLayer.addTshirt(new Tshirt("large","red","A lovely red T-shirt",9.99,7));
        serviceLayer.addTshirt(new Tshirt("extra-large","red","A lovely red T-shirt",11.99,4));
        serviceLayer.addTshirt(new Tshirt("small","yellow/striped","A clean striped shirt",7.99,7));
        serviceLayer.addTshirt(new Tshirt("medium","yellow/striped","A clean striped shirt",7.99,20));
        serviceLayer.addTshirt(new Tshirt("large","yellow/striped","A clean striped shirt",7.99,15));
        serviceLayer.addTshirt(new Tshirt("extra-large","yellow/striped","A clean striped shirt",9.99,5));
        serviceLayer.addTshirt(new Tshirt("extra-extra-large","yellow/striped","A clean striped shirt",10.99,3));
    }
}
