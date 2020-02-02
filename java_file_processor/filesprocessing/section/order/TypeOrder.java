package filesprocessing.section.order;

import java.io.File;

class TypeOrder extends Order {

    /*a typeOrder object*/
    private static final TypeOrder typeOrder = new TypeOrder();


    /**
     * @return class typeOrder object
     */
    public static TypeOrder getOrder() {
        return typeOrder;
    }


    /**
     * compares 2 given files by file type. If type is the same, compare by absolute path.
     *
     * @param file1 - file to compare
     * @param file2 - file to compare
     * @return 1 if file1 type is first, -1 if second file type should come first. If files are of the same
     * type, a comparision between the absolute path is done.
     */

    @Override
    public int compare(File file1, File file2) {
        String type1 = file1.getAbsolutePath();
        String type2 = file2.getAbsolutePath();
        String file1Type = type1.substring(type1.lastIndexOf(".") + 1);
        String file2Type = type2.substring(type2.lastIndexOf(".") + 1);
        if (file1Type.compareTo(file2Type) == 0) {
            return AbsOrder.getOrder().compare(file1, file2);
        }
        return file1Type.compareTo(file2Type);
    }
}
