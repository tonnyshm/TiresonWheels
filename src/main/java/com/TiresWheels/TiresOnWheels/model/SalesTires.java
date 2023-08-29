package com.TiresWheels.TiresOnWheels.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class SalesTires {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private BigDecimal tireSellingPrice;
    private LocalDate dateTireSold;

    @ManyToOne
    @JoinColumn(name = "stockTireId")
    private StockTires stockTires;

    @Override
    public String toString() {
        return "SalesTires{" +
                "id=" + id +
                ", dateTireSold=" + dateTireSold +
                ", tireSellingPrice=" + tireSellingPrice +
                '}';
    }

}
