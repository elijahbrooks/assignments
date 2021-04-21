package com.ss.uto.menu;

import com.ss.uto.Menu;
import com.ss.uto.entity.Booking;
import com.ss.uto.menu.entity.AirplaneMenu;
import com.ss.uto.menu.entity.AirportMenu;
import com.ss.uto.menu.entity.BookingMenu;
import com.ss.uto.menu.entity.EmployeeMenu;
import com.ss.uto.menu.entity.FlightMenu;
import com.ss.uto.menu.entity.TravelerMenu;
import com.ss.uto.service.BookingService;

import java.util.function.Function;

public class AdminMenu {
    public void startMenu(){
        String[] options = {
          "Add/Update/Delete/Read Flights",
          "Add/Update/Delete/Read Seats",
          "Add/Update/Delete/Read Tickets and Passengers",
          "Add/Update/Delete/Read Airports",
          "Add/Update/Delete/Read Travelers",
          "Add/Update/Delete/Read Employees",
          "Over-ride Trip Cancellation for a ticket."
        };
        CrudMenu crudMenu = new CrudMenu();
        Menu menu = new Menu(options);
        Integer choice = menu.getInput();

        if(choice != null){
            Function<Object, Integer> crudFlight = (a) -> crudMenu.displayMenu(new FlightMenu());
            Function<Object, Integer> crudSeat = (a) -> crudMenu.displayMenu(new AirplaneMenu());
            Function<Object, Integer> crudBooking = (a) -> crudMenu.displayMenu(new BookingMenu());
            Function<Object, Integer> crudAirport = (a) -> crudMenu.displayMenu(new AirportMenu());
            Function<Object, Integer> crudTraveler = (a) -> crudMenu.displayMenu(new TravelerMenu());
            Function<Object, Integer> crudEmployee = (a) -> crudMenu.displayMenu(new EmployeeMenu());

            Function[] functions = {crudFlight, crudSeat, crudBooking, crudAirport, crudTraveler, crudEmployee};
            if(choice == 6){
                overrideCancel();
            }else
                functions[choice].apply(new Object());
        }else
            startMenu();

        startMenu();
    }

    private void overrideCancel(){
        Booking booking = new BookingMenu().chooseBooking();
        new BookingService().cancelTrip(booking, false);

        System.out.println("Ticket cancellation overrided.");
    }

}
