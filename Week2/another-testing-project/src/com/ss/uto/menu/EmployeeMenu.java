package com.ss.uto.menu;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.Airplane;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.Route;
import com.ss.uto.menu.entity.FlightMenu;
import com.ss.uto.service.AirplaneService;
import com.ss.uto.service.FlightService;
import com.ss.uto.service.RouteService;
import com.ss.uto.service.admin.AdminFlights;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class EmployeeMenu extends Helper {
    FlightMenu flightMenu = new FlightMenu();

    public void startMenu(){
        Menu menu = new Menu(new String[]{"Enter Flights You Manage", "Quit to previous"});
        Integer choice = menu.getInput();

        if (choice != null){
            if(choice == 0)
                showFlights();
        }else
            startMenu();
    }

    private void showFlights(){
        Flight flight = flightMenu.chooseFlight(true);
        if(flight == null)
            startMenu();
        else {
            Airplane airplane = new AirplaneService().getAirplane(flight.getAirplane().getId());

            Route route = new RouteService().getRoute(flight.getRoute().getId());
            flight.setRoute(route);
            flight.setAirplane(airplane);

            new AdminFlights().updateFlight(flight);

            flightDetails(flight);
        }

    }

    private void flightDetails(Flight flight){
        Menu menu = new Menu(new String[]{"View more details about the flight",
                "Update the details about the flight", "Quit to previous"});
        Integer choice = menu.getInput();

        if (choice != null){
            if(choice == 0)
                showFlightDetails(flight);
            else if(choice == 1)
                updateFlight(flight);
            else
                showFlights();
        }else
            startMenu();
    }

    private void showFlightDetails(Flight flight){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        String originCode = flight.getRoute().getOriAirport().getAirportCode();
        String originCity = flight.getRoute().getOriAirport().getCityName();
        String destinationCode = flight.getRoute().getDesAirport().getAirportCode();
        String destinationCity = flight.getRoute().getDesAirport().getCityName();

        System.out.println("You have chosen to view the Flight with Flight Id: " +
                flight.getId() + " and Departure Airport: " + originCode + ", " + originCity +
                " and Arrival Airport: " + destinationCode + ", " + destinationCity);

        System.out.println("Departure Airport: " + originCode + ", " + originCity + " | " +
                "Arrival Airport: " + destinationCode + ", " + destinationCity);

        System.out.println("Departure Time: " + flight.getDepartTime().toLocalDateTime().format(formatter));
        System.out.println("Arrival Time: " + flight.getDepartTime().toLocalDateTime().format(formatter));

        System.out.println("Reserved Seats: " + (flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats()));

        Menu menu = new Menu(new String[]{"Quit to previous"});
        Integer choice = menu.getInput();

        if(choice != null)
            flightDetails(flight);
        else
            showFlightDetails(flight);
    }

    private void updateFlight(Flight flight){
        flight = flightMenu.updateMenu(flight);
        new AdminFlights().updateFlight(flight);

        flightDetails(flight);
    }

}
