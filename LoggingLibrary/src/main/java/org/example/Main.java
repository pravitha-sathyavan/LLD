package org.example;

import org.example.appender.impl.ConsoleAppender;
import org.example.appender.impl.FileAppender;
import org.example.model.LogLevel;
import org.example.model.Logger;
import org.example.service.LogWorker;

public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger(LogLevel.INFO);
        Logger.addAppender(new ConsoleAppender());
        Logger.addAppender(new FileAppender("app.log"));

        Thread worker = new Thread(new LogWorker());
        worker.start();

        logger.info("Application started.");
        logger.error("Something went wrong!");
        logger.debug("Debug.");
        logger.fatal("Check Now!");
    }
}