/**
 * a class the implements a closed hashset
 */
public class ClosedHashSet extends SimpleHashSet implements HashSetCapacity {

    /*an index used in the calculation of the clamp method*/
    private static final int STARTING_INDEX = 0;
    /*an array of stringwrapper objects*/
    private StringWrapper[] hashSet;


    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */

    public ClosedHashSet() {
        super();
        hashSet = new StringWrapper[currentSize];
        hashingIndex = STARTING_INDEX;
        for (int i = 0; i < capacity(); i++) {
            hashSet[i] = new StringWrapper();
        }
    }


    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super();
        currentLowerLoadFactor = lowerLoadFactor;
        currentUpperLoadFactor = upperLoadFactor;
        hashSet = new StringWrapper[currentSize];
        hashingIndex = STARTING_INDEX;
        for (int i = 0; i < capacity(); i++) {
            hashSet[i] = new StringWrapper();
        }


    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */

    public ClosedHashSet(java.lang.String[] data) {
        super();
        hashSet = new StringWrapper[currentSize];
        hashingIndex = STARTING_INDEX;
        for (int i = 0; i < capacity(); i++) {
            hashSet[i] = new StringWrapper();
        }
        for (int i = 0; i < data.length; i++) {
            add(data[i]);

        }
    }

    /**
     * checks if the current closed hashset contains the given string
     * @param searchVal the value to search for
     * @return true iff the hashset contains the string
     */
    @Override
    public boolean contains(String searchVal) {
        hashingIndex = 0;
        int index = calculateHash(searchVal);
        while (!hashSet[index].getFlag() && hashingIndex < capacity() || hashSet[index].getString() != null
         && hashingIndex < capacity()) {
            if (searchVal.equals(hashSet[index].getString())) {
                return true;
            }
            hashingIndex++;
            index = calculateHash(searchVal);
        }
        return false;
    }


    /**
     * tries adding a given string to the hashset
     * @param newValue value to add to the set
     * @return true iff the string was added successfully
     */
    @Override
    public boolean add(String newValue) {
        if (!contains(newValue)) {
            hashingIndex = 0;
            int index = calculateHash(newValue);
            while (!hashSet[index].getFlag() && hashingIndex < capacity()) {
                hashingIndex++;
                index = calculateHash(newValue);
            }
            float currentLoadFactor = ((float) (size() + 1) / capacity());
            if (currentLoadFactor > getUpperLoadFactor()) {
                rehash(INCREASE_SIZE);
                return addAfterRehash(newValue);
            }
            hashSet[index] = new StringWrapper(newValue);
            setSize(1);
            return true;
        } else {
            return false;
        }
    }

    /*incase of a rehash this method adds the string caused the need of a rehash*/
    private boolean addAfterRehash(String valueToAdd) {
        hashingIndex = 0;
        int index = calculateHash(valueToAdd);
        while (!hashSet[index].getFlag() && hashingIndex < capacity()) {
            hashingIndex++;
            index = calculateHash(valueToAdd);
        }
        hashSet[index] = new StringWrapper(valueToAdd);
        setSize(1);
        return true;

    }

    /*if needed, this method sets a bigger/smaller set and adds all current string to their appropriate
    location in the new sized hashset*/
    private void rehash(double resizeFactor) {
        StringWrapper[] setToRehash = hashSet;
        currentSize *= resizeFactor;
        hashSet = new StringWrapper[currentSize];
        for (int i = 0; i < hashSet.length; i++) {
            hashSet[i] = new StringWrapper();
        }
        for (int i = 0; i < setToRehash.length; i++) {
            if (!setToRehash[i].getFlag() && setToRehash[i].getString() != null) {
                String currentString = setToRehash[i].getString();
                hashingIndex = 0;
                int index = calculateHash(currentString);
                while (!hashSet[index].getFlag() && hashingIndex < capacity()) {
                    hashingIndex++;
                    index = calculateHash(currentString);
                }
                hashSet[index] = new StringWrapper(currentString);
            }

        }
    }

    /**
     * tries deleting a given value from the hashset
     * @param toDelete value to delete
     * @return true iff the value was deleted successfully
     */
    @Override
    public boolean delete(String toDelete) {
        hashingIndex = 0;
        if (contains(toDelete)) {
            int index = calculateHash(toDelete);
            while (!hashSet[index].getFlag() && hashingIndex < capacity()) {
                if (toDelete.equals(hashSet[index].getString())) {
                    hashSet[index].clear();
                }
                hashingIndex++;
                index = calculateHash(toDelete);
            }
            setSize(-1);
            double currentLoadFactor = (double) ((size()) / (float) capacity());
            if (currentLoadFactor < getLowerLoadFactor()) {
                if (capacity() > 1) {
                    rehash(DECREASE_SIZE);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /*returns an index in the hashset for a given value*/
    private int calculateHash(String valueToAdd) {
        return clamp(valueToAdd.hashCode());
    }

    /**
     * @return number of elements in the set
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * capacity in class SimpleHashSet
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return currentSize;
    }
}
