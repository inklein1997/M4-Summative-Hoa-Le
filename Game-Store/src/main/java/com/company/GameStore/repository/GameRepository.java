package com.company.GameStore.repository;

import com.company.GameStore.DTO.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByStudio(String studio);
    List<Game> findByEsrbRating(String esrbRating);
    List<Game> findByStudioAndEsrbRating(String studio, String esrbRating);
    Optional<Game> findByTitle(String title);
}
