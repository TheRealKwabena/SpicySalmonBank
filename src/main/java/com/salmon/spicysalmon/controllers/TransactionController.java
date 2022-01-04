package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionController {

    private static final LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> allTransactions = new LinkedHashMap<>();

    // Creates a pair of transactions and calls the add method
    public void createTransaction(String acc1, String acc2, double amount){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount);
        Transaction transaction2 = new Transaction(acc2, acc1, amount);
        addTransactions(acc1, acc2, transaction1, transaction2);
    }

    // Only called from the JSON controller for demonstration purposes.
    // The users of the application will not have the ability to back date transactions.
    public void createTransaction(String acc1, String acc2, double amount, String date){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount, date);
        Transaction transaction2 = new Transaction(acc2, acc1, amount, date);
        addTransactions(acc1, acc2, transaction1, transaction2);
    }

    // Adds two transactions to their respective accounts
    public void addTransactions(String acc1, String acc2, Transaction transaction1, Transaction transaction2){
        // Extracting SSN and accountID from accountNumber
        String SSN1 = acc1.substring(0,10);
        String accID1 = acc1.substring(10,12);
        String SSN2 = acc2.substring(0,10);
        String accID2 = acc2.substring(10,12);
        try{
            // No exceptions will be thrown if the accounts linked hashmap exists
            allTransactions.get(SSN1).get(accID1).add(transaction1);
            allTransactions.get(SSN2).get(accID2).add(transaction2);
        } catch(NullPointerException e){
            // initializes the transactions array with the appropriate collections and adds the transactions
            ArrayList<Transaction> arr1 = new ArrayList<>();
            arr1.add(transaction1);
            ArrayList<Transaction> arr2 = new ArrayList<>();
            arr2.add(transaction2);
            LinkedHashMap<String, ArrayList<Transaction>> lm1 = new LinkedHashMap<>();
            lm1.put(accID1, arr1);
            LinkedHashMap<String, ArrayList<Transaction>> lm2 = new LinkedHashMap<>();
            lm2.put(accID2, arr2);
            allTransactions.put(SSN1, lm1);
            allTransactions.put(SSN2, lm2);
            // allTransactions.get(SSN2).put(accID2, arr2);
        }
    }

    public boolean checkIfSSNUnique(String SSN) { // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }

    public String sortTransactionsAscending (String SSN, String accID){
        try {
            ArrayList<Transaction> sortedList = sortTransactionsByAmount(SSN, accID);
            String sortedTransactions = "";
            for (Transaction transaction : sortedList) {
                sortedTransactions += transaction;
            }
            return sortedTransactions;
        } catch (Exception customerNotFound) {
            return customerNotFound.getMessage();
        }
    }

    // Sort transactions by amount
    public ArrayList<Transaction> sortTransactionsByAmount (String SSN, String accID){
        ArrayList<Transaction> sortedList = allTransactions.get(SSN).get(accID);
        Collections.sort(sortedList);
        return sortedList;
    }

    // Sort transactions by descending amount
    public String sortTransactionsDescending (String SSN, String accID) {
        ArrayList<Transaction> sortedList = sortTransactionsByAmount(SSN, accID);
        String sortedTransactions = "";
        for(int i = sortedList.size(); i > 0; i--){
            sortedTransactions += sortedList.get(i) + Util.EOL;
        }
        return sortedTransactions;
    }

    public String printTransactionsForAnAccount(String SSN, String accID){
        try {
            String transactionForAnAccount = "";
            for(Transaction transaction : allTransactions.get(SSN).get(accID)){
                transactionForAnAccount += transaction + Util.EOL;
            }
            return transactionForAnAccount;
        } catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    // Print transactions for all accounts
    public String printTransactionsForAllAccounts(String SSN){
        try {
            System.out.println("kladdkaka 32");
            String transactionsForAllAccounts = "====================================================" + Util.EOL;
            for(String accountKey : allTransactions.get(SSN).keySet()){
                for(Transaction transaction : allTransactions.get(SSN).get(accountKey)){
                    transactionsForAllAccounts += transaction + Util.EOL +
                            "====================================================" + Util.EOL;
                }
            }
            return transactionsForAllAccounts;
        }
        catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    public String printAllTransactions(){
        String result = "All Registered Transactions"+Util.EOL;
        result += "-----------------------------"+Util.EOL;
        if(!allTransactions.isEmpty()){
            for(Transaction transaction: sortTransactionsDateEarliest()){
                result += transaction + Util.EOL;
            }
        } else{
            result += "No transactions registered yet."+Util.EOL;
        }
        return result;
    }

    public ArrayList<Transaction> sortTransactionsDateEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedList = new ArrayList<>(allTransactions.get(SSN).get(accID));
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }

    public ArrayList<Transaction> sortTransactionsDateEarliest(){
        ArrayList<Transaction> sortedList = new ArrayList<>();
        for(String SSN : allTransactions.keySet()){
            for(String accID: allTransactions.get(SSN).keySet()){
                sortedList.addAll(allTransactions.get(SSN).get(accID));
            }
        }
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }

    public String printTransactionsSortedEarliest(String SSN, String accID){
        String transactionsList = "";
        for(Transaction transaction : sortTransactionsDateEarliest(SSN, accID)){
            transactionsList += transaction + Util.EOL;
        }

        return transactionsList;
    }

    public String printTransactionsSortedLatest(String SSN, String accID){
        ArrayList<Transaction> sortedList = sortTransactionsDateEarliest(SSN, accID);
        String transactionsList = "";
        for(int i=sortedList.size()-1; i>=0; i--){
            transactionsList += sortedList.get(i) + Util.EOL;
        }
        return transactionsList;
    }

    public String sortByDateInterval (String SSN, String accID, String startInterval, String endInterval){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        String limitedTransactionList = "";
        ArrayList<Transaction> desiredAccount = allTransactions.get(SSN).get(accID);

        try {
            Date lowerBoundDate = formatter.parse(startInterval);
            Date upperBoundDate = formatter.parse(endInterval);
            Date currentDay = calendar.getTime();

            //If the customer sets an upperbound of ex. 2034, the method sets it back to the current date
            if(upperBoundDate.after(currentDay)){
                upperBoundDate = currentDay;
            }

            //filtering Transactions in given bounds happens here
            for (Transaction transaction : desiredAccount) {
                Date toBeCompared = formatter.parse(transaction.getDATE());
                if (toBeCompared.after(lowerBoundDate) && toBeCompared.before(upperBoundDate)) {
                    limitedTransactionList += transaction + System.lineSeparator();
                }
            }
        }

        catch (ParseException p){
            return "Please enter the date in the form YYYY/MM/DD";
        }

        return limitedTransactionList;
    }
}
