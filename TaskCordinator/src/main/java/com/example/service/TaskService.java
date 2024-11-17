package service;

import model.Priority;
import model.Task;

public interface TaskService {

    int addTask(String title, Priority priority, int execTime, String serviceProvider);
    List<Task> findByStatus(Status status);


}


