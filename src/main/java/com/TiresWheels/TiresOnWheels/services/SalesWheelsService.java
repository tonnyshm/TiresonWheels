package com.TiresWheels.TiresOnWheels.services;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.SalesWheels;
import com.TiresWheels.TiresOnWheels.repository.SalesWheelsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesWheelsService {

    @Autowired
    private SalesWheelsRepo salesWheelsRepo;

    public SalesWheels saveSalesWheels (SalesWheels salesWheels){
        SalesWheels salesWheels1 = new SalesWheels();
        salesWheels1.setDateWheelSold(LocalDate.now());
        return salesWheelsRepo.save(salesWheels);
    }


    public List<SalesWheels> findAll() {
        return salesWheelsRepo.findAll();
    }


    public void deleteSalesWheels(int id){
        SalesWheels salesWheels = salesWheelsRepo.findById(id).orElse(null);
        if(salesWheels !=null){
            salesWheelsRepo.delete(salesWheels);
        }
    }

    public List<SalesWheels> getSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesWheelsRepo.findSalesByDateRange(startDate, endDate);
    }

}
