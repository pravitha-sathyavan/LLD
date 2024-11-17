import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class TaskManagerConfig {

     @Bean
     public PriorityBlockingQueue<Task> taskQueue() {
          return new PriorityBlockingQueue<>();
     }

//     @Bean
//     public Queue<Task> taskQueue() {
//          return Collections.synchronizedCollection(new PriorityQueue<>());
//     }
//     Both bean declarations are correct

}

/*
Why did you choose PriorityBlockingQueue over ConcurrentLinkedQueue?

PriorityBlockingQueue: Automatically orders tasks based on their priority (natural ordering or a custom comparator) when they are added to the queue.
Allows threads to block when the queue is empty, using methods like take() or poll() with timeout.
Thread-safe for concurrent reads and writes.
This ensures high-priority tasks are always processed first.

ConcurrentLinkedQueue: Maintains tasks in the order they are added (FIFO). It doesn't support prioritization.
Does not block if the queue is empty. If no task is available, it simply returns null.
Thread-safe but requires additional logic for blocking behavior.

How does the system handle tasks being added while execution is in progress?
Even if tasks are added while execution is ongoing, the queue automatically reorders itself to ensure priority order is maintained.

 */