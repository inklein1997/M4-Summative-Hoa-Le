package com.company.GameStore.repository;

import com.company.GameStore.DTO.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
