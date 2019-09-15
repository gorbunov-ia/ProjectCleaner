package ru.gorbunov.project.cleaner.plan;

import org.junit.jupiter.api.Test;
import ru.gorbunov.project.cleaner.command.CommandFactory;
import ru.gorbunov.project.cleaner.task.Task;
import ru.gorbunov.project.cleaner.task.TaskType;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlanExecutorTest {

    private static final int SECOND = 1;
    private static final int TWO_SECOND = 2;

    @Test
    void testTimeout() {
        testRun(new CommandFactoryTestImpl(TWO_SECOND, true));
    }

    @Test
    void testFailCommand() {
        testRun(new CommandFactoryTestImpl(0, false));
    }

    private void testRun(CommandFactory config) {
        DefaultPlanExecutor executor = new DefaultPlanExecutor(config, SECOND);
        assertThrows(PlanExecutionException.class, () ->
                executor.execute(new Plan().addTask(new Task(TaskType.DELETE_DIR, "./test"))));
    }

    private class CommandFactoryTestImpl implements CommandFactory {

        private final int timeout;
        private final boolean result;

        CommandFactoryTestImpl(int timeout, boolean result) {
            this.timeout = timeout;
            this.result = result;
        }

        @Override
        public Callable<Boolean> createCommand(Task task) {
            return () -> {
                TimeUnit.SECONDS.sleep(timeout);
                return result;
            };
        }
    }
}
