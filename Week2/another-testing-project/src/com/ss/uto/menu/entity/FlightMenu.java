package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.Airplane;
import com.ss.uto.entity.Airport;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.Route;
import com.ss.uto.menu.BaseMenu;
import com.ss.uto.service.AirplaneService;
import com.ss.uto.service.FlightService;
import com.ss.uto.service.RouteService;
import com.ss.uto.service.admin.AdminFlights;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Random;
import java.util.function.Function;

public class FlightMenu extends Helper implements BaseMenu {
    @Override
    public Flight add() {
        AdminFlights adminFlights = new AdminFlights();
        Flight flight = adminFlights.addFlight();

        if(flight!= null) {
            System.out.println("Flight created successfully!");
            return flight;
        }
        else {
            System.out.println("Tasked failed. Please try again.");
            return add();
        }
    }

    @Override
    public Integer update() {
        AdminFlights adminFlights = new AdminFlights();
        System.out.println("Choose a flight to update:");

        Flight flight = updateMenu(chooseFlight(false));
        adminFlights.updateFlight(flight);

        System.out.println("Flight successfully updated.");

        return null;
    }

    @Override
    public Integer delete() {
        AdminFlights adminFlights = new AdminFlights();
        System.out.println("Choose a flight to delete:");

        adminFlights.deleteFlight(chooseFlight(false));

        System.out.println("Flight successfully deleted.");
        return null;
    }

    @Override
    public Integer read() {
        FlightService flightService = new FlightService();
        String[] flights = flightService.listAllFlights();

        System.out.println("Press enter to continue");
        new Menu(flights).inputMenu();

        return null;
    }

    public Timestamp chooseDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        Timestamp[] availableTimes = new Timestamp[10];
        String[] timeOptions = new String[availableTimes.length];

        for (int i = 0; i < availableTimes.length ; i++) {
            availableTimes[i] = getRandomDateTime();
            timeOptions[i] = availableTimes[i].toLocalDateTime().format(formatter);
        }

        System.out.println("Choose an available time.");
        Menu menu = new Menu(timeOptions);
        Integer choice = menu.getInput();

        if(choice != null)
            return availableTimes[choice];
        else
            return chooseDateTime();

    }

    private Timestamp getRandomDateTime(){
        Random rand = new Random();
        String Month = String.valueOf((int)Math.floor(Math.random()*(10)+1));
        String Day = String.valueOf((int)Math.floor(Math.random()*(26)+1));
        String Hour = String.valueOf(rand.nextInt(24));
        String Minute = String.valueOf(rand.nextInt(59));

        return Timestamp.valueOf("2021-"+Month+"-"+Day+" " + Hour+":"+Minute+":"+"00");
    }

    public Float chooseSeatPrice(){
        Menu menu = new Menu(new String[]{"Choose a seat price"});
        String choice = menu.inputMenu();

        try{
            Float price = Float.valueOf(choice);
            return price;
        }catch(Exception e){
            e.printStackTrace();
            return chooseSeatPrice();
        }
    }

    public Integer chooseReservedSeats(Integer max){
        Menu menu = new Menu(new String[]{"Input Reserved Seats(MAX: "+max+"): "});
        String choice = menu.inputMenu();

        try{
            Integer num = Integer.valueOf(choice);
            if(num <= max)
                return num;
            else{
                System.out.println("Not enough seats on plane.");
                return chooseReservedSeats(max);
            }
        }catch(Exception e){
            e.printStackTrace();
            return chooseReservedSeats(max);
        }
    }

    public Flight chooseFlight(boolean returnToMenu){
        FlightService flightService = new FlightService();
        String[] flights;
        if(returnToMenu)
            flights = pushToArray("Quit to previous", flightService.listAllFlightsEmployee());
        else
            flights = flightService.listAllFlights();


        Menu menu = new Menu(flights);
        String input = menu.getInputAsString().split(" ")[0];
        if (!"Quit".equals(input)){
            Integer choice = Integer.parseInt(input);
            if (choice != null) {
                return flightService.getFlight(choice);
            } else {
                return chooseFlight(returnToMenu);
            }
        }

        return null;
    }

    public Flight updateMenu(Flight flight){
        System.out.println("What would you like to update?: ");
        String[] options = {"Departure Time", "Reserved Seats", "Seat Price"};
        Integer choice = new Menu(options).getInput();

        if(choice != null){
            Function<Object, Object> departTime = (value) -> { flight.setDepartTime(chooseDateTime()); return null;};
            Function<Object, Object> seatPrice = (value) -> { flight.setSeatPrice(chooseSeatPrice()); return null;};
            Function<Object, Object> reservedSeats = (value) -> {
                flight.setReservedSeats(chooseReservedSeats(flight.getAirplane().getAirplaneType().getMaxCapacity()));
                return null;
            };

            Function[] functions = {departTime, reservedSeats, seatPrice};
            functions[choice].apply(new Object());

        }else
            return updateMenu(flight);

        return flight;
    }

}
