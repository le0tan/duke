package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for Duke app.
 */
public class Duke extends Application {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    Storage storage;
    TaskList tasks;

    /**
     * Constructs a Duke object with save file located at filePath.
     *
     * @param filePath file path of the save file
     */
    Duke(String filePath) {
        this.storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadFromSaveFile());
        } catch (DukeException e) {
            tasks = new TaskList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Duke() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        storage = new Storage();
        try {
            tasks = new TaskList(storage.loadFromSaveFile());
        } catch (DukeException e) {
            tasks = new TaskList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("/view/MainWindow.fxml"));

        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWING, window -> {
            MainWindowController controller = loader.getController();
            controller.setDuke(Duke.this);
        });

        // Create the Pane and all Details
        VBox root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Duke");
        primaryStage.show();
    }

    /**
     * Gets tasks of type ToDo.
     * @return a list of ToDo objects
     */
    List<ToDo> getTodos() {
        List<ToDo> todos = new ArrayList<>();
        for (Task t : tasks.getList()) {
            if (t instanceof ToDo) {
                todos.add((ToDo) t);
            }
        }
        return todos;
    }

    /**
     * Gets tasks of type Deadline.
     * @return a list of Deadline objects
     */
    List<Deadline> getDeadlines() {
        List<Deadline> deadlines = new ArrayList<>();
        for (Task t : tasks.getList()) {
            if (t instanceof Deadline) {
                deadlines.add((Deadline) t);
            }
        }
        return deadlines;
    }

    /**
     * Gets tasks of type Event.
     * @return a list of Event objects
     */
    List<Event> getEvent() {
        List<Event> events = new ArrayList<>();
        for (Task t : tasks.getList()) {
            if (t instanceof Event) {
                events.add((Event) t);
            }
        }
        return events;
    }

    /**
     * Gets all tasks in storage.
     * @return a list of Task
     */
    List<Task> getTasks() {
        return tasks.getList();
    }

    void updateStorage() throws IOException {
        storage.syncSaveFile(tasks);
    }
}