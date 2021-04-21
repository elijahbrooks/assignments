package com.ss.uto.service;

import com.ss.uto.dao.AirplaneDAO;
import com.ss.uto.dao.AirplaneTypeDAO;
import com.ss.uto.entity.Airplane;
import com.ss.uto.entity.AirplaneType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AirplaneService {
    ConnectionUtil connUtil = new ConnectionUtil();

    public Airplane getAirplane(Integer airplaneIndex){
        Connection conn = null;
        Airplane airplane = null;
        try {
            conn = connUtil.getConnection();
            AirplaneDAO airplaneDAO = new AirplaneDAO(conn);

            airplane = airplaneDAO.getAirplaneById(airplaneIndex);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return airplane;
    }

    public String[] getAllAirplanes(){
        Connection conn = null;
        String[] context = null;
        try {
            conn = connUtil.getConnection();
            AirplaneDAO airplaneDAO = new AirplaneDAO(conn);
            List<Airplane> allPlanes = airplaneDAO.getAllAirplanes();

            context = allPlanes.stream()
                    .map(plane -> "Plane " + plane.getId() +
                            " Max Capacity: " + plane.getAirplaneType().getMaxCapacity())
                    .toList().toArray(new String[allPlanes.size()]);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return context;
    }

    public Airplane addAirplane(Connection conn, Integer maxCapacity) throws SQLException, ClassNotFoundException {
        AirplaneDAO airplaneDAO = new AirplaneDAO(conn);
        AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);

        Integer key = airplaneTypeDAO.addAirplaneType(new AirplaneType(maxCapacity));
        AirplaneType airplaneType = airplaneTypeDAO.getAirplaneTypeByID(key);

        return airplaneDAO.getAirplaneById(airplaneDAO.addAirplane(new Airplane(airplaneType)));
    }

    public void updateAirplane(Connection conn, Airplane airplane) throws SQLException, ClassNotFoundException {
        AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);
        airplaneTypeDAO.updateAirplaneType(airplane.getAirplaneType());
    }

    public void deleteAirplane(Connection conn, Airplane airplane) throws SQLException, ClassNotFoundException {
        AirplaneDAO airplaneDAO = new AirplaneDAO(conn);
        airplaneDAO.deleteAirplane(airplane);
    }
}
