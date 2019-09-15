package ru.gorbunov.project.cleaner.task;

import java.util.Objects;

public enum TaskType {
    DELETE_DIR("-D"),
    PROCESS("-P");

    public static final int CODE_LENGTH = 2;
    private final String code;

    TaskType(String code) {
        if (Objects.requireNonNull(code, "Empty task code").length() != CODE_LENGTH) {
            throw new IllegalArgumentException("Invalid code: " + code);
        }
        this.code = code;
    }

    public static TaskType parseCode(String code) {
        for (TaskType type : TaskType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Task type not found");
    }
}
