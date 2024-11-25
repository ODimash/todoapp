# TODO Application with JavaFX and Maven
-----
Osmanov Dinmukhamed
Computing technology and software
----
## Project Overview

This project involves creating a simple TODO application using JavaFX and Maven for build automation. The application allows users to add, delete, and mark tasks as completed. The project structure follows the MVC (Model-View-Controller) pattern and demonstrates the use of classes and inheritance.

## Project Structure

The project is organized into several packages and classes as follows:

### Packages

- `odimash.todoapp`
  - `controller`
    - `TaskController`
  - `model`
    - `Task`
    - `SimpleTask`
    - `ImportantTask`

### Classes

1. **Task**: An abstract base class representing a generic task.
2. **SimpleTask**: A class representing a simple task, inheriting from `Task`.
3. **ImportantTask**: A class representing an important task, inheriting from `Task`.
4. **TaskController**: A controller class managing the list of tasks and providing methods to add, remove, and mark tasks as completed.
5. **MainApp**: The main application class responsible for setting up the JavaFX UI and interacting with the controller.

## Detailed Implementation

### Model

#### Task.java
```java
package odimash.todoapp.model;

public abstract class Task {
    protected String description;
    protected boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[COMPLETED] " : "") + description;
    }
}
```

#### SimpleTask.java
```java
package odimash.todoapp.model;

public class SimpleTask extends Task {

    public SimpleTask(String description) {
        super(description);
    }
}
```

#### ImportantTask.java
```java
package odimash.todoapp.model;

public class ImportantTask extends Task {
    public ImportantTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return (isCompleted ? "[COMPLETED] " : "") + "[IMPORTANT] " + description;
    }
}
```

### Controller

#### TaskController.java
```java
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
```

### Main Application

#### MainApp.java
```java
package odimash.todoapp;

import odimash.todoapp.controller.TaskController;
import odimash.todoapp.model.Task;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private ListView<Task> listView;
    private TextField textField;
    private CheckBox importantCheckBox;
    private TaskController taskController;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TODO Application");

        taskController = new TaskController();

        listView = new ListView<>();
        textField = new TextField();
        textField.setPromptText("Enter a new task");
        importantCheckBox = new CheckBox("Important");

        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> addTask());

        Button removeButton = new Button("Remove Task");
        removeButton.setOnAction(e -> removeTask());

        Button completeButton = new Button("Mark as Completed");
        completeButton.setOnAction(e -> markTaskAsCompleted());

        taskController.getTasks().addListener((ListChangeListener<Task>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    listView.getItems().addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    listView.getItems().removeAll(c.getRemoved());
                }
            }
        });

        VBox vbox = new VBox(10, listView, textField, importantCheckBox, addButton, removeButton, completeButton);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 300, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTask() {
        String taskDescription = textField.getText();
        boolean isImportant = importantCheckBox.isSelected();
        if (!taskDescription.isEmpty()) {
            taskController.addTask(taskDescription, isImportant);
            textField.clear();
            importantCheckBox.setSelected(false);
        }
    }

    private void removeTask() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskController.removeTask(selectedTask);
        }
    }

    private void markTaskAsCompleted() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskController.markTaskAsCompleted(selectedTask);
            listView.refresh();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

## Features

1. **Add Task**: Users can add a new task by entering a description and marking it as important if needed.
2. **Remove Task**: Users can remove a selected task from the list.
3. **Mark Task as Completed**: Users can mark a selected task as completed, which updates the task's display in the list.

## How to Run

To run the application, use the following Maven command:

```sh
mvn clean javafx:run
```

This will compile and run the JavaFX application, allowing you to interact with the TODO list.
