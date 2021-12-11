package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.menus.CustomerMenu;
import com.salmon.spicysalmon.models.Customer;

public class AuthenticationController {

    private String[] getLoginInfo(){
        System.out.println(UserIO.EOL + "Login: Please fill in your details!");
        String SSN = UserIO.readStr("Social Security Number: ");
        String password = UserIO.readStr("Password: ");
        return new String[]{SSN, password};
    }

    public void customerLogin(){
        CustomerController customerController = new CustomerController();
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        Customer loggedInCustomer = customerController.findCustomer(SSN);
        if (loggedInCustomer != null && loggedInCustomer.verifyPassword(password)) {
            CustomerMenu customerMenu = new CustomerMenu();
            customerMenu.show();
        } else {
            System.out.println("Username or password incorrect.");
        }
    }

    public void employeeLogin(){
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        EmployeeController employeeController = new EmployeeController();
//        Employee loggedInEmployee = employeeController.getEmployee(SSN);
//        if (loggedInEmployee != null && loggedInEmployee.verifyPassword(password)){
//            showEmployeeMenu();
//        }else {
//            System.out.println("Username or password incorrect");
//        }
    }
}
