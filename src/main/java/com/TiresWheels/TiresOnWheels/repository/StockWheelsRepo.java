package com.TiresWheels.TiresOnWheels.repository;

import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.model.StockWheels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockWheelsRepo extends JpaRepository<StockWheels, Integer> {

    StockWheels findByWheelSizeAndStatusIsNull(int WheelSize);
    List<StockWheels> findWheelsByWheelSizeAndStatusIsNull(int WheelSize);

}
