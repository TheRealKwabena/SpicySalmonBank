package com.salmon.spicysalmon;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class UserIO {

    public static String EOL = System.lineSeparator();
    private static final Scanner input = new Scanner(System.in);

    public static String decimalFormat(double price){
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(price);
    }

    public static double truncateFormat(double mean) {
        return (int)(mean*10)/10.0;
    }

    public static double truncateFormat2(double price){
        return (int)(price*100)/100.0;
    }

    public static final Scanner SCANNER = new Scanner(System.in);

    public static int readInt(){
        int input = SCANNER.nextInt();
        SCANNER.nextLine();
        return input;
    }

    public static String readStr(){
        String str = SCANNER.next();
        return str;
    }

    public static double readDouble(){
        return SCANNER.nextDouble();
    }

    public static String getDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String readStr(String s) {
        return s;
    }
}
