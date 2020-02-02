package filesprocessing.section.order;

import java.io.File;

class SizeOrder extends Order {

    /*a sizeOrder object*/
    private static final SizeOrder sizeOrder = new SizeOrder();


    /**
     * @return class sizeOrder object
     */
    public static SizeOrder getOrder() {
        return sizeOrder;
    }


    /**
     * compares 2 given files by size
     *
     * @param file1 - file to compare
     * @param file2 - file to compare
     * @return 1 if file1 is bigger, -1 if second file is bigger. If files are the same size, a comparision
     * by absolute path is preformed.
     */
    @Override
    public int compare(File file1, File file2) {
        double file1Size = file1.length();
        double file2Size = file2.length();
        if (file1Size > file2Size) {
            return 1;
        } else if (file1Size < file2Size) {
            return -1;
        } else {
            return AbsOrder.getOrder().compare(file1, file2);
        }
    }
}
