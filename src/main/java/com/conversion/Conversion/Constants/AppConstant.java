package com.conversion.Conversion.Constants;

import java.util.Arrays;
import java.util.List;

public interface AppConstant {

    String DOC = "application/msword";
    String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    List<String> DOC_FILES = Arrays.asList(DOC, DOCX);
    Integer BAD_REQUEST = 400;
    Integer SOMETHING_WENT_WRONG = 500;
    String JPEG = "image/jpeg";
    String JPG = "image/jpg";
    String PNG = "image/png";
    String OCTET = "application/octet-stream";
    List<String> IMAGES_ONLY = Arrays.asList(OCTET, JPEG, JPG, PNG);

}
