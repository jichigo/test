package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.terasoluna.gfw.common.date.DateFactory;

// (1)
public class UnnecessaryFilesCleaner {

    @Inject
    private DateFactory dateFactory;

    @Value("${app.upload.temporaryFileSavedPeriodMinutes}")
    private int savedPeriodMinutes;

    @Value("${app.upload.temporaryDirectory}")
    private File targetDirectory;

    // (2)
    public void cleanup() {

        // calculate cutoff date.
        Date cutoffDate = dateFactory.newDateTime().minusMinutes(
                savedPeriodMinutes).toDate();

        // collect target files.
        IOFileFilter fileFilter = FileFilterUtils.ageFileFilter(cutoffDate);
        Collection<File> targetFiles = FileUtils.listFiles(targetDirectory,
                fileFilter, null);

        if (targetFiles.isEmpty()) {
            return;
        }

        // delete files.
        for (File targetFile : targetFiles) {
            FileUtils.deleteQuietly(targetFile);
        }

    }

}
