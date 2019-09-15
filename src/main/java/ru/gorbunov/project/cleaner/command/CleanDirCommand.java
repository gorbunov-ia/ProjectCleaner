package ru.gorbunov.project.cleaner.command;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.concurrent.Callable;

public class CleanDirCommand implements Callable<Boolean> {

    private final String directory;

    CleanDirCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public Boolean call() throws Exception {
        File file = new File(directory);
        FileUtils.deleteDirectory(file);
        return true;
    }
}
