package com.TiresWheels.TiresOnWheels.repository;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.SalesWheels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesWheelsRepo  extends JpaRepository<SalesWheels, Integer> {


    @Query("SELECT st FROM SalesWheels st WHERE st.dateWheelSold BETWEEN :startDate AND :endDate")
    List<SalesWheels> findSalesByDateRange(LocalDate startDate, LocalDate endDate);
}

