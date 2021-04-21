package com.ss.uto.dao.tests;


import com.ss.uto.dao.BookingDAO;
import com.ss.uto.dao.BookingUserDAO;
import com.ss.uto.dao.UserDAO;
import com.ss.uto.entity.Booking;
import com.ss.uto.entity.BookingAgent;
import com.ss.uto.entity.BookingUser;
import com.ss.uto.entity.User;
import com.ss.uto.service.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingUserDAOTest {
    ConnectionUtil connUtil = new ConnectionUtil();

    @Test
    void addBookingUser() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookingUserDAO badao = new BookingUserDAO(conn);
            BookingDAO bookingDAO = new BookingDAO(conn);
            UserDAO udao = new UserDAO(conn);

            User user = udao.getAllUsers().get(0);
            Booking booking = bookingDAO.getAllBookings().get(0);

            BookingUser bookingUser = new BookingUser(user, booking);
            badao.addBookingUser(bookingUser);

            assertEquals(bookingUser.getBooking().getId(), badao.getBookingUserFromUser(user).getUser().getId());
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally{
            conn.close();
        }
    }

    @Test
    void deleteBookingUser() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookingUserDAO badao = new BookingUserDAO(conn);

            List<BookingUser> bookingUserList = badao.getAllBookingUsers();
            BookingUser bookingUser = bookingUserList.get(bookingUserList.size() - 1);

            badao.deleteBookingUser(bookingUser);

            assertNull(badao.getBookingUserFromBooking(bookingUser.getBooking()));
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally{
            conn.close();
        }
    }
}