package com.TiresWheels.TiresOnWheels.controller;

import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.SalesWheels;
import com.TiresWheels.TiresOnWheels.services.PdfReportServiceForTires;
import com.TiresWheels.TiresOnWheels.services.PdfReportServiceForWheels;
import com.TiresWheels.TiresOnWheels.services.SalesTiresService;
import com.TiresWheels.TiresOnWheels.services.SalesWheelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private PdfReportServiceForTires pdfReportServiceForTires;

    @Autowired
    private SalesTiresService salesTiresService;

    @Autowired
    private PdfReportServiceForWheels pdfReportServiceForWheels;

    @Autowired
    private SalesWheelsService salesWheelsService;



    @GetMapping("/generateReportForTires")
    @ResponseBody
    public ResponseEntity<byte[]> generateReportForTires(@RequestParam("startDate") String startDate,
                                                 @RequestParam("endDate") String endDate,
                                                 @RequestParam("reportType") String reportType) throws IOException {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        List<SalesTires> salesList = salesTiresService.getSalesByDateRange(parsedStartDate, parsedEndDate);


        byte[] pdfBytes = pdfReportServiceForTires.generateSalesReport(salesList, parsedStartDate, parsedEndDate);

        String reportFileName = "sales_report_Tires" + reportType.toLowerCase() + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=\"" + reportFileName + "\"")
                .body(pdfBytes);
    }





    @GetMapping("/generateReportForWheels")
    @ResponseBody
    public ResponseEntity<byte[]> generateReportForWheels(@RequestParam("startDate") String startDate,
                                                 @RequestParam("endDate") String endDate,
                                                 @RequestParam("reportType") String reportType) throws IOException {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        List<SalesWheels> salesList = salesWheelsService.getSalesByDateRange(parsedStartDate, parsedEndDate);


        byte[] pdfBytes = pdfReportServiceForWheels.generateSalesReport(salesList, parsedStartDate, parsedEndDate);

        String reportFileName = "sales_report_Wheels" + reportType.toLowerCase() + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=\"" + reportFileName + "\"")
                .body(pdfBytes);
    }



}
