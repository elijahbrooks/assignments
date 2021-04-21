package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.*;
import com.ss.uto.menu.BaseMenu;
import com.ss.uto.service.BookingService;
import com.ss.uto.service.admin.AdminBookings;
import java.sql.Connection;

public class BookingMenu extends Helper implements BaseMenu {
    BookingService bookingService = new BookingService();
    AdminBookings adminBookings = new AdminBookings();
    PassengerMenu passengerMenu = new PassengerMenu();


    @Override
    public Object add() {
        adminBookings.addBooking(null, null);
        return null;
    }

    @Override
    public Object update() {

       Booking booking = confirmationCodePrompt();
        System.out.println("What do you want to update?: ");
        Integer choice = new Menu(new String[]{"Passenger", "Payment Info"}).getInput();

        if (choice != null){
            if(choice == 0){
                passengerMenu.updatePassenger(booking);
            }else
                updatePaymentInfo(booking);
        }
        return null;
    }

    @Override
    public Object delete() {
        Booking booking = chooseBooking();
        bookingService.deleteBooking(booking);
        return null;
    }

    @Override
    public Object read() {
        String[] bookings = bookingService.getAllBookings();

        System.out.println("Press enter to continue");
        new Menu(bookings).inputMenu();

        return null;
    }


    public void updatePaymentInfo(Booking booking){
        String input = new Menu(new String[]{"Enter card number: "}).inputMenu();
        bookingService.updateBookingPayment(booking, input);
    }

    public Booking confirmationCodePrompt(){
         String confirmationCode = new Menu(new String[]{"Input Confirmation Code: "}).inputMenu();

         Booking booking = bookingService.getBookingInfo(confirmationCode);

         if(booking != null) {
             System.out.println(booking.getId());
             return booking;
         }
         else
             return confirmationCodePrompt();
    }

    public void processPayment(Connection conn, Booking booking, Flight flight){
        try{
            Integer seats = Integer.parseInt(new Menu(new String[]{"How many seats? ($"+flight.getSeatPrice()+" Each):" +
                    " "}).inputMenu());

            Float total = flight.getSeatPrice() * seats;
            System.out.print("Your total is: $");
            System.out.printf("%.2f", total);
            System.out.println();

            String input = new Menu(new String[]{"Enter card number: "}).inputMenu();
            BookingPayment bookingPayment = new BookingPayment(input, false);
            bookingPayment.setBooking(booking);

            bookingService.addBookingPayment(conn, bookingPayment);

            System.out.println("Tickets purchased. \nConfirmation Number: " + booking.getConfirmationCode());
        }catch(Exception e){
            System.out.println("Please input number.");
            processPayment(conn, booking, flight);
        }
    }

    public Booking chooseBooking(){
        String[] bookings = bookingService.getAllBookings();
        Integer bookingId = Integer.parseInt(new Menu(bookings).getInputAsString().split(" ")[0]);
        Booking booking = bookingService.getBookingInfo(bookingId);

        return booking;
    }
}
