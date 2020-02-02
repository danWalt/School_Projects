package filesprocessing.section.filter;

import java.io.File;
import java.util.LinkedList;

/**
 * This class represents filters that filter by file size.
 */
class SizeFilter extends Filter {

    /*
     * A Boolean value that tells whether to return the files for the original filter, or exactly all
     * files that the original filter does not return.
     */
    private boolean not;

    /*
     * The name of the filter type.
     */
    private String filterType;

    /*
     * value for the filters smaller_than and grater_then.
     */
    private double value;

    /*
     * The minimum value for the between filter.
     */
    private double minValue;

    /*
     * The maximum value for between filter.
     */
    private double maxValue;

    /*
     * A integer that convert bytes to k-bytes.
     */
    private static final int CONVERT_TO_K_BYTES = 1024;

    /*
     * represent the filter "grater_then"
     */
    private static final int GRATER_THEN = 1;

    /*
     * represent the filter "smaller_then"
     */
    private static final int SMALLER_THEN = 2;

    /*
     * represent the filter "between"
     */
    private static final int BETWEEN = 3;

    /*
     * A boolean value that indicate the type of the filters. True for - grater_then and smaller_then and
     * false for between.
     */
    private boolean flag;

    /*
    a linkedlist holding the files that passed the filtering process
     */
    private LinkedList<File> filterFileList;


    /**
     * constructor for "grater_then" and "smaller_then" filters.
     */
    public SizeFilter(Double value, Boolean not, String filterType) {
        this.value = value * CONVERT_TO_K_BYTES;
        this.not = not;
        this.filterType = filterType;
        flag = true;
        filterFileList = new LinkedList<>();
    }

    /**
     * constructor for "grater_then" and "smaller_then" filters.
     */
    public SizeFilter(Double minValue, Double maxValue, Boolean not, String filterName) {
        this.minValue = minValue * CONVERT_TO_K_BYTES;
        this.maxValue = maxValue * CONVERT_TO_K_BYTES;
        this.not = not;
        this.filterType = filterName;
        flag = false;
        filterFileList = new LinkedList<File>();

    }


    /**
     * @param files the list of the files.
     * @return a linked list with the files corresponding to the filter.
     */
    @Override
    public LinkedList<File> filterFileList(LinkedList<File> files) {
        if (flag) {
            if (filterType.compareTo("greater_than") == 0) {
                if (!not)
                    doFilter(value, -1, GRATER_THEN, files);
                else
                    doFilter(value + 1, -1, SMALLER_THEN, files);
            } else {
                if (!not)
                    doFilter(value, -1, SMALLER_THEN, files);
                else
                    doFilter(value - 1, -1, GRATER_THEN, files);
            }
        } else {
            doFilter(minValue, maxValue, BETWEEN, files);
        }
        return filterFileList;
    }


    /*
     * The function inserts the matching files according to the input to a linked list.
     * @param value1 value filter (for between filter if the minValue)
     * @param value2 the maxValue for between filter (irrelevant for smaller\grater filter)
     * @param filter indicates if the filter is grater_then/smaller_then/between.
     * @param files  the files to filter them.
     */
    private void doFilter(double value1, double value2, int filter, LinkedList<File> files) {
        for (File file : files) {
            switch (filter) {
                case GRATER_THEN:
                    if (file.length() > value1)
                        filterFileList.add(file);
                    break;
                case SMALLER_THEN:
                    if (file.length() < value1)
                        filterFileList.add(file);
                    break;
                case BETWEEN:
                    double lenFile = file.length();
                    if (!not) {
                        if (lenFile >= value1 && lenFile <= value2)
                            filterFileList.add(file);
                    } else {
                        if (lenFile < value1 || lenFile > value2)
                            filterFileList.add(file);
                    }
                    break;
            }
        }
    }
}
