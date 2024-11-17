package model;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

@Bean
public class TaskManager {
    private List<Task> taskList = new ArrayList<>();
    private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

    public Queue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Queue<Task> tasks) {
        this.tasks = tasks;
    }
}

/*
    static TaskManager instance;
    private TaskManager() {
    }

    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    The TaskManager class implements a custom singleton, which is not necessary in a Spring-based application. Spring itself manages singleton beans.
 */
