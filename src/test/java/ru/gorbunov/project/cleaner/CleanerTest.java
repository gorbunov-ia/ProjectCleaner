/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.gorbunov.project.cleaner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.gorbunov.project.cleaner.plan.DefaultPlanExecutor;
import ru.gorbunov.project.cleaner.plan.Plan;
import ru.gorbunov.project.cleaner.plan.PlanExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class CleanerTest {

    @Test
    void testTimeoutConfig() {
        PlanExecutorTestImpl planExecutor = new PlanExecutorTestImpl();
        new Cleaner(planExecutor::setTimeout).clean(new String[]{"-T20"});
        Assertions.assertEquals(20, planExecutor.getTimeout());
    }

    @Test
    void testDefaultTimeout() {
        PlanExecutorTestImpl planExecutor = new PlanExecutorTestImpl();
        new Cleaner(planExecutor::setTimeout).clean(new String[]{"-D./test"});
        Assertions.assertEquals(Cleaner.DEFAULT_TIMEOUT, planExecutor.getTimeout());
    }

    @Test
    void testClearDirectory(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createFile(tempDir.resolve("tempFile.txt"));

        new Cleaner(DefaultPlanExecutor::new).clean(new String[]{"-D" + tempDir.toAbsolutePath().toString()});

        Assertions.assertFalse(tempFile.toFile().exists());
        Assertions.assertFalse(tempDir.toFile().exists());
    }

    private static class PlanExecutorTestImpl implements PlanExecutor {

        private Integer timeout;

        PlanExecutorTestImpl() {
        }

        @Override
        public void execute(Plan plan) {
            // do nothing
        }

        PlanExecutor setTimeout(Integer timeout) {
            this.timeout = timeout;
            return this;
        }

        Integer getTimeout() {
            return timeout;
        }
    }

}