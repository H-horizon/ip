package command.parser;

import arthur.date.ArthurDate;
import exceptions.IllegalCommandException;

import task.list.Deadline;
import task.list.Event;
import task.list.Task;
import ui.UI;

import java.util.ArrayList;

/**
 * Represents the search command
 */
public class SearchCommand extends Command {

    /**
     * search events and dates that matches date input in search command
     *
     * @param tasks    is the list of tasks
     * @param sentence is the inputted line in array format
     * @throws IllegalCommandException if command is in incorrect format
     */
    public static void searchTasksDates(ArrayList<Task> tasks, String[] sentence) throws IllegalCommandException {
        if (sentence.length != NUMBER_OF_COMMAND_ARGUMENTS) {
            throw new IllegalCommandException();
        }
        String date = ArthurDate.getSearchDateFormat(sentence[DESCRIPTION_INDEX_IN_COMMANDS]);
        int counter = 0;
        printMatchFound(tasks, date, counter);
    }

    /**
     * prints matches obtained
     *
     * @param tasks   is the list of tasks
     * @param date    is the date inputted
     * @param counter is the current number of matches found
     */
    private static void printMatchFound(ArrayList<Task> tasks, String date, int counter) {
        for (Task task : tasks) {
            counter = incrementCounter(date, counter, task);
        }
        if (counter == EMPTY) {
            UI.printNoMatchFound();
        } else {
            UI.printSearchComplete();
        }
    }

    /**
     * Increments counter if a match is found
     *
     * @param date    is the inputted date
     * @param counter is the current number of matches found
     * @param task    is one task in the list
     * @return is the updated number of matches found
     */
    private static int incrementCounter(String date, int counter, Task task) {
        String taskDate;
        if (task instanceof Event) {
            Event temp = (Event) task;
            taskDate = ArthurDate.reformatsSearch(temp.getTaskBy());
            if (taskDate.equals(date)) {
                counter++;
                temp.printTask();
            }

        } else if (task instanceof Deadline) {
            Deadline temp = (Deadline) task;
            taskDate = ArthurDate.reformatsSearch(temp.getTaskBy());
            if (taskDate.equals(date)) {
                counter++;
                temp.printTask();
            }
        }
        return counter;
    }
}
