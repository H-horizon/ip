package command.parser;

import exceptions.IllegalCommandException;
import exceptions.IllegalTaskException;
import exceptions.IllegalTaskRedoException;
import task.list.Task;

import java.util.ArrayList;

/**
 * Represents the add command
 */
public class DoneCommand extends Command {

    public static final int INDEX_POSITION = 1;

    /**
     * mark the task indicated by the index inputted by the user as done
     *
     * @param sentence is the inputted line in array format
     * @param tasks    is the lists of task
     * @throws IllegalCommandException  if done does not have enough arguments
     * @throws IllegalTaskException     if task does not exist
     * @throws IllegalTaskRedoException if task was already marked as done
     */
    public static void markTaskAsDone(String[] sentence, ArrayList<Task> tasks) throws IllegalCommandException, IllegalTaskException,
            IllegalTaskRedoException {
        int index;
        try {
            index = getIndex(sentence, tasks);
        } catch (IllegalCommandException e) {
            throw new IllegalCommandException();
        } catch (IllegalTaskException e) {
            throw new IllegalTaskException();
        }

        Task t = tasks.get(index - 1);
        try {
            t.markAsDone();
        } catch (IllegalTaskRedoException e) {
            throw new IllegalTaskRedoException();
        }
        tasks.set(index - 1, t);
        ListCommand.printNumberOfTasksLeft(tasks);
    }

    /**
     * tries to get index from the input
     *
     * @param sentence is the inputted line in array format
     * @param tasks    is the list of tasks
     * @return index from input
     * @throws IllegalCommandException if command is in invalid format
     * @throws IllegalTaskException    task does not exist
     */
    public static int getIndex(String[] sentence, ArrayList<Task> tasks) throws IllegalCommandException,
            IllegalTaskException {
        if (sentence.length > NUMBER_OF_COMMAND_ARGUMENTS) {
            throw new IllegalCommandException();
        }
        int index = getIndexFromCommand(sentence[INDEX_POSITION]);
        if (index > tasks.size() || index < 1) {
            throw new IllegalTaskException();
        }
        return index;
    }

    /**
     * gets index from the input
     *
     * @param index is the index from the command
     * @return validated index
     */
    public static int getIndexFromCommand(String index) {
        try {
            return Integer.parseInt(index);
        } catch (NumberFormatException nfe) {
            return WRONG_INDEX;
        }
    }
}