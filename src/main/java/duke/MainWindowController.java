package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainWindowController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private AnchorPane masterPane;
    @FXML
    private ListView<String> masterListView;
    @FXML
    private ScrollPane listPane;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private ListView<Task> middleListView;
    @FXML
    private MenuItem fileNew;
    @FXML
    private MenuItem fileOpen;
    @FXML
    private Label detailLabel;
    @FXML
    private Label detailTypeLabel;
    @FXML
    private Label detailDescriptionLabel;
    @FXML
    private Label detailTimeLabel;
    @FXML
    private Text detailTypeText;
    @FXML
    private TextArea detailDescriptionTextArea;
    @FXML
    private Text detailTimeText;

    private Duke duke;

    void setDuke(Duke duke) {
        this.duke = duke;
        displayTaskList.addAll(duke.getTasks());
        middleListView.setItems(displayTaskList);
    }

    private void setDetailVisible(boolean visible) {
        detailTypeLabel.setVisible(visible);
        detailTypeText.setVisible(visible);
        detailDescriptionLabel.setVisible(visible);
        detailDescriptionTextArea.setVisible(visible);
        if (!visible) {
            detailTimeLabel.setVisible(false);
            detailTimeText.setVisible(false);
        }
    }

    private void setDetailTime(String label, String time) {
        detailTimeLabel.setText(label);
        detailTimeText.setText(time);
        detailTimeLabel.setVisible(true);
        detailTimeText.setVisible(true);
    }

    private void hideDetailTime() {
        detailTimeLabel.setVisible(false);
        detailTimeText.setVisible(false);
    }


    Duke getDuke() {
        return duke;
    }

    void refreshView() {
        switch (masterListView.getSelectionModel().getSelectedItem()) {
        case "To-Do":
            displayTaskList.setAll(duke.getTodos());
            break;
        case "Deadline":
            displayTaskList.setAll(duke.getDeadlines());
            break;
        case "Event":
            displayTaskList.setAll(duke.getEvent());
            break;
        case "All":
            displayTaskList.setAll(duke.getTasks());
            break;
        default:
            break;
        }
    }

    private static class CustomListCell extends ListCell<Task> {
        private HBox content;
        private Text title;
        private Text subtitle;
        private Label hboxLead;

        CustomListCell() {
            super();
            title = new Text();
            subtitle = new Text();
            hboxLead = new Label("Status");
            content = new HBox(hboxLead, new VBox(title, subtitle));
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                if (item instanceof Deadline) {
                    subtitle.setText("by " + ((Deadline) item).getBy().toString());
                } else if (item instanceof Event) {
                    subtitle.setText("at " + ((Event) item).getAt().toString());
                } else {
                    subtitle.setText("To-Do");
                }
                title.setText(item.getDescription());
                hboxLead.setText(item.getStatusIcon());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    private ObservableList<Task> displayTaskList;

    public MainWindowController() {
        displayTaskList = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {

        assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert fileNew != null : "fx:id=\"fileNew\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert fileOpen != null : "fx:id=\"fileOpen\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert masterPane != null : "fx:id=\"masterPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert masterListView != null :
                "fx:id=\"masterListView\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailPane != null : "fx:id=\"detailPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert middleListView != null :
                "fx:id=\"middleListView\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailLabel != null : "fx:id=\"detailLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailTypeLabel != null :
                "fx:id=\"detailTypeLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailDescriptionLabel != null :
                "fx:id=\"detailDescriptionLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailTimeLabel != null :
                "fx:id=\"detailTimeLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailTypeText != null :
                "fx:id=\"detailTypeText\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailDescriptionTextArea != null :
                "fx:id=\"detailDescriptionTextArea\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert detailTimeText != null :
                "fx:id=\"detailTimeText\" was not injected: check your FXML file 'MainWindow.fxml'.";

        setDetailVisible(false);
        middleListView.setCellFactory(param -> {

            ListCell<Task> cell = new CustomListCell();
            // MenuItem for delete task
            MenuItem deleteItem = new MenuItem();
            deleteItem.setText("Delete");
            deleteItem.setOnAction(event -> {
                duke.getTasks().remove(cell.getItem());
                try {
                    duke.updateStorage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshView();
            });
            // MenuItem for finish task
            MenuItem doneItem = new MenuItem();
            doneItem.setText("Done");
            doneItem.setOnAction(event -> {
                // TODO: Add undone feature
                int index = duke.getTasks().indexOf(cell.getItem());
                duke.getTasks().get(index).markAsDone();
                try {
                    duke.updateStorage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshView();
            });
            // TODO: MenuItem for edit task
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().add(deleteItem);
            contextMenu.getItems().add(doneItem);
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    setDetailVisible(true);
                    Task item = cell.getItem();
                    detailDescriptionTextArea.setText(item.getDescription());
                    String type;
                    if (item instanceof ToDo) {
                        type = "To-Do";
                        hideDetailTime();
                    } else if (item instanceof Deadline) {
                        type = "Deadline";
                        setDetailTime("by", ((Deadline) item).getBy().toString());
                    } else if (item instanceof Event) {
                        type = "Event";
                        setDetailTime("at", ((Event) item).getAt().toString());
                    } else {
                        type = "Unknown";
                    }
                    detailTypeText.setText(type);
                }
            });

            return cell;
        });
        ObservableList<String> items = FXCollections.observableArrayList(
                "To-Do", "Deadline", "Event", "All");
        masterListView.setItems(items);
        masterListView.setOnMouseClicked(event -> {
            setDetailVisible(false);
            refreshView();
        });
        masterListView.getSelectionModel().select(3);
    }

    @FXML
    void createNewTask(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("/view/NewTaskWindow.fxml"));
            Parent root = loader.load();
            loader.<NewTaskWindowController>getController().setParentController(this);
            Stage stage = new Stage();
            stage.setTitle("Create New Task");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openFile(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        duke = new Duke(selectedFile.getAbsolutePath());
        refreshView();
    }
}
