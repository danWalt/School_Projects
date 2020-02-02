import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class wraps a collection to be a set as we want for hashsets.
 */
public class CollectionFacadeSet implements SimpleSet {

    /*a collection to be wrapped*/
    private Collection<String> collection;

    /**
     * implements the wrapping of a given java collection.
     *
     * @param collection - The Collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        this.collection = clearingDuplicates(collection);
    }

    /*this method makes sure the given collection has no duplicates in it*/
    private Collection<String> clearingDuplicates(java.util.Collection<java.lang.String> collection) {
        SortedSet<String> set = new TreeSet<>(collection);
        collection.clear();
        collection.addAll(set);
        return collection;
    }

    /**
     * tries adding a given string to the hashset
     *
     * @param newValue value to add to the set
     * @return true iff the string was added successfully
     */
    @Override
    public boolean add(String newValue) {
        if (!collection.contains(newValue)) {
            collection.add(newValue);
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the current closed hashset contains the given string
     *
     * @param searchVal the value to search for
     * @return true iff the hashset contains the string
     */
    @Override
    public boolean contains(String searchVal) {
        return collection.contains(searchVal);
    }

    /**
     * tries deleting a given value from the hashset
     *
     * @param toDelete value to delete
     * @return true iff the value was deleted successfully
     */
    @Override
    public boolean delete(String toDelete) {
        if (collection.contains(toDelete)) {
            collection.remove(toDelete);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return number of elements in the set
     */
    @Override
    public int size() {
        return collection.size();
    }


}
