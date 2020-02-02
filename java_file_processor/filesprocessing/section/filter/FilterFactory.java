package filesprocessing.section.filter;

import filesprocessing.section.WarningException;

/**
 * this class create a new filter.
 */
public class FilterFactory {

        private static String[] argsArray;

    /* Specifies when to separate the string.*/
    private final static String separator = "#";

    /* constant string that represents the filter's type.*/
    private static final String NOT = "NOT";
    private static final String BETWEEN_FILTER = "between";
    private static final String SMALLER_FILTER = "smaller_than";
    private static final String GREATER_FILTER = "greater_than";
    private static final String FILE_FILTER = "file";
    private static final String CONTAINS_FILTER = "contains";
    private static final String SUFFIX_FILTER = "suffix";
    private static final String PREFIX_FILTER = "prefix";
    private static final String ALL_FILTER = "all";
    private static final String HIDDEN_FILTER = "hidden";
    private static final String EXECUTABLE_FILTER = "executable";
    private static final String WRITABLE_FILTER = "writable";

    /* strings that represent the permission of filter permission. */
    private static final String YES_PERMISSION = "YES";
    private static final String NO_PERMISSION = "NO";

    /**
     * when no filter is given, a default filter returning ALL FILTER is returned.
     */
    public static Filter DEFAULT_FILTER = new AllFilter(false);


    /**
     * @param args string with all the input - filter name, values and more.
     * @return an array that contain all this data.
     */
    private static String[] separator(String args) {
        argsArray = args.split(separator);
        return argsArray;
    }


    /**
     * attempts to choose the right filter to assign to the section according to the input.
     *
     * @param args a string that contains the type of the requested filter and the values.
     * @param line the line in which a warning is thrown
     * @return a specific filter object.
     * @throws WarningException if the string describing the filter wanted does not match any of the
     *                          programmed filters, a warning is thrown and the default filter is used.
     */
    public static Filter createFilter(String args, int line) throws WarningException {

        if (args == null)
            return new AllFilter(null);

        argsArray = separator(args); //moves the values into a list.

        boolean not = false;
        if (args.contains(NOT)) //check if contains NOT suffix.
            not = true;

        try {
            switch (argsArray[0]) {

                // == size filter == //
                case BETWEEN_FILTER:
                    double minVal = Double.parseDouble(argsArray[1]);
                    double maxVal = Double.parseDouble(argsArray[2]);
                    if (maxVal > minVal && minVal >= 0)
                        return new SizeFilter(minVal, maxVal, not, argsArray[0]);
                    else
                        throw new WarningException(line);

                case SMALLER_FILTER:
                case GREATER_FILTER:
                    double value = Double.parseDouble(argsArray[1]);
                    if (value >= 0 && (argsArray.length == 2 || argsArray[2].equals(NOT)))
                        return new SizeFilter(value, not, argsArray[0]);
                    else
                        throw new WarningException(line);


                    // == name filter == //
                case FILE_FILTER:
                case CONTAINS_FILTER:
                case SUFFIX_FILTER:
                case PREFIX_FILTER:
                    return new NameFilter(argsArray[1], not, argsArray[0]);

                // == permission filter == //
                case WRITABLE_FILTER:
                case EXECUTABLE_FILTER:
                case HIDDEN_FILTER:
                    if (argsArray[1].compareTo(YES_PERMISSION) == 0 || argsArray[1].compareTo
                            (NO_PERMISSION) == 0)
                        return new PermissionsFilter(argsArray[1], not, argsArray[0]);
                    else
                        throw new WarningException(line);

                    // == all filter == //
                case ALL_FILTER:
                    return new AllFilter(not);

                default:
                    throw new WarningException(line);
            }
        } catch (NumberFormatException e) {
            throw new WarningException(line);
        } catch (IndexOutOfBoundsException e) {
            throw new WarningException(line);
        }
    }
}
