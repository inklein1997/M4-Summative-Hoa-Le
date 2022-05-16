package com.company.GameStore.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "tshirt")
public class Tshirt {

    //ask for bad request posting.
    @Id//for primary key of current entity
    @Column(name = "t_shirt_id")
    @GeneratedValue(strategy = GenerationType.AUTO)//to auto_increment
    private Integer id;
    @NotNull(message = "size/other inputs are EMPTY or INVALID")
    @Size(max = 20)
    private String size;
    @NotNull(message = "color/other inputs are Empty or INVALID")
    @Size(max = 20)
    private String color;
    @NotNull(message = "description/other inputs are Empty or INVALID")
    @Size(max = 255)
    private String description;
    @NotNull(message = "price/other inputs are INVALID")
    @Digits(integer = 5, fraction = 2)//Whole,Decimal point.
    private double price;
    @NotNull(message = "quantity/other inputs are INVALID")
    private int quantity;

    public Tshirt() {
    }

    public Tshirt(String size, String color, String description, double price, int quantity) {
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Tshirt(Integer id, String size, String color, String description, double price, int quantity) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tshirt tshirt = (Tshirt) o;
        return Double.compare(tshirt.price, price) == 0 && quantity == tshirt.quantity && Objects.equals(id, tshirt.id) && Objects.equals(size, tshirt.size) && Objects.equals(color, tshirt.color) && Objects.equals(description, tshirt.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, color, description, price, quantity);
    }

    @Override
    public String toString() {
        return "Tshirt{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
