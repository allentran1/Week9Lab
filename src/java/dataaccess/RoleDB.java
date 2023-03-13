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
import models.Role;

/**
 *
 * @author allen
 */
public class RoleDB {
        /**
     * Get All Roles
     * @return roles as a list
     * @throws Exception 
     */
    public List<Role> getAll() throws Exception {
        List<Role> roles = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM userdb.role";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                
                int roleId = rs.getInt(1);
                String roleName = rs.getString(2);
                
                Role role = new Role(roleId, roleName);
                roles.add(role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return roles;
    }
    
    /**
     * Get role based on id
     * @param roleId
     * @return Role
     * @throws Exception 
     */
    public Role get(int roleId) throws Exception {
        Role role = null;
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM userdb.role WHERE role_id=?";
        
        try {
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                
                int userId = rs.getInt(1);
                String userRole = rs.getString(2);
                
                
                role = new Role(userId, userRole);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return role;
    }
}
