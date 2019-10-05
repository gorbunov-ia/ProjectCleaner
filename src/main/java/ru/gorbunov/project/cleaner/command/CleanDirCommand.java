package ru.gorbunov.project.cleaner.command;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.Callable;

public class CleanDirCommand implements Callable<Boolean> {

    private final Logger logger = LoggerFactory.getLogger(CleanDirCommand.class);

    private final String directory;

    CleanDirCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public Boolean call() throws Exception {
        File file = new File(directory);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
            logger.info("Directory {} was deleted", file);
        } else {
            logger.info("Directory {} does not exist", file);
        }
        return true;
    }
}
