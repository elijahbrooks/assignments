package com.ss.uto.service;

import com.ss.uto.dao.RouteDAO;
import com.ss.uto.entity.Airport;
import com.ss.uto.entity.Route;


import java.sql.Connection;
import java.sql.SQLException;

public class RouteService {
    ConnectionUtil connUtil = new ConnectionUtil();

    public Route getRoute(Connection conn, Airport origin, Airport destination) throws SQLException, ClassNotFoundException {
        Route route = null;
        RouteDAO routeDAO = new RouteDAO(conn);
        route = routeDAO.getRouteByLocation(origin.getAirportCode(), destination.getAirportCode());

        if(route == null)
            route = routeDAO.getRouteById(routeDAO.addRoute(new Route(origin, destination)));

        conn.commit();
        return route;
    }

    public Route getRoute(Integer id){
        Connection conn = null;
        Route route = null;
        try{
            conn = connUtil.getConnection();
            RouteDAO routeDAO = new RouteDAO(conn);
            route = routeDAO.getRouteById(id);
        }catch(Exception e){
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return route;
    }
}

