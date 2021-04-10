package Project4;

/**
 * The Customizable interface is used to define add and remove behaviors for menu items and orders.
 * @author Prof. Chang
 */

public interface Customizable {
    /**
     * Adds an object.
     * @param obj the object to add
     * @return true if object successfully added, false otherwise
     */
    boolean add(Object obj);

    /**
     * Removes an object.
     * @param obj the object to remove
     * @return true if object successfully removed, false otherwise
     */
    boolean remove(Object obj);
}
