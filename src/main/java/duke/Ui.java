package duke;

import duke.task.Task;

import java.util.List;

public class Ui {

    /**
     * Print the string of content with break lines and indentation.
     *
     * @param content
     */
    public static String formattedPrint(String content) {
        return content;
    }

    /**
     * Print the beautified error message.
     * @param errorMessage
     */
    public String showError(String errorMessage) {
        return formattedPrint("☹ OOPS!!! " + errorMessage);
    }

    /**
     * Print the beautified loading error message.
     */
    public String showLoadingError() {
        return showError("loading error");
    }

    /**
     * Print the beautified welcome message.
     */
    public String showWelcomeMessage() {
        return ("Hello! I'm Duke.\n"
                + "What can I do for you?\n");
    }

    /**
     * Show tasks.
     * @param tasks
     */
    public String showTasks(TaskList tasks) {
        return listTasks(tasks.getList(), "Here are the tasks in your list:");
    }


    private String listTasks(List<Task> tasks, String promptMessage) {
        StringBuilder builder = new StringBuilder(promptMessage);
        for (int i = 0; i < tasks.size(); i++) {
            builder.append("\n" + "     ");
            builder.append(i + 1).append(".").append(tasks.get(i).toString());
        }
        return formattedPrint(builder.toString());
    }

    /**
     * Show found tasks from the "find" command.
     *
     * @param tasks
     */
    public String showFoundTasks(List<Task> tasks) {
        return listTasks(tasks, "Here are the matching tasks in your list:");
    }

    /**
     * Prompt user of successfully adding a task.
     * @param task
     * @param tasks
     */
    public String showAddTaskMessage(Task task, TaskList tasks) {
        String output = "Got it. I've added this task: " + "\n" + "       "
                + task.toString() + "\n" + "     "
                + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task in the list." : " tasks in the list.");
        return formattedPrint(output);
    }

    /**
     * Prompt user of successfully deleting a task.
     * @param tasks
     * @param index
     */
    public String showDeleteTaskMessage(TaskList tasks, int index) {
        String tempOut = "Noted. I've removed this task: " + "\n" + "       "
                + tasks.get(index).toString() + "\n" + "     "
                + "Now you have " + (tasks.size() - 1)
                + (tasks.size() - 1 == 1 ? " task in the list." : " tasks in the list.");
        return formattedPrint(tempOut);
    }

    /**
     * Prompt user of successfully finishing a task.
     * @param tasks
     * @param index
     */
    public String showDoneTaskMessage(TaskList tasks, int index) {
        String tempOut = "Nice! I've marked this task as done: " + "\n" + "       "
                + tasks.get(index).toString();
        return formattedPrint(tempOut);
    }

    /**
     * Print the beautified farewell message.
     */
    public String showExitMessage() {
        return formattedPrint("Bye. Hope to see you again soon!");
    }

    /**
     * Prompt user of successfully creating a new save data file.
     */
    public static String showCreateSaveFileMessage() {
        return formattedPrint("data.json not found. Created a new one for you~");
    }
}
