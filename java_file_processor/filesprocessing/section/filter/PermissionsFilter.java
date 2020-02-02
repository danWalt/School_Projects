package filesprocessing.section.filter;

import java.io.File;
import java.util.LinkedList;

/**
 * This class represents filters that filter by file permissions.
 */
class PermissionsFilter extends Filter {

    /*
     * A variable representing the required permission  - yes or no.
     */
    private String permission;


    /*
     * Boolean variable representing permission - if it's yes : true , if it's no: false.
     */
    private boolean permissionBoolean;

    /*
     * The type of the filter.
     */
    private String filterType;

    /*
     * A Boolean value that tells whether to return the files for the original filter, or exactly all
     * files that the original filter does not return.
     */
    private boolean not;

    /* string that represent the permission of filter permission. */
    private static final String YES_PERMISSION = "YES";

    /* An constant strings that represent the filter's type. */
    private static final String EXECUTABLE_FILTER = "executable";
    private static final String WRITABLE_FILTER = "writable";

    /*
    a linkedlist holding the files that passed the filtering process
     */
    private LinkedList<File> filterFileList;

    /**
     * Constructor of permissions filter.
     */
    public PermissionsFilter(String permission, boolean not, String filterType) {
        this.permission = permission;
        this.not = not;
        this.filterType = filterType;
        checkPermission(permission);
        filterFileList = new LinkedList<>();

    }

    /**
     * The function checks the given permission and declares the Boolean variable 'permissionBoolean'
     * accordingly.
     *
     * @param permission a variable representing the required permission  - yes or no.
     */
    private void checkPermission(String permission) {
        permissionBoolean = (permission.compareTo(YES_PERMISSION) == 0);
    }

    /**
     * @return a linked list with the files corresponding to the filter.
     */
    public LinkedList<File> filterFileList(LinkedList<File> files) {
        if (filterType.compareTo(WRITABLE_FILTER) == 0) {
            if (!not)
                filterByWritable(permissionBoolean, files);
            else
                filterByWritable(!permissionBoolean, files);
        } else if (filterType.compareTo(EXECUTABLE_FILTER) == 0) {
            if (!not)
                filterByExecutable(permissionBoolean, files);
            else
                filterByExecutable(!permissionBoolean, files);
        } else {
            if (!not)
                filterByHidden(permissionBoolean, files);
            else
                filterByHidden(!permissionBoolean, files);
        }
        return filterFileList;
    }


    /*
     * the function filter the files by writable according to input.
     */
    private void filterByWritable(boolean permission, LinkedList<File> files) {
        for (File file : files) {
            if (permission) {
                if (file.canWrite())
                    filterFileList.add(file);
            } else {
                if (!file.canWrite())
                    filterFileList.add(file);
            }
        }
    }


    /*
     * the function filter the files by executable according to input.
     */
    private void filterByExecutable(boolean permission, LinkedList<File> files) {
        for (File file : files) {
            if (permission) {
                if (file.canExecute())
                    filterFileList.add(file);
            } else {
                if (!file.canExecute())
                    filterFileList.add(file);
            }
        }
    }


    /*
     * the function filter the files by hidden according to input.
     */
    private void filterByHidden(boolean permission, LinkedList<File> files) {
        for (File file : files) {
            if (permission) {
                if (file.isHidden())
                    filterFileList.add(file);
            } else {
                if (!file.isHidden())
                    filterFileList.add(file);
            }
        }
    }
}
