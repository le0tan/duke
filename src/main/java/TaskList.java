import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void remove(int index) {
        tasks.remove(index);
    }

    public List<Task> getList() {
        return tasks;
    }

    public void addTask(Task task, Ui ui, Storage storage) throws IOException, JSONException {
        tasks.add(task);
        storage.appendToSaveFile(task);
        ui.showAddTaskMessage(task, this);
    }
}