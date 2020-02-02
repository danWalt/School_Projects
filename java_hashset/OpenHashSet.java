/**
 * a class the implements an open hashset
 */

public class OpenHashSet extends SimpleHashSet implements HashSetCapacity {


    /**
     * HashSet array
     */
    private Brick[] hashSet;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */

    public OpenHashSet() {
        super();
        hashSet = new Brick[currentSize];
        for (int i = 0; i < capacity(); i++) {
            hashSet[i] = new Brick();
        }
    }


    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super();
        currentLowerLoadFactor = lowerLoadFactor;
        currentUpperLoadFactor = upperLoadFactor;
        hashSet = new Brick[currentSize];
        for (int i = 0; i < capacity(); i++) {
            hashSet[i] = new Brick();
        }
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */

    public OpenHashSet(java.lang.String[] data) {
        hashSet = new Brick[currentSize];
        for (int i = 0; i < data.length; i++) {
            add(data[i]);
        }

    }

    /**
     * capacity in class SimpleHashSet
     *
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return currentSize;
    }

    /**
     * tries adding a given string to the hashset
     *
     * @param newValue value to add to the set
     * @return true iff the string was added successfully
     */
    @Override
    public boolean add(String newValue) {
        float currentLoadFactor = (float) (size() + 1) / capacity();
        int brick = calculateHash(newValue);
        if (hashSet[brick] == null) {
            hashSet[brick] = new Brick();
        }
        if (!hashSet[brick].contains(newValue)) {
            if (currentLoadFactor > getUpperLoadFactor()) {
                rehash(INCREASE_SIZE);
                return addAfterRehash(newValue);
            }
            hashSet[brick].add(newValue);
            setSize(1);
            return true;

        }
        return false;
    }

    /*incase of a rehash this method adds the string caused the need of a rehash*/
    private boolean addAfterRehash(String valueToAdd) {
        int brick = calculateHash(valueToAdd);
        if (hashSet[brick] == null) {
            hashSet[brick] = new Brick();
        }
        hashSet[brick].add(valueToAdd);
        setSize(1);
        return true;
    }

    /*if needed, this method sets a bigger/smaller set and adds all current string to their appropriate
    location in the new sized hashset*/
    private void rehash(double resizeFactor) {
        currentSize *= resizeFactor;
        Brick[] setToRehash = hashSet;
        hashSet = new Brick[currentSize];

        for (int i = 0; i < hashSet.length; i++) {
            hashSet[i] = new Brick();
        }
        for (int i = 0; i < setToRehash.length; i++) {
            if (setToRehash[i] != null) {
                int size = setToRehash[i].size();
                for (int j = 0; j < size; j++) {
                    String tempElement = setToRehash[i].pop();
                    int index = calculateHash(tempElement);
                    hashSet[index].add(tempElement);
                }
            }
        }
    }

    /*returns an index in the hashset for a given value*/
    private int calculateHash(String valueToAdd) {
        return clamp(valueToAdd.hashCode());
    }

    /**
     * checks if the current closed hashset contains the given string
     * @param searchVal the value to search for
     * @return true iff the hashset contains the string
     */
    @Override
    public boolean contains(String searchVal) {
        int brick = clamp(searchVal.hashCode());
        return !(hashSet[brick] == null) && hashSet[brick].contains(searchVal);

    }

    @Override
    public boolean delete(String toDelete) {
        int brick = clamp(toDelete.hashCode());
        if (hashSet[brick] == null || !hashSet[brick].contains(toDelete)) {
            return false;
        }
        hashSet[brick].remove(toDelete);
        setSize(-1);
        float currentLoadFactor = (float) (size()) / capacity();
        if (currentLoadFactor < getLowerLoadFactor() && capacity() > 1) {
            rehash(DECREASE_SIZE);
        }
        return true;
    }

    /**
     * @return number of elements in the set
     */
    @Override
    public int size() {
        return super.size();
    }


}
