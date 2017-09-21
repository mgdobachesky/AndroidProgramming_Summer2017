package com.example.a001264912.finalexam;

/**
 * Created by 001264912 on 9/21/2017.
 */

public class BankAccount {
    public int _id;
    public String operation;
    public float amount;

    public BankAccount() {

    }

    public BankAccount(int _id, String operation, float amount) {
        this._id = _id;
        this.operation = operation;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return(operation + " - " + String.format("%.2f", amount));
    }
}
