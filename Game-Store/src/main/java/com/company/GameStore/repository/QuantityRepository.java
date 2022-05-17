package com.company.GameStore.repository;


import com.company.GameStore.DTO.Quantity;

import java.util.List;

public interface QuantityRepository {
    List<Quantity> findByQuantityGame(int quantity_game);
    List<Quantity>findByQuantityConsole(int quantity_console);
    List<Quantity>findByQuantityTshirt(int quantity_tshirt);
}
