package com.ss.uto.service;

import com.ss.uto.dao.UserDAO;
import com.ss.uto.dao.UserRoleDAO;
import com.ss.uto.entity.User;
import com.ss.uto.entity.UserRole;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    ConnectionUtil connectionUtil = new ConnectionUtil();

    public String[] getAllUsers(boolean employees){
        Connection conn = null;
        String[] users = null;
        try{
            conn = connectionUtil.getConnection();
            UserDAO userDAO = new UserDAO(conn);

            users = employees ?
                    userDAO.getAllEmployees().stream()
                            .map(user -> user.getId() + " | " + user.getFamilyName() + ", " + user.getGivenName())
                            .toList().toArray(String[]::new)
            :
                    userDAO.getAllTravelers().stream()
                            .map(user -> user.getId() + " | " + user.getFamilyName() + ", " + user.getGivenName())
                            .toList().toArray(String[]::new);

        }catch(Exception e){
            e.printStackTrace();
            try {
                conn.commit();
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

        return users;
    }

    public User getUser(Integer userId){
        Connection conn = null;
        User user = null;
        try{
            conn = connectionUtil.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            user = userDAO.getUserById(userId);
        }catch(Exception e){
            try {
                conn.commit();
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

        return user;
    }

    public User loginUser(String username, String password){
        Connection conn = null;
        User user = null;
        try{
            conn = connectionUtil.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            user = userDAO.getUserByCred(username, password);
        }catch(Exception e){
            try {
                conn.commit();
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

        return user;
    }

    public void addUser(User user, Boolean employee){
        Connection conn = null;
        try{
            conn = connectionUtil.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            UserRoleDAO userRoleDAO = new UserRoleDAO(conn);

            UserRole userRole = employee ?
                    userRoleDAO.getAllUserRoles().get(1)
                    :
                    userRoleDAO.getAllUserRoles().get(2);

            user.setUserRole(userRole);
            userDAO.addUser(user);

            conn.commit();
        }catch(Exception e){
            try {
                conn.commit();
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

    }

    public void updateUser(User user){
        Connection conn = null;
        try{
            conn = connectionUtil.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            userDAO.updateUser(user);

            conn.commit();
        }catch(Exception e){
            try {
                conn.commit();
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
    }

    public void deleteUser(User user){
        Connection conn = null;
        try{
            conn = connectionUtil.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            userDAO.deleteUser(user);

            conn.commit();
        }catch(Exception e){
            try {
                conn.commit();
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

    }

}
