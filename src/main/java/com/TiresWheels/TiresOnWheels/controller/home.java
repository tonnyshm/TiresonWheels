package com.TiresWheels.TiresOnWheels.controller;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.StockTires;
import com.TiresWheels.TiresOnWheels.services.StockTiresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class home {

    @Autowired
    private StockTiresService stockTiresService;





    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("stockTires", new StockTires());
        return "home";
    }

    @GetMapping("/home")
    public String homePage2(Model model) {
        model.addAttribute("stockTires", new StockTires());
        return "home";
    }


    @GetMapping("/generatePdfReportForTiresSales")
    public String pdfReportPageForTires(Model model) {
        model.addAttribute("salesTires", new SalesTires());
        return "generatePdfReportForTiresSales";
    }


    @GetMapping("/generatePdfReportForWheelsSales")
    public String pdfReportPageForWheelsSales(Model model) {
        model.addAttribute("salesTires", new SalesTires());
        return "generatePdfReportForWheelsSales";
    }



}
