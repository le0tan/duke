package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.ToDo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class NewTaskWindowController {

    @FXML
    private TextArea descriptionTextField;
    @FXML
    private Button confirmButton;
    @FXML
    private ComboBox<String> typeSelector;
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker datePicker;

    private MainWindowController parentController;

    void setParentController(MainWindowController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void confirm(ActionEvent event) {
        TaskList taskList = parentController.getDuke().tasks;
        Storage storage = parentController.getDuke().storage;
        if (!typeSelector.getSelectionModel().isEmpty()) {
            String selected = typeSelector.getSelectionModel().getSelectedItem();
            try {
                switch (selected) {
                case "To-Do":
                    taskList.addTask(new ToDo(descriptionTextField.getText()), storage);
                    break;
                case "Deadline":
                    taskList.addTask(
                            new Deadline(descriptionTextField.getText(),
                                    datePicker.getValue().atTime(0, 0)),
                            storage);
                    break;
                case "Event":
                    taskList.addTask(
                            new Event(descriptionTextField.getText(),
                                    datePicker.getValue().atTime(0, 0)),
                            storage);
                    break;
                default:
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            parentController.refreshView();
        }

        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    @FXML
    void initialize() {

        assert descriptionTextField != null :
                "fx:id=\"descriptionTextField\" was not injected: check your FXML file 'NewTaskWindow.fxml'.";
        assert confirmButton != null :
                "fx:id=\"confirmButton\" was not injected: check your FXML file 'NewTaskWindow.fxml'.";
        assert typeSelector != null :
                "fx:id=\"typeSelector\" was not injected: check your FXML file 'NewTaskWindow.fxml'.";
        assert dateLabel != null :
                "fx:id=\"dateLabel\" was not injected: check your FXML file 'NewTaskWindow.fxml'.";
        assert datePicker != null :
                "fx:id=\"datePicker\" was not injected: check your FXML file 'NewTaskWindow.fxml'.";

        dateLabel.setVisible(false);
        datePicker.setVisible(false);
        ObservableList<String> items = FXCollections.observableArrayList("To-Do", "Deadline", "Event");
        typeSelector.setItems(items);
        typeSelector.setOnAction(event -> {
            String selected = typeSelector.getSelectionModel().getSelectedItem();
            switch (selected) {
            case "To-Do":
                dateLabel.setVisible(false);
                datePicker.setVisible(false);
                break;
            case "Deadline":
                dateLabel.setText("by");
                dateLabel.setVisible(true);
                datePicker.setVisible(true);
                break;
            case "Event":
                dateLabel.setText("at");
                dateLabel.setVisible(true);
                datePicker.setVisible(true);
                break;
            default:
                break;
            }
        });
    }

}
