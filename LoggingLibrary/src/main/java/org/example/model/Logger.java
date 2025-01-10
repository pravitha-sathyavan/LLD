package org.example.model;

import org.example.appender.Appender;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private final LogLevel level;
    private static final List<Appender> appenders = new ArrayList<>();

    public Logger(LogLevel level) {
        this.level = level;
    }

    public static void addAppender(Appender appender) {
        appenders.add(appender);
    }

    public static List<Appender> getAppenders() {
        return appenders;
    }

    public void log(LogLevel level, String message) {
        if (level.isEnabled(this.level)) {
            LogQueue.enqueue(new LogMessage(level, message));
        }
    }

    public void debug(String message) { log(LogLevel.DEBUG, message); }
    public void info(String message) { log(LogLevel.INFO, message); }
    public void warn(String message) { log(LogLevel.WARN, message); }
    public void error(String message) { log(LogLevel.ERROR, message); }
    public void fatal(String message) { log(LogLevel.FATAL, message); }
}
