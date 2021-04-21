package com.ss.uto.service;


import com.ss.uto.dao.AirportDAO;
import com.ss.uto.entity.Airport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AirportService {
    ConnectionUtil connectionUtil = new ConnectionUtil();

    public String[] getAllAirports(Connection conn, Airport exclude) throws SQLException, ClassNotFoundException {
        String[] originAirports;
        AirportDAO airportDAO = new AirportDAO(conn);
        List<Airport> airportList = airportDAO.getAllAirports();

        if(exclude != null) {
            originAirports = airportList.stream()
                    .filter(airport -> (!airport.getAirportCode().equals(exclude.getAirportCode())) &&
                            (!airport.getCityName().equals(exclude.getCityName())))
                    .map(airport -> airport.getAirportCode() + ", " + airport.getCityName())
                    .toList().toArray(value -> new String[value]);
        }
        else {
            originAirports = airportList.stream()
                    .map(airport -> airport.getAirportCode() + ", " + airport.getCityName())
                    .toList().toArray(new String[airportList.size()]);
        }
        return originAirports;
    }

    public Airport getAirport(Connection conn, String areaCode) throws SQLException, ClassNotFoundException {
        AirportDAO airportDAO = new AirportDAO(conn);
        Airport airport = airportDAO.getAirportByAirportCode(areaCode);
        return airport;
    }
    public Airport addAirport(Connection conn, String areaCode, String cityName) throws SQLException, ClassNotFoundException {
        AirportDAO airportDAO = new AirportDAO(conn);
        airportDAO.addAirport(new Airport(areaCode, cityName));

        Airport airport = airportDAO.getAirportByAirportCode(areaCode);
        return airport;
    }



    public String[] getAllAirports(){
        Connection conn = null;
        String[] airports = null;
        try {
            conn = connectionUtil.getConnection();
            airports = getAllAirports(conn, null);
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

        return airports;
    }

    public Airport getAirport(String areaCode){
        Connection conn = null;
        Airport airport = null;
        try {
            conn = connectionUtil.getConnection();
            airport = getAirport(conn, areaCode);
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
        return airport;
    }

    public void updateAirport(Airport airport){
        Connection conn = null;
        try {
            conn = connectionUtil.getConnection();
            AirportDAO airportDAO = new AirportDAO(conn);
            airportDAO.updateAirport(airport);

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

    public void deleteAirport(Airport airport){
        Connection conn = null;
        try {
            conn = connectionUtil.getConnection();
            AirportDAO airportDAO = new AirportDAO(conn);
            airportDAO.deleteAirport(airport);

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
