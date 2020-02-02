package filesprocessing.section.filter;

import java.io.File;
import java.util.LinkedList;

/**
 * This class represent a default filter - all of the given files are moving on to the sorting by a given
 * order process
 */
class AllFilter extends Filter {

	/*
	 * A Boolean value that tells whether to return the files for the original filter, or exactly all
	 * files that the original filter does not return.
	 */
	private boolean not;

	/*
	a linkedlist holding the files that passed the filtering process
	 */
	private LinkedList<File> filterFileList = new LinkedList<>();

	/**
	 * constructor for 'all' filter.
	 * @param not A Boolean value that tells whether to return the files for the original filter, or
	 *               exactly all files that the original filter does not return.
	 */
	public AllFilter (Boolean not){
		this.not = not;
	}

	/**
	 * adds the files that passed the filter to a linkedlist
	 * @param files the list of all starting files.
	 * @return a list with the files that passed the filtering process
	 */
	@Override
	public LinkedList<File> filterFileList(LinkedList<File> files) {
		filterFileList = new LinkedList<>();
		if(not)
			return filterFileList;
		return files;
	}
}
