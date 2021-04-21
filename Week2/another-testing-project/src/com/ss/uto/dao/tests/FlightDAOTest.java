package com.ss.uto.dao.tests;

import com.ss.uto.dao.AirplaneDAO;
import com.ss.uto.dao.AirportDAO;
import com.ss.uto.dao.FlightDAO;
import com.ss.uto.dao.RouteDAO;
import com.ss.uto.entity.Airplane;
import com.ss.uto.entity.Flight;
import com.ss.uto.entity.Route;
import com.ss.uto.service.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class FlightDAOTest {
    ConnectionUtil connUtil = new ConnectionUtil();

    @Test
    void addFlight() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            RouteDAO routeDAO = new RouteDAO(conn);
            AirplaneDAO airplaneDAO = new AirplaneDAO(conn);
            FlightDAO flightDAO = new FlightDAO(conn);

            Route route = routeDAO.getAllRoutes().get(routeDAO.getAllRoutes().size() - 1);
            Airplane airplane = airplaneDAO.getAllAirplanes().get(airplaneDAO.getAllAirplanes().size() - 1);

            Flight flight = new Flight(new Timestamp(99999999), 23, 200.22F);
            flight.setRoute(route);
            flight.setAirplane(airplane);

            flightDAO.addFlight(flight);

            assertEquals(flight.getId(), flightDAO.getFlightFromId(flight.getId()).getId());

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally{
            conn.close();
        }
    }

    @Test
    void updateFlight() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            Flight flight = flightDAO.getAllFlights().get(flightDAO.getAllFlights().size() - 1);
            flight.setSeatPrice(1000F);

            flightDAO.updateFlight(flight);
            assertEquals(flight.toString(), flightDAO.getFlightFromId(flight.getId()).toString());
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally{
            conn.close();
        }
    }

    @Test
    void deleteFlight() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            Flight flight = flightDAO.getAllFlights().get(flightDAO.getAllFlights().size() - 1);

            flightDAO.deleteFlight(flight);
            assertNull(flightDAO.getFlightFromId(flight.getId()));
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally{
            conn.close();
        }
    }
}