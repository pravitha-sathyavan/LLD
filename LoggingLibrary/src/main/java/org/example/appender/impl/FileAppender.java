package org.example.appender.impl;

import org.example.appender.Appender;
import org.example.model.LogMessage;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements Appender {
    private final String filePath;

    public FileAppender(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(LogMessage message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message.format() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
