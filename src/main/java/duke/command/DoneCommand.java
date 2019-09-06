package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

import java.io.IOException;

public class DoneCommand extends Command {
    final int index;

    public DoneCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.get(index).markAsDone();
        storage.syncSaveFile(tasks);
        return ui.showDoneTaskMessage(tasks, index);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
