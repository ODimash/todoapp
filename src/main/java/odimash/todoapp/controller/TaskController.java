package odimash.todoapp.controller;

import odimash.todoapp.model.Task;
import odimash.todoapp.model.SimpleTask;
import odimash.todoapp.model.ImportantTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskController {
    private ObservableList<Task> tasks;

    public TaskController() {
        tasks = FXCollections.observableArrayList();
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(String description, boolean isImportant) {
        Task task;
        if (isImportant) {
            task = new ImportantTask(description);
        } else {
            task = new SimpleTask(description);
        }
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void markTaskAsCompleted(Task task) {
        task.setCompleted(true);
    }
}
