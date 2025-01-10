package org.example.model;

public enum LogLevel {
    DEBUG, INFO, WARN, ERROR, FATAL;

    public boolean isEnabled(LogLevel configuredLevel) {
        return this.ordinal() >= configuredLevel.ordinal();
    }
}

/*
Every enum constant has an ordinal value (position in the enum declaration), starting from 0.
DEBUG → 0
INFO → 1
WARN → 2
ERROR → 3
FATAL → 4

return this.ordinal() >= configuredLevel.ordinal();
It compares the ordinal values to determine whether the current log level is enabled based on the configured level.
Logs will only be printed if their severity is equal to or higher than the configured level.
*/
