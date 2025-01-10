package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogMessage {
    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String threadName;
    private final String message;

    public LogMessage(LogLevel level, String message) {
        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.threadName = Thread.currentThread().getName();
        this.message = message;
    }

    public String format() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + timestamp.format(formatter) + "] [" + level + "] [" + threadName + "] - " + message;
    }
}

/*
threadName stores the name of the thread that created the log message.
It is obtained using: Thread.currentThread().getName();
This helps in identifying which thread generated the log in a multi-threaded application.
 */
