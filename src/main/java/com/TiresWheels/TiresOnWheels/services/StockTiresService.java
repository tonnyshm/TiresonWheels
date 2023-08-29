package com.TiresWheels.TiresOnWheels.services;

import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.model.StockWheels;
import com.TiresWheels.TiresOnWheels.repository.StockTiresRepo;
import com.TiresWheels.TiresOnWheels.repository.StockWheelsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockTiresService {


    @Autowired
    private StockTiresRepo stockTiresRepo;



    public StockTires savestockTires (StockTires stockTires){
        return stockTiresRepo.save(stockTires);
    }


    public List<StockTires> findAll() {
        return stockTiresRepo.findAll();
    }


    public void deleteStockTires(int id){
        StockTires stockTires = stockTiresRepo.findById(id).orElse(null);
        if(stockTires !=null){
            stockTiresRepo.delete(stockTires);
        }
    }

    public StockTires findById(int tireId) {
        Optional<StockTires> optionalTire = stockTiresRepo.findById(tireId);
        return optionalTire.orElse(null);
    }


    public StockTires findTireBySize(int tireSize) {
        return stockTiresRepo.findByTireSizeAndStatusIsNull(tireSize);
    }

    public List<StockTires> findTiresBySize(int tireSize) {
        return stockTiresRepo.findTiresByTireSizeAndStatusIsNull(tireSize);
    }

}
