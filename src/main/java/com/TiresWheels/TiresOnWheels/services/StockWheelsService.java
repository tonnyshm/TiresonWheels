package com.TiresWheels.TiresOnWheels.services;

import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.model.StockWheels;
import com.TiresWheels.TiresOnWheels.repository.StockWheelsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockWheelsService {

    @Autowired
    private StockWheelsRepo stockWheelsRepo;



    public StockWheels savestockWheels (StockWheels stockWheels){
        return stockWheelsRepo.save(stockWheels);
    }


    public List<StockWheels> findAll() {
        return stockWheelsRepo.findAll();
    }


    public void deleteStockWheels(int id){
        StockWheels stockWheels = stockWheelsRepo.findById(id).orElse(null);
        if(stockWheels !=null){
            stockWheelsRepo.delete(stockWheels);
        }
    }



    public StockWheels findById(int wheelId) {
        Optional<StockWheels> optionalWheel = stockWheelsRepo.findById(wheelId);
        return optionalWheel.orElse(null);
    }


    public StockWheels findWheelBySize(int wheelSize) {
        return stockWheelsRepo.findByWheelSizeAndStatusIsNull(wheelSize);
    }

    public List<StockWheels> findWheelsBySize(int wheelSize) {
        return stockWheelsRepo.findWheelsByWheelSizeAndStatusIsNull(wheelSize);
    }


}
