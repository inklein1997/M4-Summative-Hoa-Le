package com.company.GameStore.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/*create table if not exists processing_fee (
    product_type varchar(20) not null,
    fee decimal (4,2)
);

create unique index ix_product_type_fee on processing_fee (product_type);*/
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "processing_fee", indexes = {@Index(name = "ix_product_type_fee",columnList = "product_type")})
public class ProcessingFee {

    @NotNull
    @Size(max = 20)
    private String product_type;

    @NotNull
    @Digits(integer = 4,fraction = 2)
    private double fee;

    public ProcessingFee(String product_type, double fee) {
        this.product_type = product_type;
        this.fee = fee;
    }

    public ProcessingFee() {
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "ProcessingFee{" +
                "product_type='" + product_type + '\'' +
                ", fee=" + fee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingFee that = (ProcessingFee) o;
        return Double.compare(that.fee, fee) == 0 && Objects.equals(product_type, that.product_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_type, fee);
    }
}
