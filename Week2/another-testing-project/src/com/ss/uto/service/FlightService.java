package com.ss.uto.service;

import com.ss.uto.dao.AirplaneDAO;
import com.ss.uto.dao.FlightDAO;
import com.ss.uto.dao.RouteDAO;
import com.ss.uto.entity.Airplane;
import com.ss.uto.entity.AirplaneType;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.Route;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class FlightService {

    public Flight addFlight(Connection conn, Airplane airplane, Route route, Timestamp departTime, Float seatPrice) throws SQLException, ClassNotFoundException {
        FlightDAO flightDAO = new FlightDAO(conn);

        Flight flight = new Flight(departTime, 0, seatPrice);
        flight.setAirplane(airplane);
        flight.setRoute(route);

        Integer key = flightDAO.addFlight(flight);
        flight = flightDAO.getFlightFromId(key);

        return flight;
    }

    public String[] listAllFlights(){
        String[] flights = null;
        Connection conn = null;
        try {
            conn = new ConnectionUtil().getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            RouteDAO routeDAO = new RouteDAO(conn);
            AirplaneDAO airplaneDAO = new AirplaneDAO(conn);
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

            List<Flight> allFlights = flightDAO.getAllFlights();

            flights = allFlights.stream()
                    .map(flight -> {
                        String flightName = "";
                        try {
                            Route route = routeDAO.getRouteById(flight.getRoute().getId());
                            Integer maxCapacity = airplaneDAO.getAirplaneById(flight.getAirplane()
                                    .getId()).getAirplaneType().getMaxCapacity();
                            String departTime = flight.getDepartTime().toLocalDateTime().format(formatter);

                             flightName =  flight.getId() + " | "+ route.getOriAirport().getAirportCode() +
                                    " -> " + route.getDesAirport().getAirportCode() + " | DEPARTURE AT: " + departTime +
                                    " | AVAILABLE SEATS: " + (maxCapacity - flight.getReservedSeats()) + " | SEAT PRICE: " +
                                    "$" + flight.getSeatPrice();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        return flightName;
                    }).toList().toArray(new String[allFlights.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
            }
        }

        return flights;
    }

    public String[] listAllFlightsEmployee(){
        String[] flights = null;
        Connection conn = null;
        try {
            conn = new ConnectionUtil().getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            RouteDAO routeDAO = new RouteDAO(conn);
            AirplaneDAO airplaneDAO = new AirplaneDAO(conn);
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

            List<Flight> allFlights = flightDAO.getAllFlights();

            flights = allFlights.stream()
                    .map(flight -> {
                        String flightName = "";
                        try {
                            Route route = routeDAO.getRouteById(flight.getRoute().getId());
                            flightName =  flight.getId() + " | "+ route.getOriAirport().getAirportCode() +
                                    ", " + route.getOriAirport().getCityName() + " -> " + route.getDesAirport().getAirportCode() +
                                    ", " + route.getDesAirport().getCityName();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        return flightName;
                    }).toList().toArray(new String[allFlights.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
            }
        }

        return flights;
    }

    public Flight getFlight(Integer id){
        Connection conn = null;
        Flight flight = null;
        try{
            conn = new ConnectionUtil().getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);

            flight = flightDAO.getFlightFromId(id);
        }catch (Exception e){
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return flight;
    }

    public void updateFlight(Connection conn, Flight flight) throws SQLException, ClassNotFoundException {
        FlightDAO flightDAO = new FlightDAO(conn);
        flightDAO.updateFlight(flight);
    }

    public void deleteFlight(Connection conn, Flight flight) throws SQLException, ClassNotFoundException {
        FlightDAO flightDAO = new FlightDAO(conn);
        flightDAO.deleteFlight(flight);
    }
}
