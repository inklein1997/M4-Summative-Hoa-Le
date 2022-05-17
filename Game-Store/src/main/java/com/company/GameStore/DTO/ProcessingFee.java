package com.company.GameStore.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
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

    @Id
    @NotNull
    @Size(max = 20)
    @Column(name = "product_type")
    private String productType;

    @NotNull
    @Digits(integer = 4,fraction = 2)
    private double fee;

    public ProcessingFee(String productType, double fee) {
        this.productType = productType;
        this.fee = fee;
    }

    public ProcessingFee() {
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingFee that = (ProcessingFee) o;
        return Double.compare(that.fee, fee) == 0 && Objects.equals(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, fee);
    }

    @Override
    public String toString() {
        return "ProcessingFee{" +
                "productType='" + productType + '\'' +
                ", fee=" + fee +
                '}';
    }
}
