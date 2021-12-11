package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;
import com.salmon.spicysalmon.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class TransactionController {

    private static final LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> transactionList = new LinkedHashMap<>();

    public void createTransaction(String acc1, String acc2, double amount){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount);
        Transaction transaction2 = new Transaction(acc2, acc1, amount);
        String SSN1 = acc1.substring(0,9);
        String accID1 = acc1.substring(10,11);
        String SSN2 = acc2.substring(0,9);
        String accID2 = acc2.substring(10,11);
        transactionList.get(SSN1).get(accID1).add(transaction1);
        transactionList.get(SSN2).get(accID2).add(transaction2);
    }

    public boolean checkIfSSNUnique(String SSN) { // Armin: use verb when naming methods
        return transactionList.get(SSN) == null;
    }

    public String ascendingTransactionsByPriceForAccount (String SSN, String accID){
        CustomerController customerController = new CustomerController();
        Customer desiredCustomer = customerController.findCustomer(SSN);
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
        String sortedTransactions = "";
        for(Transaction transaction : sortedList){
            sortedTransactions += transaction;
        }
        return sortedTransactions;
    }

    public ArrayList<Transaction> sortTransactionsByPriceInAcc (String SSN, String accID){
        ArrayList<Transaction> sortedList = transactionList.get(SSN).get(accID);
        Collections.sort(sortedList);
        return sortedList;
    }

    public String descendingTransactionsByPriceForAccount (String SSN, String accID) {
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
        String sortedTransactions = "";
        for(int i = sortedList.size(); i > 0; i--){
            sortedTransactions += sortedList.get(i) + UserIO.EOL;
        }
        return sortedTransactions;
    }

    public String printTransactionsForAnAccount(String SSN, String accID){
        CustomerController customerController = new CustomerController();
        String transactionForAnAccount="";
        Customer customer = customerController.findCustomer(SSN);
        if(customer!=null){
            for(Transaction transaction : transactionList.get(SSN).get(accID)){
                transactionForAnAccount += transaction + UserIO.EOL;
            }
        }
        return transactionForAnAccount;
    }

    public String printTransactionsForAllAccounts(String SSN){
        CustomerController customerController = new CustomerController();
        String transactionsForAllAccounts = "";
        Customer customer = customerController.findCustomer(SSN);
        if(customer!=null){
            for(String accountKey : transactionList.get(SSN).keySet()){
                for(Transaction transaction : transactionList.get(SSN).get(accountKey)){
                    transactionsForAllAccounts += transaction + UserIO.EOL;
                }
            }
        }
        return transactionsForAllAccounts;
    }

    public ArrayList<Transaction> sortByDateEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedList = transactionList.get(SSN).get(accID);
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }

    public String printTransactionsSortedEarliest(String SSN, String accID){
        String transactionsList = "";
        for(Transaction transaction : sortByDateEarliest(SSN, accID)){
            transactionsList += transaction + UserIO.EOL;
        }

        return transactionsList;
    }

    public String printTransactionsSortedLatest(String SSN, String accID){
        ArrayList<Transaction> sortedList = sortByDateEarliest(SSN, accID);
        String transactionsList = "";
        for(int i=sortedList.size()-1; i>=0; i--){
            transactionsList += sortedList.get(i) + UserIO.EOL;
        }
        return transactionsList;
    }
}
