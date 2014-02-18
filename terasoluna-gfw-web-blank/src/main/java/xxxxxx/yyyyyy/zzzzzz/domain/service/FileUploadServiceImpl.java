package xxxxxx.yyyyyy.zzzzzz.domain.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${app.upload.temporaryDirectory}")
    private File uploadTemporaryDirectory;

    @Value("${app.upload.directory}")
    private File uploadDirectory;

    public UploadFileInfo upload(UploadFileInfo uploadTemporaryFileInfo) {

        String uploadFileId = UUID.randomUUID().toString();

        File uploadTemporaryFile = new File(uploadTemporaryDirectory, uploadTemporaryFileInfo
                .getFileId());
        File uploadFile = new File(uploadDirectory, uploadFileId);

        uploadTemporaryFile.renameTo(uploadFile);

        String fileName = uploadTemporaryFileInfo.getFileName();
        String description = uploadTemporaryFileInfo.getDescription();

        // ....

        return new UploadFileInfo(uploadFileId, fileName, description);
    }

}
