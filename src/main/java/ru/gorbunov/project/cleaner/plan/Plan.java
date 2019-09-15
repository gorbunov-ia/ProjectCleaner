package ru.gorbunov.project.cleaner.plan;

import ru.gorbunov.project.cleaner.task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Plan {
    private final Collection<Task> tasks = new ArrayList<>();

    Collection<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public Plan addTask(Task task) {
        tasks.add(Objects.requireNonNull(task, "Task should not be null"));
        return this;
    }

    boolean isEmpty() {
        return tasks.isEmpty();
    }
}
