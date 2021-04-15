package com.example.rucafe.projectfiles;

/**
 * This class represents a Donut item.
 * It extends the MenuItem class, and also includes fields for the type of donut, flavor and quantity.
 * @author Srija Gottiparthi, Catherine Nguyen
 */

public class Donut extends MenuItem {
    private String type; // yeast, cake, or donut hole
    private String flavor; // the flavor
    private int quantity;
    private double price;

    private final static double YEAST_PRICE = 1.39;
    private final static double CAKE_PRICE = 1.59;
    private final static double HOLE_PRICE = 0.33;

    /**
     * Initializes a new Donut object with with a given type, flavor and quantity.
     * @param type type of donut
     * @param flavor donut flavor
     * @param quantity amount of donuts
     */
    public Donut(String type, String flavor, int quantity) {
        super();
        this.type = type;
        this.flavor = flavor;
        this.quantity = quantity;
    }

    /**
     * Calculates the price of a donut.
     */
    public void itemPrice() {
        if (type.equals("Yeast Donut")) {
            price = YEAST_PRICE;
        }
        else if (type.equals("Cake Donut")) {
            price = CAKE_PRICE;
        }
        else {
            price = HOLE_PRICE;
        }

        price *= quantity;
    }

    /**
     * Returns the price of the donut.
     * @return donut price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the donut
     * @return string representation of the donut
     */
    @Override
    public String toString() {
        return (type + "(" + quantity + "), " + flavor);
    }
}
