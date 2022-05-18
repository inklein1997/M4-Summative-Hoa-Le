package com.company.GameStore.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "console")
public class Console {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int console_id;
    @NotBlank
    @Size(max = 50)
    private String model;
    @NotBlank
    @Size(max = 50)
    private String manufacturer;
    @NotBlank
    @Size(max = 20)
    private String memory_amount;
    @NotBlank
    @Size(max = 20)
    private String processor;
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private double price;
    @NotNull
    private int quantity;

    public Console() {
    }

    public Console(int console_id, String model, String manufacturer, String memory_amount, String processor, double price, int quantity) {
        this.console_id = console_id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.memory_amount = memory_amount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public Console(String model, String manufacturer, String memory_amount, String processor, double price, int quantity) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.memory_amount = memory_amount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public int getConsole_id() {
        return console_id;
    }

    public void setConsole_id(int console_id) {
        this.console_id = console_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemory_amount() {
        return memory_amount;
    }

    public void setMemory_amount(String memory_amount) {
        this.memory_amount = memory_amount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
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
        Console console = (Console) o;
        return console_id == console.console_id && Double.compare(console.price, price) == 0 && quantity == console.quantity && Objects.equals(model, console.model) && Objects.equals(manufacturer, console.manufacturer) && Objects.equals(memory_amount, console.memory_amount) && Objects.equals(processor, console.processor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(console_id, model, manufacturer, memory_amount, processor, price, quantity);
    }

    @Override
    public String toString() {
        return "Console{" +
                "console_id=" + console_id +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", memory_amount='" + memory_amount + '\'' +
                ", processor='" + processor + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
