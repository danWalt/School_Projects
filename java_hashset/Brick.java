/**
 * A class wrapping a linkedlist. used in the openhashset as objects of the array.
 */
public class Brick {

    /* java's linkedlist*/
    private java.util.LinkedList<String> linkedListWrapper;


    /**
     * constructing and empty linked list
     */
    public Brick() {
        linkedListWrapper = new java.util.LinkedList<String>();
    }

    /**
     * adds a string object to a given linkedlist
     * @param string the string that is added
     * @return true if added successfully, false otherwise
     */
    public boolean add(String string) {
        return linkedListWrapper.add(string);
    }

    /**
     * removes a string from the linkedlist
     * @param string the string to remove
     * @return true iff the string was removed successfully
     */
    public boolean remove(String string) {
        return linkedListWrapper.remove(string);
    }

    /**
     * checks if a string is already in the linkedlsit
     * @param string the string to chec
     * @return true iff the string is in the linkedlist
     */
    public boolean contains(String string) {
        return linkedListWrapper.contains(string);
    }

    /**
     * @return number of elements in the linkedlist
     */
    public int size() {
        return linkedListWrapper.size();
    }

    /**
     * @return first object in a linked list.
     */
    public String pop() {
        return linkedListWrapper.pop();
    }

}
