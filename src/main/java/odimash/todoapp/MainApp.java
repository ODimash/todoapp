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
