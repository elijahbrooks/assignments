package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.Airport;
import com.ss.uto.menu.BaseMenu;
import com.ss.uto.service.AirportService;
import com.ss.uto.service.admin.AdminFlights;

import java.sql.Connection;

public class AirportMenu extends Helper implements BaseMenu {
    AirportService airportService = new AirportService();

    @Override
    public Airport add() {
        AdminFlights adminFlights = new AdminFlights();
        Airport airport = adminFlights.addAirport();
        return airport;
    }

    @Override
    public Integer update() {
        System.out.println("Choose an Airport to Update: ");
        String areaCode = new Menu(airportService.getAllAirports()).getInputAsString().split(", ")[0];
        System.out.println(areaCode);
        Airport airport = airportService.getAirport(areaCode);

        System.out.println("Input new city name: ");
        String cityName = new Menu(new String[]{"Input new city name: "}).inputMenu();

        airport.setCityName(cityName);

        airportService.updateAirport(airport);
        return null;
    }

    @Override
    public Integer delete() {
        System.out.println("Choose an Airport to Update: ");
        String areaCode = new Menu(airportService.getAllAirports()).getInputAsString().split(", ")[0];

        Airport airport = airportService.getAirport(areaCode);
        airportService.deleteAirport(airport);

        return null;
    }

    @Override
    public Integer read() {
        System.out.println("Press enter to continue. ");
        new Menu(airportService.getAllAirports()).inputMenu();

        return null;
    }

    public Airport chooseAirport(Connection conn, Airport excluded, String name) throws Exception {
        AirportService airportService = new AirportService();
        Airport airport = null;

        String[] oriAirports = airportService.getAllAirports(conn, excluded);
        oriAirports = pushToArray("Create new Airport", oriAirports);

        System.out.println("Choose an " + name + " Airport.\n");
        String input = new Menu(oriAirports).getInputAsString();

        if(input != null) {
            String code = input.split(", ")[0];
            if(code != "Create new Airport")
                airport = airportService.getAirport(conn, code);
            else
               airport = new AdminFlights().addAirportWithConn(conn);

        }else{
            chooseAirport(conn, excluded, name);
        }

        return airport;
    }
}
