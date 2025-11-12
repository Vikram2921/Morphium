package com.morphium.core;

public class MorphiumException extends RuntimeException {
    private String sourcePath;
    private int line;
    private int column;

    public MorphiumException(String message) {
        super(message);
    }

    public MorphiumException(String message, Throwable cause) {
        super(message, cause);
    }

    public MorphiumException(String message, String sourcePath, int line, int column) {
        super(formatMessage(message, sourcePath, line, column));
        this.sourcePath = sourcePath;
        this.line = line;
        this.column = column;
    }

    private static String formatMessage(String message, String sourcePath, int line, int column) {
        return String.format("%s at %s:%d:%d", message, sourcePath, line, column);
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
