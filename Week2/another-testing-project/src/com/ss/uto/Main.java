package com.ss.uto;


import com.ss.uto.menu.AdminMenu;
import com.ss.uto.menu.EmployeeMenu;
import com.ss.uto.menu.TravelerMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Welcome to the Utopia Airlines Management System. Which " +
                "category of a user are you.");
        Menu newMenu = new Menu(new String[]{"Employee", "Administrator", "Traveler"});
        String role = newMenu.getInputAsString();
        if(role != null)
            switch(role) {
                case "Employee":
                    new EmployeeMenu().startMenu();
                    break;
                case "Administrator":
                    new AdminMenu().startMenu();
                    break;
                case "Traveler":
                    new TravelerMenu().startMenu();
                    break;
                default:
                    System.out.println("This shouldn't print.");
            }

        main(null);

    }
}
