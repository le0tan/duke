package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    final String searchWord;

    public FindCommand(String searchWord) {
        this.searchWord = searchWord;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        List<Task> foundTasks = new ArrayList<>();
        for (Task t : tasks.getList()) {
            if (t.getDescription().contains(this.searchWord)) {
                foundTasks.add(t);
            }
        }
        return ui.showFoundTasks(foundTasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
