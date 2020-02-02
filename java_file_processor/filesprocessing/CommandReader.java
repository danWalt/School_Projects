package filesprocessing;

import filesprocessing.section.Section;
import filesprocessing.section.SectionFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class CommandReader {

    /*order subsection offset*/
    private static final int ORDER_SUB_SECTION_OFFSET = 2;

    /*search for new section*/
    private static final int NEW_SECTION_START = 4;

    /*commands file is empty*/
    private static final int COMMAND_FILE_IS_EMPTY = 0;

    /*the filter command offset*/
    private static final int FILTER_COMMAND_OFFSET = 1;

    /*the order COMMAND offset*/
    private static final int ORDER_COMMAND_OFFSET = 3;

    /* adjust computer counting to file lines counting*/
    private static final int ADJUST_ROW_NUMBER_START_AT_1 = 1;

    /*search for a new section start if filter found*/
    private static final int UPDATE_INDEX_IF_FILTER_FOUND = 3;

    /*search for a new section start if filter not found*/
    private static final int UPDATE_INDEX_IF_FILTER_NOT_FOUND = 1;

    /*a string printed when there is an error with the file given*/
    private static final String PROBLEM_WITH_FILE = "There is a problem with the input file";

    /*a string printed when there is an error with the ORDER/FILTER sub-section*/
    private static final String BAD_SUBSECTION_NAME = "ERROR: Bad subsection name";

    /*a string printed when there is an error with the sections file format*/
    private static final String BAD_FORMAT = "ERROR: Bad format of Commands File";

    /*a linked list that holds all of the section objects*/
    private LinkedList<Section> sectionLinkedList;

    /*a string that represent the correct first line in the sub-section. */
    private static final String FILTER = "FILTER";

    /*a string that represent the correct third line in the sub-section. */
    private static final String ORDER = "ORDER";

    /**
     * The constructor for the objec that process the command file into sections.
     *
     * @param commandFile a given file that holds the parameters of the sections.
     * @throws ErrorException if there is a problem with the format.
     */
    public CommandReader(File commandFile) throws ErrorException {
        File commandsFiles = commandFile;
        ArrayList<String> linesArray = buildLinesArray(commandsFiles);
        sectionLinkedList = buildSection(linesArray);
    }

    /*process the lines in the commands files into different sections*/
    private LinkedList<Section> buildSection(ArrayList<String> linesArray) {
        LinkedList<Section> sectionLinkedList = new LinkedList<>();
        int i = 0;
        while (i < linesArray.size()) {
            if (linesArray.get(i).equals(FILTER)) {
                String filter = linesArray.get(i + FILTER_COMMAND_OFFSET);
                String order = null;
                if (i + ORDER_COMMAND_OFFSET < linesArray.size()) {
                    order = linesArray.get(i + ORDER_COMMAND_OFFSET);
                }
                Section currentSection = SectionFactory.createSection(filter, order, i + ADJUST_ROW_NUMBER_START_AT_1);
                sectionLinkedList.add(currentSection);
                i += UPDATE_INDEX_IF_FILTER_FOUND;
            } else {
                i += UPDATE_INDEX_IF_FILTER_NOT_FOUND;
            }
        }
        return sectionLinkedList;
    }

    /*convert file to an array list containing all lines in the file*/
    private static ArrayList<String> buildLinesArray(File commandsFiles) throws ErrorException {
        ArrayList<String> linesArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(commandsFiles))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                linesArray.add(line);
            }
        } catch (IOException e) {
            throw new ErrorException(PROBLEM_WITH_FILE);
        }
        checkForType2Errors(linesArray);
        return linesArray;
    }

    /*check for bad format of commands file*/
    private static void checkForType2Errors(ArrayList<String> linesArray) throws ErrorException {
        if (linesArray.size() == COMMAND_FILE_IS_EMPTY) {
            return;
        } else if (!(FILTER).equals(linesArray.get(0))) {
            throw new ErrorException(BAD_SUBSECTION_NAME);
        }
        for (int i = 0; i < linesArray.size(); i++) {
            if (linesArray.get(i).equals(FILTER)) {
                if (i % NEW_SECTION_START == 0) {
                    if (i + ORDER_SUB_SECTION_OFFSET >= linesArray.size()) {
                        throw new ErrorException(BAD_FORMAT);
                    }
                    if (!(linesArray.get(i + ORDER_SUB_SECTION_OFFSET).equals(ORDER))) {
                        throw new ErrorException(BAD_SUBSECTION_NAME);
                    }
                }
            }
            if (linesArray.get(i).equals(ORDER)) {
                if (i % 4 == 0) { // ORDER placement in an organized command file
                    if (!(linesArray.get(i - 2).equals(FILTER))) { // difference between order
                        // subsection to it's matching filter subsection
                        throw new ErrorException(BAD_SUBSECTION_NAME);
                    }
                }
            }
        }
    }

    /**
     * @return a linkedlist containing all of the created section objects
     */
    public LinkedList<Section> getSectionArray() {
        return sectionLinkedList;
    }
}


