package com.TiresWheels.TiresOnWheels.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class StockWheels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int wheelSize;
    private String wheelBrand;
    private LocalDate dateCreated;

    private BigDecimal wheelPrice;
    private String wheelSerialNumber;

    @Enumerated(EnumType.STRING)
    private StockOrigin stockOriginWheel;
    private String status; // null (not sold) or "Sold"

    @OneToMany(mappedBy = "stockWheels")
    private List<SalesWheels> wheelsSales;
}
