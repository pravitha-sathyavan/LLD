package org.example.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogQueue {
    private static final BlockingQueue<LogMessage> queue = new LinkedBlockingQueue<>();

    public static void enqueue(LogMessage log) {
        queue.offer(log);
    }

    public static LogMessage dequeue() throws InterruptedException {
        return queue.take();
    }
}

/*
LogQueue is a shared queue used for asynchronous logging.
Instead of writing logs directly to a file/console (which can be slow), logs are added to this queue.
A separate worker thread will consume logs from the queue and write them efficiently.

BlockingQueue ensures thread safety and prevents race conditions when multiple threads add logs.
LinkedBlockingQueue is an unbounded queue (it grows dynamically) and is backed by a linked list.

*/