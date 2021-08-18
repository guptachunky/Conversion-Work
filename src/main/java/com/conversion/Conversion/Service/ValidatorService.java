package com.conversion.Conversion.Service;

import com.conversion.Conversion.Constants.AppConstant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidatorService {

    Boolean isDocFile(MultipartFile multipartFile) {
        return !multipartFile.isEmpty() && AppConstant.DOC_FILES.contains(multipartFile.getContentType());
    }

    Boolean isAllImages(MultipartFile[] multipartFile) {
        if (multipartFile.length == 0) {
            return false;
        }
        for (MultipartFile file : multipartFile) {
            if (!AppConstant.IMAGES_ONLY.contains(file.getContentType()))
                return false;
        }
        return true;
    }
}
