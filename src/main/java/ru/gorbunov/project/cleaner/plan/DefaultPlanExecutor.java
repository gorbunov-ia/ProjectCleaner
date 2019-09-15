package ru.gorbunov.project.cleaner.plan;

import ru.gorbunov.project.cleaner.command.CommandFactory;
import ru.gorbunov.project.cleaner.command.CommandFactoryImpl;
import ru.gorbunov.project.cleaner.task.Task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DefaultPlanExecutor implements PlanExecutor {
    private final CommandFactory commandFactory;
    private final int timeout;

    /**
     * @param commandFactory factory of commands
     * @param timeout        seconds
     */
    public DefaultPlanExecutor(CommandFactory commandFactory, int timeout) {
        this.commandFactory = Objects.requireNonNull(commandFactory);
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout should be positive");
        }
        this.timeout = timeout;
    }

    /**
     * @param timeout seconds
     */
    public DefaultPlanExecutor(int timeout) {
        this(new CommandFactoryImpl(), timeout);
    }

    @Override
    public void execute(Plan plan) {
        if (Objects.requireNonNull(plan, "Plan should not be null").isEmpty()) {
            return;
        }
        Collection<Callable<Boolean>> commands = new LinkedList<>();
        for (Task task : plan.getTasks()) {
            commands.add(commandFactory.createCommand(task));
        }
        ExecutorService executor = Executors.newFixedThreadPool(commands.size());
        try {
            for (Future<Boolean> future : executor.invokeAll(commands, timeout, TimeUnit.SECONDS)) {
                if (future.isCancelled() || Boolean.FALSE.equals(future.get())) {
                    throw new PlanExecutionException("Timeout exceeded");
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new PlanExecutionException(e);
        } finally {
            executor.shutdown();
        }
    }
}
