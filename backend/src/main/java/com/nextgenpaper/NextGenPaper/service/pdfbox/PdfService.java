package com.nextgenpaper.NextGenPaper.service.pdfbox;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import com.nextgenpaper.NextGenPaper.entity.Question;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Arrays;
@Service
public class PdfService implements IPdfService{

//    Extract text from Multipart file
    public String pdfToText(MultipartFile file) {
        File tempFile = null;
        try {
            // 1. Save to resources/temp/
            String tempDirPath = new File("src/main/resources/temp").getAbsolutePath();
            File dir = new File(tempDirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            tempFile = new File(tempDirPath, Objects.requireNonNull(file.getOriginalFilename()));
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            // 2. Load with PDFBox
            try (PDDocument document = PDDocument.load(tempFile)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing PDF file: " + e.getMessage(), e);
        } finally {
            // 3. Clean up: delete file
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

//    Generate pdf from list of questions
    @Override
    public byte[] generatePDF(ArrayList<Question> questions) {
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            float margin = 50;
            PDRectangle pageSize = page.getMediaBox();
            float yStart = pageSize.getHeight() - margin;
            float yPosition = yStart;
            float tableWidth = pageSize.getWidth() - 2 * margin;
            float bottomMargin = 50;
            float yStartNewPage = yStart;

            BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, true);
            addWatermark(document,page, "NextGenPaper");
            // Add Header Row
            Row<PDPage> headerRow = table.createRow(25f);

            Cell<PDPage> cell1 = headerRow.createCell(10, "Q.No");
            Cell<PDPage> cell2 = headerRow.createCell(60, "Question");
            Cell<PDPage> cell3 = headerRow.createCell(20, "Bloom Level");
            Cell<PDPage> cell4 = headerRow.createCell(10, "Marks");

            for (Cell<PDPage> cell : Arrays.asList(cell1, cell2, cell3, cell4)) {
                cell.setFontSize(12f); // Or 14f, based on your preference
                cell.setTopPadding(5f);    // Optional: add more spacing
                cell.setBottomPadding(5f);
                cell.setFont(PDType1Font.HELVETICA_BOLD); // Optional: make it bold
            }


            // Add Data Rows
            for (Question q : questions) {
                Row<PDPage> row = table.createRow(25f);

                Cell<PDPage> qnoCell = row.createCell(10, String.format("%d.%d", q.getQNo(), q.getSubQNo()));
                Cell<PDPage> questionCell = row.createCell(60, q.getQuestion());
                Cell<PDPage> bloomCell = row.createCell(20, q.getBloomLevel());
                Cell<PDPage> marksCell = row.createCell(10, String.valueOf(q.getMarks()));

                // Apply font size and padding uniformly
                for (Cell<PDPage> cell : Arrays.asList(qnoCell, questionCell, bloomCell, marksCell)) {
                    cell.setFontSize(12);
                    cell.setTopPadding(6);
                    cell.setBottomPadding(6);
                    cell.setLeftPadding(4);   // Optional for nicer spacing
                    cell.setRightPadding(4);
                    // cell.setFont(PDType1Font.HELVETICA); // Optional: set font explicitly
                }
            }


            table.draw();

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                document.save(out);              // Save into memory
                return out.toByteArray();        // ✅ Return byte[] to controller
            }
        }catch (IOException exception){
            throw new RuntimeException("Error processing PDF file: " + exception.getMessage(), exception);
        }
    }
    private void addWatermark(PDDocument document, PDPage page, String watermarkText) throws IOException {
        PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

        // Transparency
        PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
        graphicsState.setNonStrokingAlphaConstant(0.2f); // Light watermark
        cs.setGraphicsStateParameters(graphicsState);

        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 60);
        cs.setNonStrokingColor(200, 200, 200); // Light gray
        cs.setTextRotation(Math.toRadians(45), 150, 300); // Rotate and position
        cs.showText(watermarkText);
        cs.endText();
        graphicsState.setNonStrokingAlphaConstant(1f); // Light watermark
        cs.close();
    }

//  Extract text from PDF using filename
    public String pdfToText(String fileName) {
        try {
            String tempDirPath = new File("src/main/resources/uploads").getAbsolutePath();
            File file = new File(tempDirPath, fileName);
            if (file.exists() && file.isFile()) {
                System.out.println("File exists.");
                // 2. Load with PDFBox
                try (PDDocument document = PDDocument.load(file)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    return stripper.getText(document);
                }
            }else{
                return  "";
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing PDF file: " + e.getMessage(), e);
        }
    }
}
