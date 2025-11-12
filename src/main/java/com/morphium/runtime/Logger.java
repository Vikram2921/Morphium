package com.morphium.runtime;

/**
 * Logger interface for Morphium scripts.
 * Allows custom logging implementations to be plugged in.
 */
public interface Logger {
    
    enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
    
    /**
     * Log a message at the specified level
     * @param level The log level
     * @param message The message to log
     */
    void log(Level level, String message);
    
    /**
     * Log a message at INFO level
     * @param message The message to log
     */
    default void info(String message) {
        log(Level.INFO, message);
    }
    
    /**
     * Log a message at WARN level
     * @param message The message to log
     */
    default void warn(String message) {
        log(Level.WARN, message);
    }
    
    /**
     * Log a message at ERROR level
     * @param message The message to log
     */
    default void error(String message) {
        log(Level.ERROR, message);
    }
    
    /**
     * Log a message at DEBUG level
     * @param message The message to log
     */
    default void debug(String message) {
        log(Level.DEBUG, message);
    }
}
