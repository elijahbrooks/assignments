package com.ss.uto.menu;

import com.ss.uto.Menu;
import com.ss.uto.entity.BookingUser;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.User;
import com.ss.uto.menu.entity.FlightMenu;
import com.ss.uto.service.BookingService;
import com.ss.uto.service.UserService;
import com.ss.uto.service.admin.AdminBookings;

public class TravelerMenu {
    private User user;
    private FlightMenu flightMenu = new FlightMenu();

    public void startMenu(){
        System.out.println("Enter your login credentials");
        String username = new Menu(new String[]{"Enter your username: "}).inputMenu().trim();
        String password = new Menu(new String[]{"Enter your password: "}).inputMenu().trim();

        user = new UserService().loginUser(username, password);

        if(user != null){
            travellerMenu();
        } else{
            System.out.println("Invalid credentials.");
        }
    }


    private void travellerMenu(){
        Menu menu = new Menu(new String[]{"Book a ticket", "Cancel an Upcoming trip", "Quit to Previous"});
        Integer choice = menu.getInput();

        if (choice != null){
            if(choice == 0)
                bookTicket();
            else if(choice == 1)
                cancelTrip();
        }
    }

    private void bookTicket(){
        System.out.println("Pick a Flight to book a ticket for.");
        Flight flight = flightMenu.chooseFlight(true);
        if(flight != null) {
            new AdminBookings().addBooking(user, flight);
        }
        travellerMenu();
    }

    private void cancelTrip(){
        BookingUser bookingUser = new BookingService().getBookingUserFromUser(user);

        if(bookingUser != null){
            new BookingService().cancelTrip(bookingUser.getBooking(), true);
            System.out.println("Trip successfully cancelled.");
        }else{
            System.out.println("You don't have a trip planned. ");
        }

        travellerMenu();
    }

}
