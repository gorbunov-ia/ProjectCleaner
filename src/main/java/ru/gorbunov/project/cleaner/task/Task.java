package ru.gorbunov.project.cleaner.task;

import java.util.Objects;

public class Task {
    private final TaskType type;
    private final String command;

    public Task(TaskType type, String command) {
        this.type = Objects.requireNonNull(type);
        this.command = Objects.requireNonNull(command);
    }

    public TaskType getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }
}
