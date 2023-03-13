/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.User;
import models.Role;

/**
 *
 * @author allen
 */
public class UserDB {
    
    /**
     * get all users
     * @return users as a list
     * @throws Exception 
     */
    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM userdb.user";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                
                String userEmail = rs.getString(1);
                String userFn = rs.getString(2);
                String userLn = rs.getString(3);
                String userPassword = rs.getString(4);
                int userRole = rs.getInt(5);
                
                // the role at this time is only the role id given in the table
                User user = new User(userEmail, userFn, userLn, userPassword, new Role(userRole, ""));
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }

    /**
     * Get user by email
     * @param email
     * @return User
     * @throws Exception 
     */
    public User get(String email) throws Exception {
        User user = null;
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM userdb.user WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String userEmail = rs.getString(1);
                String userFn = rs.getString(2);
                String userLn = rs.getString(3);
                String userPassword = rs.getString(4);
                int userRole = rs.getInt(5);
                
                // the role at this time is only the role id given in the table
                user = new User(userEmail, userFn, userLn, userPassword, new Role(userRole, ""));
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return user;
    }
    
    /**
     * Make new user
     * @param user
     * @throws Exception 
     */
    public void insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO userdb.user (email, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getId());
            
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    /**
     * update an existing user
     * @param user
     * @throws Exception 
     */
    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE userdb.user SET first_name=?, last_name=?, password=?, role=? WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getId());
            
            ps.setString(5, user.getEmail());
            
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    /**
     * delete a user by email
     * @param email
     * @throws Exception 
     */
    public void delete(String email) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM userdb.user WHERE email=(?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
