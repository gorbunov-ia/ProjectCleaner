package ru.gorbunov.project.cleaner.plan;

public class PlanExecutionException extends RuntimeException {

    PlanExecutionException(Exception e) {
        super(e);
    }

    PlanExecutionException(String message) {
        super(message);
    }
}
