package filesprocessing.section.order;

import filesprocessing.section.SectionFactory;
import filesprocessing.section.WarningException;
import filesprocessing.section.filter.Filter;

/**
 * A class creating different orders object depends on input arguments.
 */
public class OrderFactory {


    /*represents the absolute path sorting argument*/
    private static final String SORT_BY_ABS_NAME = "abs";

    /*represents the type sorting argument*/
    private static final String SORT_BY_FILE_TYPE = "type";

    /*represents the size sorting argument*/
    private static final String SORT_BY_FILE_SIZE = "size";

    /*represents a new section opener*/
    private static final String NEW_SECTION = "FILTER";


    /* Specifies when to separate the string.*/
    private static String separator = "#";

    /**
     * @return a default order.
     */
    public static Order defaultOrder() {
        SectionFactory.setReverse(false);
        return new AbsOrder();
    }

    /**
     * creates different types of Order objects based on input
     *
     * @param order         order input, determines the ordering way
     * @param currentFilter Required filter for files to pass
     * @param line          number of line this Order input is in the FILTERS and ORDERS file
     * @return a new Order object that compares the files by the required parameter
     * @throws WarningException if order input isn't a known input, an exception is thrown and files are
     *                          sorted by absolute path
     */
    public static Order makeOrder(String order, Filter currentFilter, int line) throws WarningException {
        if (order == null)
            return defaultOrder();

        String[] orderList = order.split(separator);
        String orderType = orderList[0];

        switch (orderType) {
            case SORT_BY_ABS_NAME:
            case NEW_SECTION:
                return new AbsOrder(); // sort by absolute path
            case SORT_BY_FILE_SIZE:
                return new SizeOrder(); // sort by file size
            case SORT_BY_FILE_TYPE:
                return new TypeOrder(); // sort by file type
            default:
                throw new WarningException(line); // sort by absolute path and throw exception
        }
    }


}
