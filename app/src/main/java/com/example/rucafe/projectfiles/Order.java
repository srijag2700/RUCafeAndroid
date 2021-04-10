package Project4;

import java.util.ArrayList;

/**
 * This class represents an order in the cafe.
 * An order includes fields such as the list of menu items, unique order number and subtotal.
 * @author Srija Gottiparthi, Catherine Nguyen
 */


public class Order implements Customizable {
    private ArrayList<MenuItem> order;
    private int orderNumber;
    private double subtotal;
    private static final double NJ_SALES_TAX_RATE = 0.06625;

    /**
     * Initializes a new Order object with an empty array size.
     */
    public Order() {
        order = new ArrayList<MenuItem>();
    }

    /**
     * Initializes a new Order with an empty array size and order number.
     * @param orderNumber the number of the order
     */
    public Order(int orderNumber) {
        order = new ArrayList<MenuItem>();
        this.orderNumber = orderNumber;
    }

    /**
     * Initializes a new Order with given order details such as order number and subtotal.
     * @param o the given order
     */
    public Order (Order o) {
        this.order = o.order;
        this.orderNumber = o.orderNumber;
        this.subtotal = o.subtotal;
    }

    /**
     * Set the order's number.
     * @param orderNumber number of the order
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Returns the order number.
     * @return order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Adds either a Coffee or Donut object to the order.
     * @param obj the object to add
     * @return true if object is successfully added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (obj instanceof Coffee) {
            Coffee newCoffee = (Coffee) obj;
            newCoffee.itemPrice();
            order.add(newCoffee);
            subtotal += newCoffee.getPrice();
            return true;
        }
        else if (obj instanceof Donut) {
            Donut newDonut = (Donut) obj;
            newDonut.itemPrice();
            order.add(newDonut);
            subtotal += newDonut.getPrice();
            return true;
        }
        return false;
    }

    /**
     * Removes either a Coffee or a Donut object from the order
     * @param obj the object to remove
     * @return true if object is successfully removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Coffee) {
            Coffee newCoffee = (Coffee) obj;
            newCoffee.itemPrice();
            order.remove(newCoffee);
            subtotal -= newCoffee.getPrice();
            return true;
        }
        else if (obj instanceof Donut) {
            Donut newDonut = (Donut) obj;
            newDonut.itemPrice();
            order.remove(newDonut);
            subtotal -= newDonut.getPrice();;
            return true;
        }
        return false;
    }

    /**
     * Returns the order
     * @return order
     */
    public ArrayList<MenuItem> getOrder() {
        return order;
    }

    /**
     * Returns the subtotal of the order
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Returns the tax of the order
     * @return tax
     */
    public double getTax() {
        return subtotal * NJ_SALES_TAX_RATE;
    }

    /**
     * Returns the total of the order
     * @return total
     */
    public double getTotal() {
        return subtotal + getTax();
    }

    /**
     * Returns a string representation of the order
     * @return string representation of order
     */
    @Override
    public String toString() {
        String orderDetails = "Order #" + orderNumber + ": ";
        for (MenuItem m : order) {
            orderDetails += m.toString() + "; ";
        }
        return orderDetails;
    }
}
