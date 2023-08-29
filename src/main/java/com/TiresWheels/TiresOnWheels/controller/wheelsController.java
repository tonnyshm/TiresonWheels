package com.TiresWheels.TiresOnWheels.controller;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.SalesWheels;
import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.model.StockWheels;
import com.TiresWheels.TiresOnWheels.repository.SalesWheelsRepo;
import com.TiresWheels.TiresOnWheels.repository.StockWheelsRepo;
import com.TiresWheels.TiresOnWheels.services.SalesWheelsService;
import com.TiresWheels.TiresOnWheels.services.StockWheelsService;
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
public class wheelsController {
    @Autowired
    private StockWheelsService stockWheelsService;
    @Autowired
    private SalesWheelsService salesWheelsService;
    @Autowired
    private StockWheelsRepo stockWheelsRepo;



    @GetMapping("/wheelStock")
    public String showStockWheelsForm(Model model) {
        model.addAttribute("StockWheels", new StockWheels());
        return "wheelStockPage";
    }


    @GetMapping("/wheelStockPage")
    public String showStockWheelsPage(Model model) {
        model.addAttribute("StockWheels", new StockWheels());
        return "wheelStockPage";
    }


    @PostMapping("/stockwheels/save")
    public String submitExpenseForm(@ModelAttribute("wheelStockPage")StockWheels stockWheels) {
        stockWheelsService.savestockWheels(stockWheels);
        // Redirect to a success page or display a message
        return "redirect:/home";
    }



    @GetMapping("/searchWheels")
    public String showSearchWheelPage(Model model) {
        model.addAttribute("wheels", Collections.emptyList());
        return "searchWheels";
    }

    @PostMapping("/sellWheel")
    public String sellWheel(@RequestParam("wheelId") int wheelId,
                            @RequestParam("wheelPrice") BigDecimal wheelSellingPrice) {
        // Fetch the StockTires object using the tireId
        StockWheels stockWheels = stockWheelsService.findById(wheelId);

        // Create a new SalesTires object
        SalesWheels salesWheels = new SalesWheels();
        salesWheels.setWheelSellingPrice(wheelSellingPrice);
        salesWheels.setDateWheelSold(LocalDate.now());
        salesWheels.setStockWheels(stockWheels); // Associate the StockTires object

        // Save the SalesTires object along with the updated StockTires status
        salesWheelsService.saveSalesWheels(salesWheels);

        // Update the status of the associated StockTires object
        stockWheels.setStatus("SOLD");
        stockWheelsRepo.save(stockWheels);

        return "redirect:/searchWheels"; // Redirect or perform any additional actions
    }




    @GetMapping("/searchWheelsResult")
    public String showSearchTiresResult(@RequestParam(value = "wheelSize", required = false) Integer wheelSize, Model model) {
        if (wheelSize != null) {
            List<StockWheels> foundWheel = stockWheelsService.findWheelsBySize(wheelSize);
            model.addAttribute("wheels", foundWheel);
        } else {
            model.addAttribute("wheels", Collections.emptyList());
        }
        return "list_of_wheels";
    }

    @GetMapping("/sellWheel")
    public String showSellWheelForm(@RequestParam("wheelId") int wheelId, Model model) {
        StockWheels selectedWheel = stockWheelsService.findById(wheelId);
        if (selectedWheel != null) {
            model.addAttribute("wheel", selectedWheel);
            return "sellWheel";
        } else {
            return "redirect:/searchWheels";
        }


    }}
