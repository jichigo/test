package xxxxxx.yyyyyy.zzzzzz.app.sample;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class SpecifiedUploadFileValidator
                                         implements
                                         ConstraintValidator<SpecifiedUploadFile, MultipartFile> {

    @Override
    public void initialize(SpecifiedUploadFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile value,
            ConstraintValidatorContext context) {
        return value != null
                && StringUtils.hasLength(value.getOriginalFilename());
    }

}
