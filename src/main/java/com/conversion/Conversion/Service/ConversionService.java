package com.conversion.Conversion.Service;

import com.conversion.Conversion.Constants.AppConstant;
import com.conversion.Conversion.Pojo.FileDetail;
import com.conversion.Conversion.Pojo.FilesDetails;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@Service
public class ConversionService {
    private final ValidatorService validatorService;

    public ConversionService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public void docToPdf(FileDetail fileDetail, HttpServletResponse response) {
        if (!validatorService.isDocFile(fileDetail.getFile())) {
            response.setStatus(AppConstant.BAD_REQUEST);
            return;
        }
        InputStream doc;
        try {
            File docFile = converterToFile(fileDetail.getFile());
            doc = new FileInputStream(docFile);
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            File file = File.createTempFile("output", ".pdf");
            OutputStream out = new FileOutputStream(file);
            PdfConverter.getInstance().convert(document, out, options);
            getClaimFiles(file, response);
        } catch (IOException e) {
            response.setStatus(AppConstant.SOMETHING_WENT_WRONG);
        }
    }

    public void getClaimFiles(File file, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment; filename=dummy.pdf");
            response.getOutputStream().write(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            response.setStatus(AppConstant.SOMETHING_WENT_WRONG);
        }
    }

    public File converterToFile(MultipartFile multipartFile) throws IOException {
        File convFile = File.createTempFile("temp", "temp");
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    //    ========================================================================================================================================================================
//    Images to Pdf
    public void combineImagesIntoPDF(FilesDetails files, HttpServletResponse response) {
        if (!validatorService.isAllImages(files.getFiles())) {
            response.setStatus(AppConstant.BAD_REQUEST);
            return;
        }
        try (PDDocument doc = new PDDocument()) {
            for (MultipartFile input : files.getFiles()) {
                addImageAsNewPage(doc, converterToFile(input));
            }
            File file2 = File.createTempFile("output", ".pdf");
            FileOutputStream file = new FileOutputStream(file2);
            doc.save(file);
            getClaimFiles(file2, response);
        } catch (Exception ignored) {
            response.setStatus(AppConstant.SOMETHING_WENT_WRONG);
        }
    }

    private void addImageAsNewPage(PDDocument doc, File file) {
        try {
            PDImageXObject image = PDImageXObject.createFromFileByContent(file, doc);
            PDRectangle pageSize = new PDRectangle(0, 0, 595, 500);

            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();
            float ratio = Math.min(pageWidth / originalWidth, pageHeight / originalHeight);
            float scaledWidth = originalWidth * ratio;
            float scaledHeight = originalHeight * ratio;
            float x = (pageWidth - scaledWidth) / 2;
            float y = (pageHeight - scaledHeight) / 2;

            PDPage page = new PDPage(pageSize);
            doc.addPage(page);
            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.drawImage(image, x, y, scaledWidth, scaledHeight);
            }
            System.out.println("Added: " + file);
        } catch (IOException e) {
            System.err.println("Failed to process: " + file);
            e.printStackTrace(System.err);
        }
    }

    public void mergePdfs(FilesDetails files, HttpServletResponse response) {
        PDFMergerUtility PDFMerger = new PDFMergerUtility();
        try {
            String fileName = "output.pdf";
            PDFMerger.setDestinationFileName(fileName);
            for (MultipartFile input : files.getFiles()) {
                PDFMerger.addSource(converterToFile(input));
            }
            PDFMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            getClaimFiles(new File(PDFMerger.getDestinationFileName()), response);
        } catch (Exception ignored) {
            response.setStatus(AppConstant.SOMETHING_WENT_WRONG);
        }

    }
}
