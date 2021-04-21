package com.ss.uto.service.admin;


import com.ss.uto.Menu;
import com.ss.uto.entity.Airplane;
import com.ss.uto.entity.Airport;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.Route;
import com.ss.uto.menu.entity.AirplaneMenu;
import com.ss.uto.menu.entity.AirportMenu;
import com.ss.uto.menu.entity.FlightMenu;
import com.ss.uto.service.AirportService;
import com.ss.uto.service.ConnectionUtil;
import com.ss.uto.service.FlightService;
import com.ss.uto.service.RouteService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AdminFlights {
    ConnectionUtil connUtil = new ConnectionUtil();


    public Flight addFlight()  {
        AirportMenu airportMenu = new AirportMenu();
        FlightMenu flightMenu = new FlightMenu();

        Flight flight = null;
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            FlightService flightService = new FlightService();

            Airport origin = airportMenu.chooseAirport(conn,null, "Origin");
            Airport destination = airportMenu.chooseAirport(conn, origin, "Destination");
            Airplane airplane = new AirplaneMenu().chooseAirplane();

            Route route = new RouteService().getRoute(conn, origin, destination);

            Timestamp departureTime = flightMenu.chooseDateTime();
            Float seatPrice = flightMenu.chooseSeatPrice();
            flight = flightService.addFlight(conn, airplane, route, departureTime, seatPrice);

            conn.commit();
        } catch (Exception e) {
            System.out.println("An error occurred. Please try again.");
            e.printStackTrace();
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addFlight();
        } finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flight;
    }

    public Airport addAirport(){
        Airport airport = null;
        Connection conn = null;
        try{
            conn = connUtil.getConnection();
            airport = addAirportWithConn(conn);
            conn.commit();
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addAirport();
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return airport;
    }

    public void updateFlight(Flight flight){
        Connection conn = null;
        try{
            conn = connUtil.getConnection();
             updateFlightWithConn(conn, flight);
            conn.commit();
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addAirport();
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void deleteFlight(Flight flight){
        Connection conn = null;
        try{
            conn = connUtil.getConnection();
            deleteFlightWithConn(conn, flight);
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addAirport();
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void updateFlightWithConn(Connection conn, Flight flight) throws Exception{
        FlightService flightService = new FlightService();
        flightService.updateFlight(conn, flight);
    }

    public void deleteFlightWithConn(Connection conn, Flight flight) throws Exception{
        FlightService flightService = new FlightService();
        flightService.deleteFlight(conn, flight);
    }


    public Airport addAirportWithConn(Connection conn) throws Exception {
        AirportService airportService = new AirportService();

        String areaCode = new Menu(new String[]{"Input Airport Code: "}).inputMenu();
        String cityName = new Menu(new String[]{"Input City Name: "}).inputMenu();

        return airportService.addAirport(conn, areaCode, cityName);
    }
}
