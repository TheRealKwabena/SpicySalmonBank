package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.UserIO;

public class Menu {
    private final String MENU_HEADING;
    private final String[] MENU_OPTIONS;

    public Menu(String heading, String[] options){
        this.MENU_HEADING = heading;
        this.MENU_OPTIONS = options;
    }

    public String toString(){
        String result = UserIO.EOL + MENU_HEADING + UserIO.EOL;
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            result += i + ". " + MENU_OPTIONS[i] + UserIO.EOL;
        }
        return result;
    }

    public int getValidOption(){
        int userInput;
        do{
            System.out.println("Enter you option number: ");
            userInput = UserIO.readInt();
            if(userInput < 0 || userInput >= MENU_OPTIONS.length){
                System.out.println("Invalid menu option. Please type another option.");
            }
        } while(userInput < 0 || userInput >= MENU_OPTIONS.length);

        return userInput;
    }
}
