package service;

import model.Priority;
import model.Status;
import model.Task;
import model.TaskManager;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Service
public class TaskServiceImpl implements TaskService, DisposableBean{

    TaskManager taskManager;
    private AtomicInteger idCounter = new AtomicInteger(0);
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public TaskServiceImpl(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public int addTask(String title, Priority priority, int execTime, String serviceProvider) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        if (execTime <= 0) {
            throw new IllegalArgumentException("Execution time must be greater than zero");
        }
        if (serviceProvider == null || serviceProvider.isBlank()) {
            throw new IllegalArgumentException("Service provider cannot be null or blank");
        }
        int taskId = idCounter.incrementAndGet();
        Task task = new Task(
                taskId,
                priority,
                title,
                execTime,
                Status.PENDING,
                serviceProvider,
                LocalDateTime.now()
        );
        taskManager.getTasks().add(task);
//        synchronized (taskManager) {
//            taskManager.getTasks().add(task); // Assuming the queue is a thread-safe collection
//        }
//        PriorityBlockingQueue is inherently thread-safe.
//        This means it handles all necessary synchronization internally when multiple threads add or retrieve elements.
//        Adding external synchronized blocks around such collections is unnecessary.

        return taskId;
    }


/*
@PostConstruct is an annotation provided by Java's javax.annotation package.
It marks a method that should be executed once, after the dependency injection is complete and the bean is fully initialized.
Using @PostConstruct for queue execution initialization in the given code is a way to ensure that the task execution logic is
 started as soon as the Spring application context is fully initialized.
 */

    @PostConstruct
    public void initQueueExecution() {
        executor.submit(this::executeQueue);
    }

    public void executeQueue() throws InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = taskManager.getTasks().poll(); // Use PriorityBlockingQueue's poll
                if (task != null) {
                    task.setStatus(Status.IN_PROGRESS);
                    Thread.sleep(task.getExecTime() * 1000L); // Simulate task execution
                    task.setStatus(Status.COMPLETED);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt flag and exit
            } catch (Exception e) {
                // Log errors or handle task-specific exceptions
                System.err.println("Error executing task: " + e.getMessage());
            }
        }
    }


    public void stop() {
        executor.shutdownNow();
    }


    @Override
    public void destroy() throws Exception {
        stop(); // Ensure shutdown happens during application termination
    }



    @Override
    public List<Task> getTasksByStatus(Status status) {
        return taskManager.getTasks().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }
}
