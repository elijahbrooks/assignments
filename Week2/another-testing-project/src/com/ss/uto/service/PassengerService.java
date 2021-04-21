package com.ss.uto.service;

import com.ss.uto.dao.BookingDAO;
import com.ss.uto.dao.PassengerDAO;
import com.ss.uto.entity.Booking;
import com.ss.uto.entity.Passenger;

import java.sql.Connection;
import java.sql.SQLException;

public class PassengerService {
    ConnectionUtil connUtil = new ConnectionUtil();

    public Passenger addPassenger(Connection conn, Passenger passenger) throws SQLException, ClassNotFoundException {
        PassengerDAO passengerDAO = new PassengerDAO(conn);
        passengerDAO.addPassenger(passenger);

        return passengerDAO.getPassengerByBooking(passenger.getBooking());
    }

    public Passenger getPassenger(Booking booking){
        Connection conn = null;
        Passenger passenger = null;
        try {
            conn = connUtil.getConnection();
            PassengerDAO passengerDAO = new PassengerDAO(conn);
            passenger = passengerDAO.getPassengerByBooking(booking);

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return passenger;
    }

    public void updatePassenger(Passenger passenger){
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            PassengerDAO passengerDAO = new PassengerDAO(conn);
            passengerDAO.updatePassenger(passenger);

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
