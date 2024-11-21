package odimash.todoapp;

import odimash.todoapp.controller.TaskController;
import odimash.todoapp.model.Task;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainAppController {

    @FXML
    private ListView<Task> listView;

    @FXML
    private TextField textField;

    @FXML
    private CheckBox importantCheckBox;

    private TaskController taskController;

    @FXML
    public void initialize() {
        taskController = new TaskController();

        taskController.getTasks().addListener((ListChangeListener<Task>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    listView.getItems().addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    listView.getItems().removeAll(c.getRemoved());
                }
            }
        });
    }

    @FXML
    private void addTask() {
        String taskDescription = textField.getText();
        boolean isImportant = importantCheckBox.isSelected();
        if (!taskDescription.isEmpty()) {
            taskController.addTask(taskDescription, isImportant);
            textField.clear();
            importantCheckBox.setSelected(false);
        }
    }

    @FXML
    private void removeTask() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskController.removeTask(selectedTask);
        }
    }

    @FXML
    private void markTaskAsCompleted() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskController.markTaskAsCompleted(selectedTask);
            listView.refresh();
        }
    }
}
