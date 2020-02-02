package filesprocessing;

import filesprocessing.section.Section;
import filesprocessing.section.WarningException;

import java.io.File;
import java.util.LinkedList;

/**
 * This class prints the errors and filtered and ordered files.
 */
public class Printer {

    /**
     * the function prints the warning exception and the right files in the correct order.
     *
     * @param sections the different sections to iterate over and print their files.
     */
    public static void printer(LinkedList<Section> sections) {
        int i; // an index that is set at the starting point of the print session
        File currentFile;
        for (Section section : sections) {
            for (String warningException : section.getWarningExceptions())
                System.err.println(warningException);
            if (section.isReverse()) {
                i = section.getRightFiles().size() - 1; // i is the last index in the list and goes down to
                // the first object
                while (i >= 0) {
                    currentFile = section.getRightFiles().get(i--);
                    if (currentFile != null) {
                        System.out.println(currentFile.getName());
                    }
                }
            } else {
                i = 0; // i starts at the beginning of the list and increases in each iteration until it is
                // at the last file
                while (section.getRightFiles().size() > i) {
                    currentFile = section.getRightFiles().get(i++);
                    if (currentFile != null)
                        System.out.println(currentFile.getName());
                }
            }
        }
    }
}
