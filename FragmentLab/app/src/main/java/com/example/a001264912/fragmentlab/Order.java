package com.example.a001264912.fragmentlab;

/**
 * Created by 001264912 on 9/7/2017.
 */

public class Order {
    // Properties
    private String firstName;
    private String lastName;
    private String chocolateType;
    private int chocolateQuantity;
    private boolean expeditedShipping;

    // Constructors
    public Order() {}

    public Order(String firstName, String lastName, String chocolateType, int chocolateQuantity,
                 boolean expeditedShipping) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.chocolateType = chocolateType;
        this.chocolateQuantity = chocolateQuantity;
        this.expeditedShipping = expeditedShipping;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getChocolateType() {
        return chocolateType;
    }

    public void setChocolateType(String chocolateType) {
        this.chocolateType = chocolateType;
    }

    public int getChocolateQuantity() {
        return chocolateQuantity;
    }

    public void setChocolateQuantity(int chocolateQuantity) {
        this.chocolateQuantity = chocolateQuantity;
    }

    public boolean isExpeditedShipping() {
        return expeditedShipping;
    }

    public void setExpeditedShipping(boolean expeditedShipping) {
        this.expeditedShipping = expeditedShipping;
    }

}