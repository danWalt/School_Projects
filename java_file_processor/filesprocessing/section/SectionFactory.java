package filesprocessing.section;

import filesprocessing.section.filter.*;
import filesprocessing.section.order.*;

import java.util.LinkedList;

/**
 * this class creates a new section.
 */
public class SectionFactory {

    /* Points to the arrangement of the order, whether normal or reversed. */
    private static final String REVERSE = "REVERSE";

    /*keeps track of printing order*/
    private static boolean reverse;

    /**
     * updates printing order requirement based on input
     *
     * @param reverse whether or not file names should be printed reversed
     */
    public static void setReverse(boolean reverse) {
        SectionFactory.reverse = reverse;
    }

    /**
     * the function creates a new section object according to the input while dealing with errors.
     *
     * @param dataFilter A string that represents filter information - type, values, and more.
     * @param dataOrder  A string that represents order information - type, values, and more.
     * @param line       The line at which the 'section' begins.
     * @return a new section according to the input.
     */
    public static Section createSection(String dataFilter, String dataOrder, int line) {

        Order sectionOrder;
        Filter sectionFilter;

        LinkedList<String> warningExceptionsList = new LinkedList<String>();

        sectionFilter = findFilter(dataFilter, line, warningExceptionsList);
        reverse = (dataOrder != null && dataOrder.endsWith(REVERSE)); ////****
        sectionOrder = findOrder(dataOrder, line, warningExceptionsList, reverse, sectionFilter);

        return new Section(sectionOrder, sectionFilter, warningExceptionsList, reverse);
    }

    /*
     * this function finds the right filter of a given section.
     */
    private static Filter findFilter(String dataFilter, int line, LinkedList<String>
            warningExceptionsList) {
        try {
            return FilterFactory.createFilter(dataFilter, line + 1);
        } catch (WarningException e) {
            warningExceptionsList.add(e.getWarningMessage());
            return FilterFactory.DEFAULT_FILTER;
        }
    }

    /*
     * this function finds the right order of a given section.
     */
    private static Order findOrder(String dataOrder, int line, LinkedList<String>
            warningExceptionsList, boolean reverse, Filter sectionFilter) {
        try {
            return OrderFactory.makeOrder(dataOrder, sectionFilter, line + 3);
        } catch (WarningException e) {
            reverse = false;
            warningExceptionsList.add(e.getWarningMessage());
            return OrderFactory.defaultOrder();
        }
    }

}
