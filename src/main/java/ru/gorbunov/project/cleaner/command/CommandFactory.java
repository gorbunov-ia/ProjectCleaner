package ru.gorbunov.project.cleaner.command;

import ru.gorbunov.project.cleaner.task.Task;

import java.util.concurrent.Callable;

public interface CommandFactory {
    Callable<Boolean> createCommand(Task task);
}
