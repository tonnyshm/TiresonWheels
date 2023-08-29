package com.TiresWheels.TiresOnWheels.services;


import com.TiresWheels.TiresOnWheels.model.SalesTires;
import com.TiresWheels.TiresOnWheels.model.StockTires;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfReportServiceForTires {

    public byte[] generateSalesReport(List<SalesTires> salesList, LocalDate startDate, LocalDate endDate) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        addHeader(contentStream, startDate, endDate);
        addSaleDetails(contentStream, salesList);
        addFooter(contentStream);


        BigDecimal overallProfitLoss = calculateOverallProfitLoss(salesList);

        // Add content for overall profit/loss
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(50, 100); // Adjust the position as needed

        if (overallProfitLoss.compareTo(BigDecimal.ZERO) > 0) {
            contentStream.showText("Overall Profit: " + overallProfitLoss);
        } else if (overallProfitLoss.compareTo(BigDecimal.ZERO) < 0) {
            contentStream.showText("Overall Loss: " + overallProfitLoss.abs());
        } else {
            contentStream.showText("No Profit or Loss");
        }
        contentStream.endText();


        contentStream.close();
        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }

    private void addHeader(PDPageContentStream contentStream, LocalDate startDate, LocalDate endDate) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(100, 750);
        contentStream.showText("Sales Report For Tires");
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Period: " + startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + " to " + endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        contentStream.endText();
    }

    private void addSaleDetails(PDPageContentStream contentStream, List<SalesTires> salesList) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
        contentStream.newLineAtOffset(50, 700);



//        contentStream.showText("Tire ID");
//        contentStream.newLineAtOffset(20, 0);

        contentStream.showText("Tire Stock Price");
        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Tire Brand");
        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Tire Size");
        contentStream.newLineAtOffset(50, 0);

        contentStream.showText("Stock Origin");
        contentStream.newLineAtOffset(50, 0);

        contentStream.showText("Date Created");
        contentStream.newLineAtOffset(100, 0);

        contentStream.showText("Selling Price");
        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Date Sold");
        contentStream.newLineAtOffset(100, 0);

        contentStream.showText("Profit");
        contentStream.newLineAtOffset(60, 0);

        contentStream.endText();

        int yOffset = 680;
        for (SalesTires sale : salesList) {
            StockTires stockTires = sale.getStockTires();
            BigDecimal profit = sale.getTireSellingPrice().subtract(stockTires.getTirePrice());

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(50, yOffset);

//            contentStream.showText(String.valueOf(stockTires.getId()));
//            contentStream.newLineAtOffset(20, 0);

            contentStream.showText(stockTires.getTirePrice().toString());
            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(stockTires.getTireBrand());
            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(String.valueOf(stockTires.getTireSize()));
            contentStream.newLineAtOffset(50, 0);

            contentStream.showText(stockTires.getStockOriginTire().toString());
            contentStream.newLineAtOffset(50, 0);

            contentStream.showText(stockTires.getDateCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(100, 0);

            contentStream.showText(sale.getTireSellingPrice().toString());
            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(sale.getDateTireSold().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(100, 0);

            contentStream.showText(profit.toString());
            contentStream.newLineAtOffset(60, 0);

            contentStream.endText();

            yOffset -= 20;
        }
    }


    private BigDecimal calculateOverallProfitLoss(List<SalesTires> salesList) {
        BigDecimal overallProfitLoss = BigDecimal.ZERO;

        for (SalesTires sale : salesList) {
            StockTires stockTires = sale.getStockTires();
            BigDecimal profit = sale.getTireSellingPrice().subtract(stockTires.getTirePrice());
            overallProfitLoss = overallProfitLoss.add(profit);
        }

        return overallProfitLoss;
    }

    private void addFooter(PDPageContentStream contentStream) throws IOException {
        // Add footer content here if needed
    }
}
