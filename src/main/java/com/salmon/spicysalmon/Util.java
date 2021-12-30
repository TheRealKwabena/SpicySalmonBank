package com.salmon.spicysalmon;

import java.io.Console;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Util {
    public static String EOL = System.lineSeparator();
    private static final Scanner input = new Scanner(System.in);

    public static String readLine(String message){
        System.out.print(message);
        return input.nextLine();
    }

    public static int readInt(String message){
        int input = -1;
        do{
            try{
                String potentialInteger = readLine(message);
                input = Integer.parseInt(potentialInteger);
            } catch(Exception e){
                System.out.println("Please enter a valid integer.");
            }
        }while(input == -1);
        return input;
    }

    public static double readDouble(String message){
        double input = -1.0;
        do{
            try{
                String potentialDouble = readLine(message);
                input = Double.parseDouble(potentialDouble);
            } catch(Exception e){
                System.out.println("Please enter a valid integer.");
            }
        }while(input == -1.0);
        return input;
    }

    public static String getDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String readSSN(String message){
        String SSN;
        do{
            SSN = readLine(message);
            if(!SSN.matches("^\\d{10}$")){
                System.out.println("Please enter a valid Social Security Number, use format YYMMDDXXXX");
            }
        } while(!SSN.matches("^\\d{10}$"));
        return SSN;
    }

    public static String readNewPassword(){
        String password = "";
        String verifiedPassword = "";
        do {
            System.out.println("Create a new password, it has to have:"
                    + Util.EOL + "Both upper-case and lower-case letters"
                    + Util.EOL + "One number"
                    + Util.EOL + "Longer than 8 characters" + Util.EOL);
            password = readPassword("Enter your password: ");
            verifiedPassword = readPassword("Confirm your password: ");

            System.out.println();
            if(password.equals(password.toLowerCase())) System.out.println("Your password did not have a uppercase Character");
            if(password.equals(password.toUpperCase())) System.out.println("Your password did not have a lowercase Character");
            if(!password.matches(".*[0-9].*")) System.out.println("Your password did not have a number");
            if(password.length() < 8) System.out.println("Your password was not longer than 8 characters");
            if(!password.equals(verifiedPassword)) System.out.println("The passwords do not match, please try again!");
            System.out.println();
        } while (!(password.length() > 8
                && !password.equals(password.toLowerCase())
                && !password.equals(password.toUpperCase())
                && password.matches(".*[0-9].*")
                && password.equals(verifiedPassword)));
        return password;
    }

    public static void waitToContinue(){
        readLine("Press enter to continue: ");
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {
            System.out.print("");
        }
    }

    public static String readPassword(String message){
        System.out.print(message);
        try{
            Console console = System.console();
            char[] passwordChars = console.readPassword();
            String thePassword = String.valueOf(passwordChars);
            return thePassword;
        } catch(Exception e){
            String input = readLine(message);
            return input;
        }
    }
}
