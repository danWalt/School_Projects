package filesprocessing.section.filter;

import java.io.File;
import java.util.LinkedList;

/**
 * This abstract class represent a simple filter.
 */
public abstract class Filter {

	/**
	 * @param files the list of the files.
	 * @return a linked list with the files corresponding to the filter.
	 */
	public abstract LinkedList<File> filterFileList(LinkedList<File> files);

}
