package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class NotEmptyUploadFileCollectionValidator
                                                  implements
                                                  ConstraintValidator<NotEmptyUploadFile, Collection<MultipartFile>> {

    private final NotEmptyUploadFileValidator validator = new NotEmptyUploadFileValidator();

    @Override
    public void initialize(NotEmptyUploadFile constraintAnnotation) {
        validator.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Collection<MultipartFile> values,
            ConstraintValidatorContext context) {
        boolean isValid = true;
        for (MultipartFile file : values) {
            if (!validator.isValid(file, context)) {
                isValid = false;
            }
        }
        return isValid;
    }

}
