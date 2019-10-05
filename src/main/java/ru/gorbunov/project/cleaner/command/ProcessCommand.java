package ru.gorbunov.project.cleaner.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ProcessCommand implements Callable<Boolean> {

    private final Logger logger = LoggerFactory.getLogger(ProcessCommand.class);

    private final String command;

    ProcessCommand(String command) {
        this.command = command;
    }

    @Override
    public Boolean call() throws Exception {
        final int exitVal;
        final StringBuilder output = new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(this.command);

            readInput(output, process);

            exitVal = process.waitFor();
        } catch (Exception e) {
            logger.error("Command {} is not executed", command);
            throw e;
        }

        return isSuccess(exitVal, output.toString());
    }

    private Boolean isSuccess(int exitVal, String output) {
        if (exitVal == 0) {
            logger.info("Command completed:\n{}", output);
            return true;
        }
        logger.info("Command did not complete");
        return false;
    }

    private void readInput(StringBuilder output, Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
            }
        }
    }
}
