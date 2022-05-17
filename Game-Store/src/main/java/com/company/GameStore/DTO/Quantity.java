package com.company.GameStore.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "quantity")
public class Quantity {
    @NotNull
    private int quatity_game;
    @NotNull
    private int quatity_console;
    @NotNull
    private int quatity_tshirt;

    public Quantity(int quatity_game, int quatity_console, int quatity_tshirt) {
        this.quatity_game = quatity_game;
        this.quatity_console = quatity_console;
        this.quatity_tshirt = quatity_tshirt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return quatity_game == quantity.quatity_game && quatity_console == quantity.quatity_console && quatity_tshirt == quantity.quatity_tshirt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quatity_game, quatity_console, quatity_tshirt);
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "quatity_game=" + quatity_game +
                ", quatity_console=" + quatity_console +
                ", quatity_tshirt=" + quatity_tshirt +
                '}';
    }
}
