package filesprocessing.section.filter;

import java.io.File;
import java.util.LinkedList;

/**
 * This class represents filters that filter by file name.
 */
class NameFilter extends Filter {


    /*
     * A Boolean value that tells whether to return the files for the original filter, or exactly all
     * files that the original filter does not return.
     */
    private boolean not;


    /*
     * The type of the filter.
     */
    private String filterType;

    /*
     * The value.
     */
    private String value;

    /*
    a linkedlist holding the files that passed the filtering process
     */
    private LinkedList<File> filterFileList;


    /**
     * Constructor of name filter.
     */
    public NameFilter(String value, boolean not, String filterType) {
        this.value = value;
        this.not = not;
        this.filterType = filterType;
        filterFileList = new LinkedList<>();
    }

    /**
     * @return a linked list with the files that passed the filtering process
     */
    public LinkedList<File> getFilterFileList() {
        return filterFileList;
    }

    /**
     * @return a linked list with the files corresponding to the filter.
     */
    public LinkedList<File> filterFileList(LinkedList<File> files) {
        if (filterType.equals("file"))
            filterByFile(files);
        else if (filterType.equals("contains"))
            filterByContains(files);
        else if (filterType.equals("prefix"))
            filterByPrefix(files);
        else if (filterType.equals("suffix"))
            filterBySuffix(files);
        return filterFileList;
    }

    /*
    filters the original files list and returns only the files that passed the filter
     */
    private void filterByFile(LinkedList<File> files) {
        for (File file : files)
            if (!not) {
                if (file.getName().equals(value))
                    filterFileList.add(file);
            } else {
                if (!file.getName().equals(value))
                    filterFileList.add(file);
            }
    }

    /*
    filters the original files list and returns only the files that passed the filter
     */
    private void filterByContains(LinkedList<File> files) {
        for (File file : files) {
            if (!not) {
                if (file.getName().contains(value))
                    filterFileList.add(file);
            } else {
                if (!file.getName().contains(value))
                    filterFileList.add(file);
            }
        }
    }

    /*
    filters the original files list and returns only the files that passed the filter
     */
    private void filterByPrefix(LinkedList<File> files) {
        for (File file : files) {
            if (!not) {
                if (file.getName().startsWith(value))
                    filterFileList.add(file);
            } else {
                if (!file.getName().startsWith(value))
                    filterFileList.add(file);
            }
        }
    }

    /*
    filters the original files list and returns only the files that passed the filter
     */
    private void filterBySuffix(LinkedList<File> files) {
        for (File file : files) {
            if (!not) {
                if (file.getName().endsWith(value))
                    filterFileList.add(file);
            } else {
                if (!file.getName().endsWith(value))
                    filterFileList.add(file);
            }
        }
    }
}
