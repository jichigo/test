package xxxxxx.yyyyyy.zzzzzz.app.sample;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileMaxSizeValidator
                                       implements
                                       ConstraintValidator<UploadFileMaxSize, MultipartFile> {

    private UploadFileMaxSize constraint;

    @Override
    public void initialize(UploadFileMaxSize constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(MultipartFile value,
            ConstraintValidatorContext context) {
        if (constraint.value() < 0 || value == null) {
            return true;
        }
        return value.getSize() <= constraint.value();
    }
}
