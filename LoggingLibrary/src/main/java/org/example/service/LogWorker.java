package org.example.service;

import org.example.appender.Appender;
import org.example.model.LogMessage;
import org.example.model.LogQueue;
import org.example.model.Logger;

public class LogWorker implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                LogMessage log = LogQueue.dequeue();
                for (Appender appender : Logger.getAppenders()) {
                    appender.append(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/*
A separate worker thread reads logs from the queue and writes them to a file or console.
The worker thread writes logs separately, preventing performance bottlenecks.
 */

