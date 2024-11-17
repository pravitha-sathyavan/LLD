package model.controller;

import model.Priority;
import service.TaskService;

@RestController
@RequestMapping("/rest/taskmanager")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping("/task")
    public ResponseEntity<String> addTask(@RequestBody Task taskRequest) {
        try {
            int taskId = taskService.addTask(
                    taskRequest.getTitle(),
                    taskRequest.getPriority(),
                    taskRequest.getExecTime(),
                    taskRequest.getServiceProvider()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Task created successfully with ID: " + taskId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create task: " + e.getMessage());
        }
    }

    @PostMapping("/execute")
    public ResponseEntity<String> executeQueue() {
        try {
            taskService.executeQueue();
            return ResponseEntity.ok("Task execution started successfully.");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start task execution: " + e.getMessage());
        }
    }
    /*
    we should not use GET endoint here even though its not updating any records in db
    GET is Idempotent
    A GET request should not have side effects. It’s designed for fetching resources without altering the state of the system.
    Triggering executeQueue() initiates a process that changes the system state (e.g., task statuses, execution, etc.), even if indirectly.
    Running a process like this violates the intended use of GET.

    POST is Meant for Actions
    A POST request is appropriate for triggering an action or initiating processes like task execution.
    The key point is that POST allows for non-idempotent operations, which fits the nature of starting a background task.

    Caching Concerns with GET
    HTTP caches (e.g., proxies or browsers) may cache GET responses. Using GET to trigger task execution can lead to unintended behavior if the request is cached.

    REST Semantics
    RESTful APIs follow a convention where:
    GET is for retrieval.
    POST is for creating or triggering operations.
    PUT/PATCH is for updates.
    DELETE is for deletions.
    */

    @PostMapping("/stop")
    public ResponseEntity<String> stopTaskExecution() {
        try {
            taskService.stop();
            return ResponseEntity.ok("Task execution stopped successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to stop task execution: " + e.getMessage());
        }
    }


    /*
    A PUT request must be idempotent.
    This means multiple identical PUT requests should result in the same system state as a single request.
    Stopping a task executor involves changing the system state in a way that isn't idempotent
    (e.g., shutting down the executor and clearing tasks, which can’t be repeated for the same effect).
    */

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable("status") Status status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no tasks found
        }
        return ResponseEntity.ok(tasks); // 200 OK with task list
    }
}


