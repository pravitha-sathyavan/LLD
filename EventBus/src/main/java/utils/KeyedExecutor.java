package utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class KeyedExecutor {
    private final Executor[] executor;

    public KeyedExecutor(final int threads) {
        executor = new Executor[threads];
        for (int i = 0; i < threads; i++) {
            executor[i] = Executors.newSingleThreadExecutor();
        }
    }

    public CompletionStage<Void> submit(final String id, final Runnable task) {
        return CompletableFuture.runAsync(task, executor[id.hashCode() % executor.length]);
    }

    public <T> CompletionStage<T> get(final String id, final Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, executor[id.hashCode() % executor.length]);
    }
}
/*
Task Serialization by Key: Ensures that tasks submitted with the same key (id) i.e topicName are executed sequentially in the same thread.
Thread Pool Management: Distributes tasks across multiple single-threaded executors for concurrency while maintaining order within each key.

keyedExecutor.submit("user1", () -> System.out.println("First task for user1"));
keyedExecutor.submit("user1", () -> System.out.println("Second task for user1"));
keyedExecutor.submit("user2", () -> System.out.println("Task for user2"));

Tasks for "user1" are executed sequentially in the same thread.
Tasks for "user2" may execute in parallel, depending on which executor it maps to.

keyedExecutor.get("user1", () -> {
    return "Result for user1";
}).thenAccept(System.out::println);

 */
