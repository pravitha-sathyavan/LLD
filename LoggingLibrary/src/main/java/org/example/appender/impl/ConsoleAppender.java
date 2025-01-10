package org.example.appender.impl;

import org.example.appender.Appender;
import org.example.model.LogMessage;

public class ConsoleAppender implements Appender {

    @Override
    public void append(LogMessage message) {
        System.out.println(message.format());
    }
}
