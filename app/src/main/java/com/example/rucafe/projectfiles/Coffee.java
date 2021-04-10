package Project4;

import java.util.ArrayList;

/**
 * This class represents a Coffee item.
 * It extends the MenuItem class, and also includes fields for the size of the coffee, quantity and addins.
 * @author Srija Gottiparthi, Catherine Nguyen
 */

public class Coffee extends MenuItem implements Customizable {
    private String size;
    private int quantity;
    private ArrayList<String> addIns;
    private double price;

    private static final double SHORT_PRICE = 1.99;
    private static final double TALL_PRICE = 2.49;
    private static final double GRANDE_PRICE = 2.99;
    private static final double VENTI_PRICE = 3.49;
    private static final double ADDINS_PRICE = 0.20;

    /**
     * Initializes a new Coffee object with a given size, quantity and addins.
     * @param size size of coffee
     * @param quantity amount of coffee
     * @param addIns addins of coffee
     */
    public Coffee(String size, int quantity, ArrayList<String> addIns) {
        super();
        this.size = size;
        this.quantity = quantity;
        this.addIns = addIns;
    }

    /**
     * Adds an addin to the coffee order.
     * @param obj addin
     * @return true if successfully added, false if otherwise
     */
    public boolean add(Object obj) {
        if (obj instanceof String) {
            String newAddIn = (String) obj;
            addIns.add(newAddIn);
        }
        return false;
    }

    /**
     * Removes an addin from the coffee order.
     * @param obj addin
     * @return true if successfully removed, false if otherwise
     */
    public boolean remove(Object obj) {
        if (obj instanceof String) {
            String newAddIn = (String) obj;
            addIns.remove(newAddIn);
        }
        return false;
    }

    /**
     * Calculates the price of a donut.
     */
    @Override
    public void itemPrice() {
        // check to make sure this is only done once
        if (size.equals("Short")) {
            price = SHORT_PRICE;
        }
        else if (size.equals("Tall")) {
            price = TALL_PRICE;
        }
        else if (size.equals("Grande")) {
            price = GRANDE_PRICE;
        }
        else if (size.equals("Venti")) {
            price = VENTI_PRICE;
        }

        // add ins
        price += ADDINS_PRICE * (double) addIns.size();
        price *= quantity;
    }

    /**
     * Returns the price of the coffee
     * @return price of coffee
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the string representation of the coffee
     * @return string representation of the coffee
     */
    @Override
    public String toString() {
        String totalAddIns = "";
        for (int i = 0; i < addIns.size(); i++) {
            if (i == addIns.size() - 1) {
                totalAddIns += addIns.get(i);
            }
            else {
                totalAddIns += addIns.get(i) + ", ";
            }
        }
        return (size + " coffee (" + quantity + "), with " + totalAddIns);
    }
}
