package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.Airplane;
import com.ss.uto.menu.BaseMenu;
import com.ss.uto.service.AirplaneService;
import com.ss.uto.service.admin.AdminAirplanes;

import java.sql.Connection;
import java.sql.SQLException;

public class AirplaneMenu extends Helper implements BaseMenu {
    AdminAirplanes adminAirplanes = new AdminAirplanes();
    AirplaneService airplaneService = new AirplaneService();

    @Override
    public Object add() {
        adminAirplanes.addAirplane();

        System.out.println("Airplane Successfully Added");
        return null;
    }

    @Override
    public Object update() {

        Airplane airplane = chooseAirplane();
        airplane.getAirplaneType().setMaxCapacity(chooseMaxCapacity());

        adminAirplanes.updateAirplane(airplane);
        return null;
    }

    @Override
    public Object delete() {
        adminAirplanes.deleteAirplane(chooseAirplane());
        return null;
    }

    @Override
    public Object read() {
        System.out.println("Press Enter to continue.");
        new Menu(airplaneService.getAllAirplanes()).inputMenu();

        return null;
    }

    public Airplane chooseAirplane(){
        System.out.println("Choose an Airplane.");

        Menu menu = new Menu(airplaneService.getAllAirplanes());
        String input = menu.getInputAsString();

        if(input != null){
            Integer planeId = Integer.parseInt(input.split(" ")[1]);
            return airplaneService.getAirplane(planeId);
        }else{
            return chooseAirplane();
        }
    }

    public void addAirplane(Connection conn) throws SQLException, ClassNotFoundException {
        airplaneService.addAirplane(conn, chooseMaxCapacity());
    }

    private Integer chooseMaxCapacity(){
        Integer maxCapacity;
        Menu menu = new Menu(new String[]{"Input max capacity."});
        String input = menu.inputMenu();
        try {
            maxCapacity = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Please input a number.");
            return chooseMaxCapacity();
        }
        return maxCapacity;
    }
}
