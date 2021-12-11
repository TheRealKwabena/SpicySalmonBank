package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.UserIO;

import java.util.UUID;

public class Transaction implements Comparable<Transaction>{
    public final String ID;
    public final String TO;
    public final String FROM;
    public final double AMOUNT;
    public final String DATE;
    
    public Transaction(String to, String from, double amount){
        this.ID = UUID.randomUUID().toString().replace("-", "");
        this.TO = to;
        this.FROM = from;
        this.AMOUNT = amount;
        this.DATE = UserIO.getDateAndTime();
    }
    public String getID() {
        return ID;
    }

    public String getTO() {
        return TO;
    }

    public String getFROM() {
        return FROM;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }

    public String getDATE() {
        return DATE;
    }

    @Override
    public int compareTo(Transaction o) {
        return Double.compare(this.AMOUNT, o.AMOUNT);
    }
}
