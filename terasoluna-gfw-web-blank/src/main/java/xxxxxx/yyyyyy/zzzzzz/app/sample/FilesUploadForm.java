package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

public class FilesUploadForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private List<FileUploadForm> fileUploadForms;

    public List<FileUploadForm> getFileUploadForms() {
        return fileUploadForms;
    }

    public void setFileUploadForms(List<FileUploadForm> fileUploadForms) {
        this.fileUploadForms = fileUploadForms;
    }

    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

}
