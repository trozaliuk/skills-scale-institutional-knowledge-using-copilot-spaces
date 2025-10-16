package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    
    private final Map<Long, Task> taskStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public Task createTask(Task task) {
        Long id = idGenerator.getAndIncrement();
        task.setId(id);
        taskStorage.put(id, task);
        return task;
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskStorage.values());
    }
    
    public Optional<Task> getTaskById(Long id) {
        return Optional.ofNullable(taskStorage.get(id));
    }
    
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        if (!taskStorage.containsKey(id)) {
            return Optional.empty();
        }
        
        Task existingTask = taskStorage.get(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        
        return Optional.of(existingTask);
    }
    
    public boolean deleteTask(Long id) {
        return taskStorage.remove(id) != null;
    }
    
    public Optional<Task> markTaskAsCompleted(Long id) {
        Task task = taskStorage.get(id);
        if (task == null) {
            return Optional.empty();
        }
        
        task.setCompleted(true);
        return Optional.of(task);
    }
}
