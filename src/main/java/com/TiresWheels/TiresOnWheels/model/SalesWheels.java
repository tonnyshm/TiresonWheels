package com.TiresWheels.TiresOnWheels.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class SalesWheels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private BigDecimal wheelSellingPrice;
    private LocalDate dateWheelSold;

    @ManyToOne
    @JoinColumn(name = "stockWheelId")
    private StockWheels stockWheels;

}
