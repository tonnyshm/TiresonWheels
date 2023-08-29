package com.TiresWheels.TiresOnWheels.controller;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.repository.StockTiresRepo;
import com.TiresWheels.TiresOnWheels.services.SalesTiresService;
import com.TiresWheels.TiresOnWheels.services.StockTiresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class tiresController {


    @Autowired
    private StockTiresService stockTiresService;

    @Autowired
    private SalesTiresService salesTiresService;

    @Autowired
    private StockTiresRepo stockTiresRepo;

    @GetMapping("/stocktirespage")
    public String showAddCarForm(Model model) {
        model.addAttribute("StockTires", new StockTires());
        return "stocktirespage";
    }



    @PostMapping("/stocktires/save")
    public String submitExpenseForm(@ModelAttribute("stocktirespage") StockTires stockTires) {
       stockTiresService.savestockTires(stockTires);
        // Redirect to a success page or display a message
        return "redirect:/home";
    }



    @GetMapping("/searchTires")
    public String showSearchTiresPage(Model model) {
        model.addAttribute("tires", Collections.emptyList());
        return "searchTires";
    }

    @PostMapping("/sellTire")
    public String sellTire(@RequestParam("tireId") int tireId,
                           @RequestParam("tirePrice") BigDecimal tireSellingPrice) {
        // Fetch the StockTires object using the tireId
        StockTires stockTire = stockTiresService.findById(tireId);

        // Create a new SalesTires object
        SalesTires salesTire = new SalesTires();
        salesTire.setTireSellingPrice(tireSellingPrice);
        salesTire.setDateTireSold(LocalDate.now());
        salesTire.setStockTires(stockTire); // Associate the StockTires object

        // Save the SalesTires object along with the updated StockTires status
        salesTiresService.saveSalesTires(salesTire);

        // Update the status of the associated StockTires object
        stockTire.setStatus("SOLD");
        stockTiresRepo.save(stockTire);

        return "redirect:/searchTires"; // Redirect or perform any additional actions
    }


    @GetMapping("/searchTiresResult")
    public String showSearchTiresResult(@RequestParam(value = "tireSize", required = false) Integer tireSize, Model model) {
        if (tireSize != null) {
            List<StockTires> foundTires = stockTiresService.findTiresBySize(tireSize);
            model.addAttribute("tires", foundTires);
        } else {
            model.addAttribute("tires", Collections.emptyList());
        }
        return "list_of_tires";
    }

    @GetMapping("/sellTire")
    public String showSellTireForm(@RequestParam("tireId") int tireId, Model model) {
        StockTires selectedTire = stockTiresService.findById(tireId);
        if (selectedTire != null) {
            model.addAttribute("tire", selectedTire);
            return "sellTire";
        } else {
            return "redirect:/searchTires";
        }
    }



}


