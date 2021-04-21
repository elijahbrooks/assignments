package com.ss.uto.entity;

import com.ss.uto.dao.BookingDAO;
import com.ss.uto.dao.FlightDAO;
import com.ss.uto.service.ConnectionUtil;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightBooking {
    private Flight flight;
    private Booking booking;

    public FlightBooking(Flight flight, Booking booking) {
        this.flight = flight;
        this.booking = booking;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public static FlightBooking toObject(ResultSet rs) throws SQLException, ClassNotFoundException {


        Integer flightId = rs.getInt("flight_id");
        Integer bookingId = rs.getInt("booking_id");

        Flight flight = new Flight(flightId);
        Booking booking = new Booking(bookingId);

        return new FlightBooking(flight, booking);
    }

    @Override
    public String toString() {
        return "FlightBookings{" +
                "flight=" + flight +
                ", booking=" + booking +
                '}';
    }
}
