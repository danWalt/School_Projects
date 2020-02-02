package filesprocessing.section.order;

import java.io.File;


class AbsOrder extends Order {

    /*an absOrder object*/
    private static final AbsOrder pathOrder = new AbsOrder();


    /**
     * @return class absOrder object
     */
    public static AbsOrder getOrder() {
        return pathOrder;
    }


    /**
     * compares 2 given files by absolute path
     *
     * @param file1 - file to compare
     * @param file2 - file to compare
     * @return 1 if file1 is first, 0 if files are the same, -1 if second file should come first.
     */
    @Override
    public int compare(File file1, File file2) {
        return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
    }
}
