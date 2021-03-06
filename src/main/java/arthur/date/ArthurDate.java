package arthur.date;

import exceptions.IllegalDateException;
import task.list.Deadline;
import task.list.Event;
import ui.UI;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Scanner;

/**
 * represents arthur's date format
 */
public class ArthurDate {
    public static final String EMPTY_STRING = "";
    public static String INITIAL_FORMAT = "yyyy/MM/dd HHmm";
    public static String FINAL_FORMAT = "MM-dd-yyyy HH:mm";
    public static String SEARCH_FORMAT = "yyyy/MM/dd";

    /**
     * reformats date to store as by
     *
     * @param by is the initial by attribute of a task
     * @return final by attribute
     */
    public static String reformatInput(String by) {
        SimpleDateFormat formatter = new SimpleDateFormat(INITIAL_FORMAT);

        boolean isFormatIncorrect;
        Date inputDate;
        do {
            try {
                isDateValid(by.trim());
                inputDate = formatter.parse(by.trim());
                formatter.applyPattern(FINAL_FORMAT);
                return formatter.format(inputDate);
            } catch (ParseException | IllegalDateException e) {
                UI.printInsertCorrectDateTask();
                UI.printDottedLines();
                Scanner in = new Scanner(System.in);
                by = in.nextLine();
                isFormatIncorrect = true;
            }
        } while (isFormatIncorrect);
        return EMPTY_STRING;
    }

    /**
     * reformats by to store as date in file
     *
     * @param by is the final by attribute
     * @return initial by attribute of a task
     */
    public static String reformatOutput(String by) {
        if (by.equals(Deadline.NO_BY) || by.equals(Event.NO_BY)) {
            return EMPTY_STRING;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FINAL_FORMAT);
        boolean isFormatInCorrect;
        Date inputDate;
        do {
            try {
                inputDate = formatter.parse(by);
                formatter.applyPattern(INITIAL_FORMAT);
                return formatter.format(inputDate);
            } catch (ParseException e) {
                isFormatInCorrect = true;
            }
        } while (isFormatInCorrect);
        return EMPTY_STRING;

    }

    /**
     * returns correct format of the date to search
     *
     * @param date is the date inputted by user
     * @return validates date inputted as correct
     */
    public static String getSearchDateFormat(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(SEARCH_FORMAT);
        boolean isFormatInCorrect;
        Date inputDate;
        do {
            try {
                inputDate = formatter.parse(date.trim());
                return formatter.format(inputDate);
            } catch (ParseException e) {
                UI.printInsertCorrectDateSearch();
                UI.printDottedLines();
                Scanner in = new Scanner(System.in);
                date = in.nextLine();
                isFormatInCorrect = true;
            }
        } while (isFormatInCorrect);
        return EMPTY_STRING;
    }

    /**
     * reformats date of task to that needed by search command
     *
     * @param date is the bye attribute of a task
     * @return the search format for that task to compare with the inputted one
     */
    public static String reformatsSearch(String date) {
        if (date.equals(Deadline.NO_BY) || date.equals(Event.NO_BY)) {
            return EMPTY_STRING;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FINAL_FORMAT);
        Date dateParsed;
        try {
            dateParsed = formatter.parse(date);
        } catch (ParseException e) {
            return EMPTY_STRING;
        }
        formatter.applyPattern(SEARCH_FORMAT);
        return formatter.format(dateParsed);
    }

    /**
     * @param input takes in a date
     * @throws IllegalDateException if date is in invalid format
     */
    public static void isDateValid(String input) throws IllegalDateException {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral("/")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral("/")
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendLiteral(" ")
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .withChronology(IsoChronology.INSTANCE);
        ;
        try {
            LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalDateException();
        }
    }
}