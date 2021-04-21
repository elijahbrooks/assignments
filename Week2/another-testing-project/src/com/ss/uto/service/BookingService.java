package com.ss.uto.service;

import com.ss.uto.dao.*;
import com.ss.uto.entity.*;
import com.ss.uto.menu.entity.BookingMenu;
import com.ss.uto.menu.entity.PassengerMenu;

import java.sql.Connection;
import java.sql.SQLException;

public class BookingService {
    ConnectionUtil connectionUtil = new ConnectionUtil();

    public Booking addBooking(Connection conn) throws SQLException, ClassNotFoundException {
        BookingDAO bookingDAO = new BookingDAO(conn);
        String amountOfBookings = String.valueOf(bookingDAO.getAllBookings().size() + 1);
        Booking booking = new Booking(1, "CONFIRMATION-" + amountOfBookings);

        Integer key = bookingDAO.addBooking(booking);

        return bookingDAO.getBookingById(key);
    }

    public void addBookingUser(Connection conn, User user, Booking booking) throws SQLException, ClassNotFoundException {
        BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
        BookingUser bookingUser = new BookingUser(user, booking);

        bookingUserDAO.addBookingUser(bookingUser);

    }

    public void addBookingAgent(Connection conn, User user, Booking booking) throws SQLException, ClassNotFoundException {
        BookingAgentDAO bookingAgentDAO = new BookingAgentDAO(conn);
        BookingAgent bookingAgent = new BookingAgent(booking, user);

        bookingAgentDAO.addBookingAgent(bookingAgent);

    }
    public void addBookingGuest(Connection conn, Passenger passenger) throws SQLException, ClassNotFoundException {
        BookingGuestDAO bookingGuestDAO = new BookingGuestDAO(conn);
        String[] emailAndPhone = new PassengerMenu().getEmailAndPhone();

        BookingGuest bookingGuest = new BookingGuest(passenger.getBooking(), emailAndPhone[0], emailAndPhone[1]);
        bookingGuestDAO.addBookingGuest(bookingGuest);
    }

    public void addFlightBooking(Connection conn, Flight flight, Booking booking) throws SQLException, ClassNotFoundException {
        FlightBookingDAO flightBookingDAO = new FlightBookingDAO(conn);

        FlightBooking flightBooking = new FlightBooking(flight, booking);
        flightBookingDAO.addFlightBooking(flightBooking);
    }

    public void addBookingPayment(Connection conn, BookingPayment bookingPayment) throws SQLException, ClassNotFoundException {
        BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
        bookingPaymentDAO.addBookingPayment(bookingPayment);
    }

    public Booking getBookingInfo(String confirmationCode) {
        Connection conn = null;
        Booking booking = null;
        try {
            conn = connectionUtil.getConnection();
            BookingDAO bookingDAO = new BookingDAO(conn);
            booking = bookingDAO.getBookingByCode(confirmationCode);

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

        return booking;
    }

    public Booking getBookingInfo(Integer bookingID) {
        Connection conn = null;
        Booking booking = null;
        try {
            conn = connectionUtil.getConnection();
            BookingDAO bookingDAO = new BookingDAO(conn);
            booking = bookingDAO.getBookingById(bookingID);
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

        return booking;
    }

    public BookingUser getBookingUserFromUser(User user){
        Connection conn = null;
        BookingUser bookingUser = null;
        try {
            conn = connectionUtil.getConnection();
            BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
            bookingUser = bookingUserDAO.getBookingUserFromUser(user);
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

        return bookingUser;
    }

    public void updateBookingPayment(Booking booking, String stripeID) {
        Connection conn = null;
        try {
            conn = connectionUtil.getConnection();
            BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
            BookingPayment bookingPayment = bookingPaymentDAO.getBookingPaymentByBooking(booking);
            bookingPayment.setStripeId(stripeID);

            bookingPaymentDAO.updateBookingPayment(bookingPayment);

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

    public void cancelTrip(Booking booking, Boolean cancel) {
        Connection conn = null;
        try {
            conn = connectionUtil.getConnection();
            BookingDAO bookingDAO = new BookingDAO(conn);
            BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
            BookingPayment bookingPayment = bookingPaymentDAO.getBookingPaymentByBooking(booking);

            if(cancel){
                bookingPayment.setRefunded(true);
                booking.setIsActive(0);
            }else {
                bookingPayment.setRefunded(false);
                booking.setIsActive(1);
            }

            bookingDAO.updateBooking(booking);
            bookingPaymentDAO.updateBookingPayment(bookingPayment);
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

    public void deleteBooking(Booking booking) {
        Connection conn = null;
        try {
            conn = connectionUtil.getConnection();
            BookingDAO bookingDAO = new BookingDAO(conn);

            BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
            PassengerDAO passengerDAO = new PassengerDAO(conn);
            FlightBookingDAO flightBookingDAO = new FlightBookingDAO(conn);
            BookingAgentDAO bookingAgentDAO = new BookingAgentDAO(conn);
            BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
            BookingGuestDAO bookingGuestDAO = new BookingGuestDAO(conn);

            BookingUser bookingUser = bookingUserDAO.getBookingUserFromBooking(booking);
            BookingAgent bookingAgent = bookingAgentDAO.getBookingAgentFromBooking(booking);
            FlightBooking flightBooking = flightBookingDAO.getFlightBookingByBooking(booking);
            Passenger passenger = passengerDAO.getPassengerByBooking(booking);
            BookingGuest bookingGuest = bookingGuestDAO.getBookingGuestByBooking(booking);
            BookingPayment bookingPayment = bookingPaymentDAO.getBookingPaymentByBooking(booking);

            if(bookingUser!=null)
                bookingUserDAO.deleteBookingUser(bookingUser);
            if(bookingAgent!=null)
                bookingAgentDAO.deleteBookingAgent(bookingAgent);
            if(flightBooking!=null)
                flightBookingDAO.deleteFlightBooking(flightBooking);
            if(passenger!=null)
                passengerDAO.deletePassenger(passenger);
            if(bookingGuest!=null)
                bookingGuestDAO.deleteBookingGuest(bookingGuest);
            if(bookingPayment!=null)
                bookingPaymentDAO.deleteBookingPayment(bookingPayment);

            bookingDAO.deleteBooking(booking);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
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

    public String[] getAllBookings(){
        Connection conn = null;
        String[] bookings = null;
        try {
            conn = connectionUtil.getConnection();
            BookingDAO bookingDAO = new BookingDAO(conn);
            bookings =  bookingDAO.getAllBookings().stream()
                    .map(booking -> booking.getId() + " " + booking.getConfirmationCode())
                    .toList().toArray(value -> new String[value]);

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

        return bookings;
    }

}
