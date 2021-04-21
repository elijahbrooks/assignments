package com.ss.uto.dao;

import com.ss.uto.entity.Airplane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDAO extends BaseDAO<Airplane> {


    public AirplaneDAO(Connection conn) {
        super(conn);
    }

    public Integer addAirplane(Airplane airplane) throws SQLException, ClassNotFoundException {
        Integer key = saveWithPK("INSERT INTO airplane(id, type_id) VALUES (?, ?)", new Object[]{
                airplane.getId(),
                airplane.getAirplaneType().getId()});
        airplane.setId(key);
        return key;
    }

    public void updateAirplane(Airplane airplane) throws SQLException, ClassNotFoundException{
        save("UPDATE airplane SET type_id = ? WHERE id = ?", new Object[]{
           airplane.getAirplaneType().getId(), airplane.getId()});
    }

    public void deleteAirplane(Airplane airplane) throws SQLException, ClassNotFoundException{
        save("DELETE FROM airplane WHERE id = ?", new Object[]{airplane.getId()});
    }

    public Airplane getAirplaneById(Integer id) throws SQLException, ClassNotFoundException{
        List<Airplane> airplanes = read("SELECT airplane.id, type_id, max_capacity FROM utopia.airplane\n" +
                "JOIN airplane_type ON airplane_type.id = type_id AND airplane.id = ?", new Object[]{id});
        if(airplanes.size() > 0)
            return airplanes.get(0);

        return null;
    }

    public List<Airplane> getAllAirplanes() throws SQLException, ClassNotFoundException{
        return read("SELECT airplane.id, type_id, max_capacity FROM utopia.airplane\n" +
                "JOIN airplane_type ON airplane_type.id = type_id;", null);
    }

    @Override
    public List<Airplane> extractData(ResultSet rs) throws SQLException {
       List<Airplane> airplanes = new ArrayList<>();

       while(rs.next())
           airplanes.add(Airplane.toObject(rs));

       return airplanes;
    }
}
