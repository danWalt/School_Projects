/**
 * an abstract class containing the main methods a hashset should implement
 */
public abstract class SimpleHashSet implements SimpleSet {

    /**
     * Describes the higher load factor of a newly created hash set
     */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;


    /**
     * Describes the lower load factor of a newly created hash set
     */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;


    /**
     * Describes the capacity of a newly created hash set
     */
    protected static final int INITIAL_CAPACITY = 16;

    /**
     * increase size factor
     */

    protected final double INCREASE_SIZE = 2;

    /**
     * decrease size factor
     */

    protected final double DECREASE_SIZE = 0.5;


    /**
     * HashSet current cell size
     */
    protected int currentSize;

    /**
     * Current upper load factor
     */
    protected float currentUpperLoadFactor;

    /**
     * Current lower load factor
     */
    protected float currentLowerLoadFactor;

    /**
     * Counts number of items in a set
     */
    private int elementsInSet;


    /**
     * closed hashing index
     */

    protected int hashingIndex = 0;

    public SimpleHashSet() {
        currentUpperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        currentLowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        currentSize = INITIAL_CAPACITY;
        elementsInSet = 0;


    }


    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     *
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */

    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        currentLowerLoadFactor = lowerLoadFactor;
        currentUpperLoadFactor = upperLoadFactor;
        currentSize = INITIAL_CAPACITY;
        elementsInSet = 0;
    }

    public SimpleHashSet(java.lang.String[] data) {
        currentUpperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        currentLowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        currentSize = INITIAL_CAPACITY;
        elementsInSet = 0;
    }


    /**
     * Checks how many objects the current table can hold
     *
     * @return The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity (see the exercise description for
     * details)
     *
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index) {
        return ((index + calculateHashIndex()) & (currentSize - 1));
    }


    private int calculateHashIndex() {
        return ((hashingIndex + (hashingIndex * hashingIndex)) / 2);
    }

    /**
     * get current hashtable lower load factor
     *
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor() {
        return currentLowerLoadFactor;
    }

    /**
     * get current hashtable upper load factor
     *
     * @return The upper load factor of the table.
     */

    protected float getUpperLoadFactor() {
        return currentUpperLoadFactor;
    }

    /**
     * tries adding a given string to the hashset
     * @param newValue value to add to the set
     * @return true iff the string was added successfully
     */
    @Override
    public boolean add(String newValue) {
        return false;
    }

    /**
     * checks if the current closed hashset contains the given string
     * @param searchVal the value to search for
     * @return true iff the hashset contains the string
     */
    @Override
    public abstract boolean contains(String searchVal);

    /**
     * tries deleting a given value from the hashset
     * @param toDelete value to delete
     * @return true iff the value was deleted successfully
     */
    @Override
    public boolean delete(String toDelete) {
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return elementsInSet;
    }

    /**
     * calculates new number of elements in set
     * @param valueToAdd how much should we add or remove
     */
    protected void setSize(int valueToAdd) {
        elementsInSet += valueToAdd;
    }

}
