package com.TiresWheels.TiresOnWheels.services;

import com.TiresWheels.TiresOnWheels.model.SalesWheels;
import com.TiresWheels.TiresOnWheels.model.StockWheels;
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
public class PdfReportServiceForWheels {

    public byte[] generateSalesReport(List<SalesWheels> salesList, LocalDate startDate, LocalDate endDate) throws IOException {
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
        contentStream.showText("Sales Report For Wheels");
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Period: " + startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + " to " + endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        contentStream.endText();
    }

    private void addSaleDetails(PDPageContentStream contentStream, List<SalesWheels> salesList) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
        contentStream.newLineAtOffset(50, 700);

//        contentStream.showText("Wheel ID");
//        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Wheel Stock Price");
        contentStream.newLineAtOffset(80, 0);

        contentStream.showText("Wheel Brand");
        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Wheel Size");
        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Stock Origin");
        contentStream.newLineAtOffset(60, 0);

        contentStream.showText("Date Created");
        contentStream.newLineAtOffset(100, 0);

        contentStream.showText("Selling Price");
        contentStream.newLineAtOffset(70, 0);

        contentStream.showText("Date Sold");
        contentStream.newLineAtOffset(100, 0);

        contentStream.showText("Profit");
        contentStream.newLineAtOffset(60, 0);

        contentStream.endText();

        int yOffset = 680;
        for (SalesWheels sale : salesList) {
            StockWheels stockWheels = sale.getStockWheels();
            BigDecimal profit = sale.getWheelSellingPrice().subtract(stockWheels.getWheelPrice());

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset(50, yOffset);

//            contentStream.showText(String.valueOf(stockWheels.getId()));
//            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(stockWheels.getWheelPrice().toString());
            contentStream.newLineAtOffset(80, 0);

            contentStream.showText(stockWheels.getWheelBrand());
            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(String.valueOf(stockWheels.getWheelSize()));
            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(stockWheels.getStockOriginWheel().toString());
            contentStream.newLineAtOffset(60, 0);

            contentStream.showText(stockWheels.getDateCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(100, 0);

            contentStream.showText(sale.getWheelSellingPrice().toString());
            contentStream.newLineAtOffset(70, 0);

            contentStream.showText(sale.getDateWheelSold().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(100, 0);

            contentStream.showText(profit.toString());
            contentStream.newLineAtOffset(60, 0);

            contentStream.endText();

            yOffset -= 20;
        }
    }

    private BigDecimal calculateOverallProfitLoss(List<SalesWheels> salesList) {
        BigDecimal overallProfitLoss = BigDecimal.ZERO;

        for (SalesWheels sale : salesList) {
            StockWheels stockWheels = sale.getStockWheels();
            BigDecimal profit = sale.getWheelSellingPrice().subtract(stockWheels.getWheelPrice());
            overallProfitLoss = overallProfitLoss.add(profit);
        }

        return overallProfitLoss;
    }

    private void addFooter(PDPageContentStream contentStream) throws IOException {
        // Add footer content here if needed
    }
}
