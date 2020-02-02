package filesprocessing.section.order;

import java.io.File;
import java.util.Comparator;

/**
 * an abstract order class the implements the comparator class. This requires all extending order classes
 * to implement a comparator
 */
public abstract class Order implements Comparator<File> {

    /**
     * a method comparing between 2 files.
     *
     * @param file1 file 1 to comapre
     * @param file2 file 2 to compare
     * @return 1 if file1 is first, 0 if file1 and file2 are the same, -1 if file2 is first
     */
    public abstract int compare(File file1, File file2);

}
