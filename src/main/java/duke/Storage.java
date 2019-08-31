package duke;

import duke.task.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Storage {

    private String filePath;

    /**
     * Constructs a Storage object with a custom file path.
     *
     * @param filePath file path of the save file
     */
    Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Constructs a Storage object with a default file path.
     *
     */
    Storage() {
        this.filePath = getSaveFilePath();
    }

    /**
     * Get the default file path specific to platform.
     * @return default file path specific to platform
     */
    private String getSaveFilePath() {
        if (System.getProperty("os.name").equals("Windows 10")) {
            return "/Users/uicfa/Downloads/data.json";
        } else {
            return "/Users/leo/Downloads/data.json";
        }
    }

    /**
     * Creates the save file (used when it doesn't exist).
     * @throws IOException
     */
    private void createSaveFile() throws IOException {
        File file = new File(this.filePath);
        if (!file.createNewFile()) {
            throw new IOException();
        }
        writeToSaveFile("{\"data\":[]}");
    }

    /**
     * Read and parse the save file.
     * @return A parsed JSONObject from the save file
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject readSaveFile() throws IOException, JSONException {
        // TODO: handle the exception where data.json doesn't exist or format is wrong
        InputStream is;
        try {
            is = new FileInputStream(this.filePath);
        } catch (FileNotFoundException e) {
            createSaveFile();
            is = new FileInputStream(this.filePath);
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        return new JSONObject(new JSONTokener(sb.toString()));
    }

    /**
     * Write a string to the save file (which overwrites the old content).
     * @param content a String to be written into the save file
     * @throws IOException
     */
    private void writeToSaveFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(this.filePath);
        fileWriter.write(content);
        fileWriter.close();
    }

    /**
     * Write the record of a Task to the end of the save file.
     * @param task the Task to be appended
     * @throws IOException
     * @throws JSONException
     */
    void appendToSaveFile(Task task) throws IOException, JSONException {
        JSONObject obj = readSaveFile();
        obj.append("data", task.toMap());
        writeToSaveFile(obj.toString());
    }

    /**
     * Overwrites the save file so that it stays the same as the internal TaskList.
     * @param tasks the TaskList to be synced with the save file
     * @throws IOException
     * @throws JSONException
     */
    void syncSaveFile(TaskList tasks) throws IOException, JSONException {
        JSONObject obj = new JSONObject();
        obj.put("data", new ArrayList<>());
        for (Task t : tasks.getList()) {
            obj.append("data", t.toMap());
        }
        writeToSaveFile(obj.toString());
    }

    /**
     * Read and parse the save file to a List of Task.
     * @return the list of tasks
     * @throws IOException
     * @throws JSONException
     * @throws DukeException
     */
    List<Task> loadFromSaveFile() throws IOException, JSONException, DukeException {
        List<Task> tasks = new ArrayList<>();
        JSONObject obj = readSaveFile();
        JSONArray dataArray = obj.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            tasks.add(Task.fromJson(dataArray.getJSONObject(i)));
        }
        return tasks;
    }
}
