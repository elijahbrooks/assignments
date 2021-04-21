package com.ss.uto.service.admin;

import com.ss.uto.entity.Booking;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.Passenger;
import com.ss.uto.entity.User;
import com.ss.uto.menu.entity.BookingMenu;
import com.ss.uto.menu.entity.FlightMenu;
import com.ss.uto.menu.entity.PassengerMenu;
import com.ss.uto.menu.entity.UserBaseMenu;
import com.ss.uto.service.BookingService;
import com.ss.uto.service.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminBookings {
    private ConnectionUtil connUtil = new ConnectionUtil();
    private BookingService bookingService = new BookingService();
    private UserBaseMenu userBaseMenu = new UserBaseMenu();
    private FlightMenu flightMenu = new FlightMenu();
    private PassengerMenu passengerMenu = new PassengerMenu();

    public void addBooking(User user, Flight flight){
        Connection conn = null;
        BookingMenu bookingMenu = new BookingMenu();
        try{
            conn = connUtil.getConnection();
            Booking booking = bookingService.addBooking(conn);

            if(user == null)
                makeBooking(conn, booking, userBaseMenu.chooseUserRole(), null);
            else
                makeBooking(conn, booking, user, flight);

            conn.commit();
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                e.printStackTrace();
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addBooking(user, flight);
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void makeBooking(Connection conn, Booking booking, User user, Flight flight) throws SQLException, ClassNotFoundException {
        BookingMenu bookingMenu = new BookingMenu();
        if (user != null) {
            String userRole = user.getUserRole().getName();
            if ("Traveler".equals(userRole)) {
                bookingService.addBookingUser(conn, user, booking);
            } else {
                bookingService.addBookingAgent(conn, user, booking);
            }
        } else {
            Passenger passenger = passengerMenu.proceedAsGuest(conn, booking);
            bookingService.addBookingGuest(conn, passenger);
        }

        if (flight == null) {
            flight = flightMenu.chooseFlight(false);
        }

        bookingService.addFlightBooking(conn, flight, booking);
        bookingMenu.processPayment(conn, booking, flight);
    }
}
