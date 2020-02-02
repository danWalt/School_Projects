package filesprocessing;

import filesprocessing.section.Section;

import java.io.*;
import java.util.LinkedList;

/**
 * The main class that starts the file processing process
 */
public class DirectoryProcessor {

    /*the error printed if too many arguments are given*/
    private static final String TOO_MANY_ARGUMENTS = "Only 2 arguments are needed";

    /**
     * The main function that starts with processing the files.
     *
     * @param args 2 args are given. a folder containing files to sort and a file that contains FILTER and
     *             ORDER sections.
     * @throws ErrorException an Error is thrown if more then 2 arguments are given.
     */
    public static void main(String[] args) throws ErrorException {
        if (args.length > 2) {
            throw new ErrorException(TOO_MANY_ARGUMENTS);
        }
        try {
            File folder = new File(args[0]);
            File[] filesToSort = folder.listFiles();
            File commandsFiles = new File(args[1]);
            CommandReader commands = new CommandReader(commandsFiles);
            LinkedList<Section> sections;
            sections = commands.getSectionArray();


            LinkedList<File> filesLinkedList = new LinkedList<>();
            if (filesToSort != null) {
                for (File fileToSort : filesToSort) {
                    if (!fileToSort.isDirectory()) {
                        filesLinkedList.add(fileToSort);
                    }
                }
                //process the files in each section and returns an organized list
                for (Section section : sections) {
                    section.process(filesLinkedList);
                }
                // starts the printing of the filtered and sorted files.
                Printer.printer(sections);
            }
        } catch (ErrorException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}

