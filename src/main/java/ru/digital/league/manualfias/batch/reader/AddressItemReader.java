package ru.digital.league.manualfias.batch.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.FileSystemResource;
import ru.digital.league.manualfias.xml.pojo.AddressObject;

import java.io.File;

@Slf4j
public class AddressItemReader extends StaxEventItemReader<AddressObject> implements StepExecutionListener {

    private static final String SCHEMAS_FOLDER_NAME = "Schemas";
    private static final String DATA_FILE_NAME = "AS_ADDROBJ";

    @Override
    public void beforeStep(StepExecution stepExecution) {
        Long updateId = stepExecution.getJobExecution().getJobParameters().getLong("updateId");
        log.info("Opening folder for update {}", updateId);
        String dirPath = System.getProperty("user.dir") + File.separator + updateId;
        File folder = new File(dirPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !file.getName().equals(SCHEMAS_FOLDER_NAME)) {
                    File[] subfiles = file.listFiles();
                    if (subfiles != null) {
                        for (File subfile : subfiles) {
                            if (subfile.getName().startsWith(DATA_FILE_NAME)) {
                                log.info("Pulling house data from file {}", subfile.getName());
                                setResource(new FileSystemResource(subfile));
                            }
                        }
                    }
                }
                if (file.getName().startsWith(DATA_FILE_NAME)) {
                    log.info("Pulling address data from file {}", file.getName());
                    setResource(new FileSystemResource(file));
                }
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

}
