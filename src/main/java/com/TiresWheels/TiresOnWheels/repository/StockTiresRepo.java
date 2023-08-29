package com.TiresWheels.TiresOnWheels.repository;

import com.TiresWheels.TiresOnWheels.model.StockTires;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTiresRepo extends JpaRepository<StockTires, Integer> {
//    List<StockTires> findTiresBySize(int tireSize);
//    StockTires findByTireSize(int tireSize);

    StockTires findByTireSizeAndStatusIsNull(int tireSize);
    List<StockTires> findTiresByTireSizeAndStatusIsNull(int tireSize);


}