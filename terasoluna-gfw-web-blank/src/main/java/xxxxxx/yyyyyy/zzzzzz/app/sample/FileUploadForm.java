package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @SpecifiedUploadFile(groups = Upload.class)
    @NotEmptyUploadFile(groups = Upload.class)
    @UploadFileMaxSize(groups = Upload.class)
    private MultipartFile file;

    @NotNull
    @Length(min = 0, max = 100)
    private String description;

    private String fileName;

    private String uploadTemporaryFileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadTemporaryFileId() {
        return uploadTemporaryFileId;
    }

    public void setUploadTemporaryFileId(String uploadTemporaryFileId) {
        this.uploadTemporaryFileId = uploadTemporaryFileId;
    }

    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static interface Upload {
    }
}
