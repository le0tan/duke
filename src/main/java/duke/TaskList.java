package duke;

import duke.task.Task;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    TaskList() {
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

    List<Task> getList() {
        return tasks;
    }

    /**
     * Add a task to TaskList, output the result using Ui.
     *
     * @param task
     * @param storage
     * @throws IOException
     * @throws JSONException
     */
    void addTask(Task task, Storage storage) throws IOException, JSONException {
        tasks.add(task);
        storage.appendToSaveFile(task);
    }
}