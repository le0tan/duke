package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;

import java.io.IOException;

public class AddCommand extends Command {
    final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        return tasks.addTask(this.task, ui, storage);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
