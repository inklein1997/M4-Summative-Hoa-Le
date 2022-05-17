package com.company.GameStore.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "sales_tax_rate", indexes = {@Index(name = "ix_state_rate", columnList = "state")})
public class SalesTaxRate {
    @Id
    @NotNull
    @Size(min = 2, max = 2)
    private String state;
    @NotNull
    @Digits(integer = 3,fraction = 2)
    private double rate;

    public SalesTaxRate(String state, double rate) {
        this.state = state;
        this.rate = rate;
    }

    public SalesTaxRate() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesTaxRate that = (SalesTaxRate) o;
        return Double.compare(that.rate, rate) == 0 && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, rate);
    }

    @Override
    public String toString() {
        return "SalesTaxRate{" +
                "state='" + state + '\'' +
                ", rate=" + rate +
                '}';
    }
}
