package com.TiresWheels.TiresOnWheels.services;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.SalesWheels;
import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.repository.SalesTiresRepo;
import com.TiresWheels.TiresOnWheels.repository.SalesWheelsRepo;
import com.TiresWheels.TiresOnWheels.repository.StockTiresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesTiresService {

    @Autowired
    private SalesTiresRepo salesTiresRepo;

    @Autowired
    private StockTiresRepo stockTiresRepo;

    public SalesTires saveSalesTires (SalesTires salesTires){
        return salesTiresRepo.save(salesTires);

    }

    public List<SalesTires> findAll() {
        return salesTiresRepo.findAll();
    }


    public void deleteSalesTires(int id){
        SalesTires salesTires = salesTiresRepo.findById(id).orElse(null);
        if(salesTires !=null){
            salesTiresRepo.delete(salesTires);
        }
    }

    public List<SalesTires> getSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesTiresRepo.findSalesByDateRange(startDate, endDate);
    }

}
