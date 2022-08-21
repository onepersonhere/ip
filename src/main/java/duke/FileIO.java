package duke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

/**
 * FileIO handles the loading and saving tasks in the file.
 */
public class FileIO {
    /**
     * Save the list of tasks to a file.
     *
     * @param list List of tasks to save
     * @param filePath The path of the file to save to
     * @throws DukeException if the file cannot be saved
     */
    public static void save(StorageList list, String filePath) throws DukeException {
        try {
            createFile(filePath);
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < list.getSize(); i++) {
                bw.write(list.get(i).toString());
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("File not found.");
        } catch (IOException e) {
            throw new DukeException("Error writing to file.");
        }
    }

    /**
     * Loads the tasks from the file.
     *
     * @param list List of tasks to load into
     * @param filePath The path of the file to load from
     * @throws DukeException if the file cannot be loaded
     */
    public static void load(StorageList list, String filePath) throws DukeException {
        try {
            list.reset();
            createFile(filePath);
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    parseLine(line, list);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("File not found.");
        } catch (IOException e) {
            throw new DukeException("Error reading from file.");
        }
    }

    /**
     * Parses a line of text and adds the task to the list.
     *
     * @param line The line of text to parse
     * @param list The list to add the task to
     */
    private static void parseLine(String line, StorageList list) throws DukeException {
        String description = getDescription(line);
        String addition = getAddition(line);
        Task task;
        switch (line.charAt(1)) {
        case 'D':
            task = new Deadline(description, addition);
            break;
        case 'E':
            task = new Event(description, addition);
            break;
        case 'T':
            task = new Todo(description);
            break;
        default:
            task = new Task(description);
        }
        list.add(task);
    }

    /**
     * Returns the description of the task.
     *
     * @param line The line of text to parse
     * @return The description of the task
     */
    private static String getDescription(String line) {
        if (line.indexOf(':') == -1) {
            return line.substring(7);
        } else {
            return line.substring(7, line.indexOf(" (") + 1);
        }
    }

    /**
     * Returns the additional information of the task.
     *
     * @param line The line of text to parse
     * @return The additional information of the task
     */
    private static String getAddition(String line) {
        if (line.indexOf(':') == -1) {
            return "";
        } else {
            return line.substring(line.indexOf(":") + 2, line.indexOf(")"));
        }
    }

    /**
     * Creates the file if it doesn't exist.
     *
     * @param filePath The path of the file to create
     * @throws IOException If the file cannot be created
     */
    private static void createFile(String filePath) throws IOException {
        new File(filePath).createNewFile();
    }
}
