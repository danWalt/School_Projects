package filesprocessing.section;

import filesprocessing.section.order.Order;

import java.io.File;
import java.util.LinkedList;

import filesprocessing.section.filter.Filter;

/**
 * This class represent a section that has a filter and an order.
 */
public class Section {

    /* the specific filter of the section */
    private Filter filterSection;

    /* the specific order of the section */
    private Order orderSection;

    /* a list with all the exceptions. */
    private LinkedList<String> warningExceptions;

    /* the file after filtering and ordering (without reverse) */
    private LinkedList<File> rightFiles;

    /* represents whether to print in plain order or reverse file names. */
    private boolean reverse;

    /**
     * constructor for Section.
     *
     * @param order             how should the files be filtered
     * @param filter            how should the remaining files be ordered
     * @param warningExceptions a list with all the exceptions thrown during sorting.
     * @param reverse           represents whether to print in regular order or reverse file names.
     */

    public Section(Order order, Filter filter, LinkedList<String> warningExceptions, boolean reverse) {
        orderSection = order;
        filterSection = filter;
        this.warningExceptions = warningExceptions;
        this.reverse = reverse;
    }

    /**
     * @return true if reverse printing is required, false otherwise.
     */
    public boolean isReverse() {
        return reverse;
    }

    /**
     * @return the list that contains all the warning exceptions.
     */
    public LinkedList<String> getWarningExceptions() {
        return warningExceptions;
    }

    /**
     * @return a list with the files after filtering and ordering.
     */
    public LinkedList<File> getRightFiles() {
        return this.rightFiles;
    }

    /**
     * this function receives a list with all the files and filters and then orders them by the filter and
     * ordered needed.
     *
     * @param files the files to filter and order.
     */
    public void process(LinkedList<File> files) {
        LinkedList<File> currentList = new LinkedList<>();
        currentList.addAll(files);
        this.rightFiles = filterSection.filterFileList(currentList);
        this.rightFiles.sort(orderSection);
    }


}
