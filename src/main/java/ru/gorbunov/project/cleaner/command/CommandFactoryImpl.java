package ru.gorbunov.project.cleaner.command;

import ru.gorbunov.project.cleaner.task.Task;
import ru.gorbunov.project.cleaner.task.TaskType;

import java.util.Objects;
import java.util.concurrent.Callable;

public class CommandFactoryImpl implements CommandFactory {

    @Override
    public Callable<Boolean> createCommand(Task task) {
        TaskType type = Objects.requireNonNull(task, "Task should be not null").getType();
        switch (type) {
            case DELETE_DIR: {
                return new CleanDirCommand(task.getCommand());
            }
            case PROCESS: {
                return new ProcessCommand(task.getCommand());
            }
            default: {
            }
            throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }

}
