package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    
    private TaskService taskService;
    
    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }
    
    @Test
    void testCreateTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        
        // Act
        Task createdTask = taskService.createTask(task);
        
        // Assert
        assertNotNull(createdTask);
        assertNotNull(createdTask.getId());
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Test Description", createdTask.getDescription());
        assertFalse(createdTask.isCompleted());
    }
    
    @Test
    void testGetAllTasks() {
        // Arrange
        Task task1 = new Task();
        task1.setTitle("Task 1");
        Task task2 = new Task();
        task2.setTitle("Task 2");
        
        taskService.createTask(task1);
        taskService.createTask(task2);
        
        // Act
        List<Task> tasks = taskService.getAllTasks();
        
        // Assert
        assertEquals(2, tasks.size());
    }
    
    @Test
    void testGetTaskById_ExistingTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Test Task");
        Task createdTask = taskService.createTask(task);
        
        // Act
        Optional<Task> foundTask = taskService.getTaskById(createdTask.getId());
        
        // Assert
        assertTrue(foundTask.isPresent());
        assertEquals(createdTask.getId(), foundTask.get().getId());
        assertEquals("Test Task", foundTask.get().getTitle());
    }
    
    @Test
    void testGetTaskById_NonExistingTask() {
        // Act
        Optional<Task> foundTask = taskService.getTaskById(999L);
        
        // Assert
        assertFalse(foundTask.isPresent());
    }
    
    @Test
    void testUpdateTask_ExistingTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Original Title");
        task.setDescription("Original Description");
        Task createdTask = taskService.createTask(task);
        
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        
        // Act
        Optional<Task> result = taskService.updateTask(createdTask.getId(), updatedTask);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals("Updated Description", result.get().getDescription());
    }
    
    @Test
    void testUpdateTask_NonExistingTask() {
        // Arrange
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        
        // Act
        Optional<Task> result = taskService.updateTask(999L, updatedTask);
        
        // Assert
        assertFalse(result.isPresent());
    }
    
    @Test
    void testDeleteTask_ExistingTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Test Task");
        Task createdTask = taskService.createTask(task);
        
        // Act
        boolean deleted = taskService.deleteTask(createdTask.getId());
        
        // Assert
        assertTrue(deleted);
        assertFalse(taskService.getTaskById(createdTask.getId()).isPresent());
    }
    
    @Test
    void testDeleteTask_NonExistingTask() {
        // Act
        boolean deleted = taskService.deleteTask(999L);
        
        // Assert
        assertFalse(deleted);
    }
    
    @Test
    void testMarkTaskAsCompleted_ExistingTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Test Task");
        Task createdTask = taskService.createTask(task);
        
        // Act
        Optional<Task> result = taskService.markTaskAsCompleted(createdTask.getId());
        
        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().isCompleted());
        assertNotNull(result.get().getCompletedAt());
    }
    
    @Test
    void testMarkTaskAsCompleted_NonExistingTask() {
        // Act
        Optional<Task> result = taskService.markTaskAsCompleted(999L);
        
        // Assert
        assertFalse(result.isPresent());
    }
    
    @Test
    void testMultipleTasksHaveUniqueIds() {
        // Arrange & Act
        Task task1 = taskService.createTask(new Task(null, "Task 1", "Desc 1"));
        Task task2 = taskService.createTask(new Task(null, "Task 2", "Desc 2"));
        Task task3 = taskService.createTask(new Task(null, "Task 3", "Desc 3"));
        
        // Assert
        assertNotEquals(task1.getId(), task2.getId());
        assertNotEquals(task2.getId(), task3.getId());
        assertNotEquals(task1.getId(), task3.getId());
    }
}
