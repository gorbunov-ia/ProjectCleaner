package ru.gorbunov.project.cleaner.command;

import java.util.concurrent.Callable;

public class ProcessCommand implements Callable<Boolean> {

    private final String process;

    ProcessCommand(String process) {
        this.process = process;
    }

    @Override
    public Boolean call() throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
