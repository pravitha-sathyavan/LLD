package org.example.appender.impl;

import org.example.appender.Appender;
import org.example.model.LogMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RollingFileAppender implements Appender {
    private final String filePath;
    private final long maxFileSize = 10 * 1024 * 1024; // 10MB

    public RollingFileAppender(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(LogMessage message) {
        File file = new File(filePath);
        if (file.length() > maxFileSize) {
            file.renameTo(new File(filePath + "." + System.currentTimeMillis()));
        }
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message.format() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
