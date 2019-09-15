package ru.gorbunov.project.cleaner;

import ru.gorbunov.project.cleaner.plan.Plan;
import ru.gorbunov.project.cleaner.plan.PlanExecutor;
import ru.gorbunov.project.cleaner.task.Task;
import ru.gorbunov.project.cleaner.task.TaskType;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

class Cleaner {
    static final int DEFAULT_TIMEOUT = 45;
    private static final String TIMEOUT_KEY = "-T";

    private final Function<Integer, PlanExecutor> planExecutorConstructor;

    Cleaner(Function<Integer, PlanExecutor> planExecutorConstructor) {
        this.planExecutorConstructor = Objects.requireNonNull(planExecutorConstructor);
    }

    void clean(String[] args) {
        Plan plan = new Plan();
        Integer timeout = null;
        for (String arg : args) {
            if (isTimeout(arg)) {
                timeout = getTimeout(arg);
                continue;
            }
            Task task = createTask(arg);
            plan.addTask(task);
        }
        planExecutorConstructor.apply(Optional.ofNullable(timeout).orElse(DEFAULT_TIMEOUT)).execute(plan);
    }

    private static boolean isTimeout(String arg) {
        return arg != null && arg.startsWith(TIMEOUT_KEY);
    }

    private static Integer getTimeout(String arg) {
        return Integer.parseInt(arg.substring(TIMEOUT_KEY.length()));
    }

    private static Task createTask(String arg) {
        if (Objects.requireNonNull(arg, "Argument should not be null").length() < TaskType.CODE_LENGTH + 1) {
            throw new IllegalArgumentException("Illegal argument length: " + arg);
        }
        TaskType type = TaskType.parseCode(arg.substring(0, TaskType.CODE_LENGTH));
        return new Task(type, arg.substring(TaskType.CODE_LENGTH));
    }
}
