package com.company.GameStore.repository;

import com.company.GameStore.DTO.Console;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsoleRepository extends JpaRepository<Console,Integer> {
    List<Console> findByManufacturer(String manufacturer);
}
