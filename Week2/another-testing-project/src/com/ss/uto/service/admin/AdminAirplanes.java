package com.ss.uto.service.admin;

import com.ss.uto.entity.Airplane;
import com.ss.uto.menu.entity.AirplaneMenu;
import com.ss.uto.service.AirplaneService;
import com.ss.uto.service.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminAirplanes {
    ConnectionUtil connUtil = new ConnectionUtil();

    public void addAirplane(){
        Connection conn = null;
        try{
            conn = connUtil.getConnection();
            AirplaneMenu airplaneMenu = new AirplaneMenu();
            airplaneMenu.addAirplane(conn);

            conn.commit();
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addAirplane();
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void updateAirplane(Airplane airplane){
        Connection conn = null;
        try{
            conn = connUtil.getConnection();
            AirplaneService airplaneService = new AirplaneService();
            airplaneService.updateAirplane(conn, airplane);

            conn.commit();
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addAirplane();
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void deleteAirplane (Airplane airplane){
        Connection conn = null;
        try{
            conn = connUtil.getConnection();
            AirplaneService airplaneService = new AirplaneService();
            airplaneService.deleteAirplane(conn, airplane);

            conn.commit();
        }catch(Exception e){
            System.out.println("An error occurred. Please try again.");
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addAirplane();
        }finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
