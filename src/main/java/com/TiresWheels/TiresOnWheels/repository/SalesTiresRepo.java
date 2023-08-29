package com.TiresWheels.TiresOnWheels.repository;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesTiresRepo  extends JpaRepository<SalesTires, Integer> {

    @Query("SELECT st FROM SalesTires st WHERE st.dateTireSold BETWEEN :startDate AND :endDate")
    List<SalesTires> findSalesByDateRange(LocalDate startDate, LocalDate endDate);
}
