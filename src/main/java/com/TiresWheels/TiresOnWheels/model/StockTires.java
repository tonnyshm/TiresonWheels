package com.TiresWheels.TiresOnWheels.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;



@Data
@Entity
public class StockTires {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int tireSize;
    private String tireBrand;
    private String tireDimensions; //diameter, width, sidewall, circumference of a tire
    private LocalDate dateCreated;

    private BigDecimal tirePrice;
    private String serialNumberTire;
    @Enumerated(EnumType.STRING)
    private StockOrigin stockOriginTire;
    private String status; // null (not sold) or "Sold"


    @OneToMany(mappedBy = "stockTires")
    private List<SalesTires> tireSales;
}
