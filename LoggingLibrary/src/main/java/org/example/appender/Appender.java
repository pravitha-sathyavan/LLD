package org.example.appender;

import org.example.model.LogMessage;

public interface Appender {
    void append(LogMessage message);
}
