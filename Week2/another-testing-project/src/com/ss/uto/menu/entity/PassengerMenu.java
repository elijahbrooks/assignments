package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.Booking;
import com.ss.uto.entity.Passenger;
import com.ss.uto.menu.BaseMenu;
import com.ss.uto.service.PassengerService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.function.Function;

public class PassengerMenu extends Helper implements BaseMenu {
    PassengerService passengerService = new PassengerService();
    @Override
    public Object add() {
        return null;
    }

    @Override
    public Object update() {
        return null;
    }

    @Override
    public Object delete() {
        return null;
    }

    @Override
    public Object read() {
        return null;
    }

    public Passenger proceedAsGuest(Connection conn, Booking booking) throws SQLException, ClassNotFoundException {
        String firstName = new Menu(new String[]{"Input First Name: "}).inputMenu();
        String lastName = new Menu(new String[]{"Input Last Name: "}).inputMenu();
        String gender = new Menu(new String[]{"Input Gender: "}).inputMenu();
        String address = new Menu(new String[]{"Input Address: "}).inputMenu();
        Date dob = getDateOfBirth();

        Passenger passenger = new Passenger(booking, firstName, lastName, dob, gender, address);

        return passengerService.addPassenger(conn, passenger);
    }

    public Date getDateOfBirth(){
        Date dob;
        try{
            dob = Date.valueOf(new Menu(new String[]{"Input Date Of Birth(yyyy-mm-dd): "}).inputMenu());
        }catch (Exception e){
            System.out.println("Please input valid format.");
            return getDateOfBirth();
        }
        return dob;
    }

    public String[] getEmailAndPhone(){
        String email = new Menu(new String[]{"Input Email: "}).inputMenu();
        String phone = new Menu(new String[]{"Input Phone Number: "}).inputMenu();

        return new String[]{email, phone};
    }

    public void updatePassenger(Booking booking){
        Passenger passenger = passengerService.getPassenger(booking);
        if(passenger != null)
            updateMenu(passenger);
        else
            System.out.println("Can't find Passenger with that confirmation number.");
    }

    private void updateMenu(Passenger passenger){
        String[] options = {"First Name", "Last Name", "Gender", "Address"};
        Integer input = new Menu(options).getInput();

        if(input != null){
            Function<Object, Object> firstName = value -> {
                passenger.setGiven_name(new Menu(new String[]{"New First Name: "}).inputMenu());
                return null;
            };
            Function<Object, Object> lastName = value -> {
                passenger.setFamily_name(new Menu(new String[]{"New Last Name: "}).inputMenu());
                return null;
            };
            Function<Object, Object> gender = value -> {
                passenger.setGender(new Menu(new String[]{"New Gender: "}).inputMenu());
                return null;
            };
            Function<Object, Object> address = value -> {
                passenger.setAddress(new Menu(new String[]{"New Address: "}).inputMenu());
                return null;
            };

            Function[] methods = {firstName, lastName, gender, address};
            methods[input].apply(new Object());

            passengerService.updatePassenger(passenger);
        }
    }
}
