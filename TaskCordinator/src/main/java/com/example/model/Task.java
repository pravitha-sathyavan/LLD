package model;

import java.time.LocalDateTime;

public class Task implements Comparable<Task> {

    @Auto
    private int id;
    private String name;
    private Priority priortity;
    private int execTime;
    private LocalDateTime createdTime;
    private Status status;
    private String sericeProviderId;

    public Task(int id, Priority priority, String title, int executionTime, Status status, String serviceProvider, LocalDateTime creationTime) {
        this.id = id;
        this.priority = priority;
        this.title = title;
        this.executionTime = executionTime;
        this.status = status;
        this.serviceProvider = serviceProvider;
        this.creationTime = creationTime;
    }

    @Override
    public int compareTo(Task other) {
        // Higher priority value is processed first; for ties, use creation time
        int priorityComparison = other.priority.ordinal() - this.priority.ordinal();
        return (priorityComparison != 0) ? priorityComparison : this.creationTime.compareTo(other.creationTime);
    }

/*
    What happens if two tasks have the same priority and timestamp?

If the priority and creation time are identical, the compareTo method returns 0, indicating that the two tasks are considered equal in terms of ordering.
Behavior of PriorityBlockingQueue
PriorityBlockingQueue does not impose a strict FIFO ordering for elements that are considered equal by the comparator. In such cases:
The queue may store tasks in any order (depending on internal implementation).
Tasks with the same priority and timestamp might not be processed in a deterministic order.
 */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Priority getPriortity() {
        return priortity;
    }

    public void setPriortity(Priority priortity) {
        this.priortity = priortity;
    }

    public int getExecTime() {
        return execTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSericeProviderId() {
        return sericeProviderId;
    }

    public void setSericeProviderId(String sericeProviderId) {
        this.sericeProviderId = sericeProviderId;
    }
}
