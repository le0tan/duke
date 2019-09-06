package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {
    final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.remove(index);
        storage.syncSaveFile(tasks);
        return ui.showDeleteTaskMessage(tasks, index);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
