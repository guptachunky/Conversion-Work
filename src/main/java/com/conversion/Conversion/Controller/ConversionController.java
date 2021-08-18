package com.conversion.Conversion.Controller;

import com.conversion.Conversion.Pojo.FileDetail;
import com.conversion.Conversion.Pojo.FilesDetails;
import com.conversion.Conversion.Service.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/conversion")
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping(value = "/docToPdf")
    public void docToPdf(FileDetail file, HttpServletResponse response) {
        conversionService.docToPdf(file, response);
    }

    @PostMapping(value = "/imagesToPdf")
    public void imagesToPdf(FilesDetails file, HttpServletResponse response) {
        conversionService.combineImagesIntoPDF(file, response);
    }
}
