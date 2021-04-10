package Project4;

/**
 * This class represents a menu item.
 * It includes fields such as the price for an item.
 * This is the parent class for Donut and Coffee classes.
 * @author Srija Gottiparthi, Catherine Nguyen
 */

public class MenuItem {
    double price;

    /**
     * Initializes a new MenuItem object with a default price of 0.
     */
    public MenuItem() {
        this.price = 0;
    }

    /**
     * Calculate the price of the menu item.
     * This method is overridden for each type of menu item.
     */
    public void itemPrice() { return; }

}
