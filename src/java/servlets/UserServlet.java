/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.RoleDB;
import exceptions.InvalidFieldsException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.*;
import models.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author allen
 */
public class UserServlet extends HttpServlet {

    private boolean first = true;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<User> users;
        List<Role> roles;
        
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        
        String action = request.getParameter("action");
        
        request.setAttribute("edit", false);
        
        
        if (action != null && action.equals("delete")) {
            String deleteEmail = request.getParameter("userEmail");
            
            try {
                us.delete(deleteEmail);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action != null && action.equals("edit")){ // edit a user
            
            request.setAttribute("edit", true); // so the edit page shows up.
            try {
                User editUser = us.get(request.getParameter("userEmail")); // get user to edit
                request.setAttribute("editUser", editUser);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        try {
            users = us.getAll();
            roles = rs.getAll();
            
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<User> users;
        List<Role> roles;
        
        String action = request.getParameter("action");
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        request.setAttribute("edit", false);
        
//        HttpSession session = request.getSession();
        
        if (action != null && action.equals("add")) {
            
            String email = request.getParameter("email").trim();
            String firstName = request.getParameter("fname").trim();
            String lastName = request.getParameter("lname").trim();
            String password = request.getParameter("password").trim();
            int roleId = Integer.parseInt(request.getParameter("role"));
            
            
            try {
                RoleDB accessRoles = new RoleDB();
                Role role = accessRoles.get(roleId);
                
                User insertUser = new User(email, firstName, lastName, password);
                insertUser.setRole(role);
                
                us.insert(insertUser);
                request.removeAttribute("error");
                
            } catch (InvalidFieldsException inv) {
                request.setAttribute("error", "There was an invalid field. User was not added.");
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (action != null && action.equals("update")) {
            
            User newUser;
            
            String email = request.getParameter("email");
            String firstName = request.getParameter("fname").trim();
            String lastName = request.getParameter("lname").trim();
            String password = request.getParameter("password").trim();
            int roleId = Integer.parseInt(request.getParameter("role"));
            
            
            
            
            
            
            try {
                RoleDB accessRoles = new RoleDB();
                Role role = accessRoles.get(roleId);
                
                newUser = new User(email, firstName, lastName, password);
                newUser.setRole(role);
            
                us.update(newUser);
                request.removeAttribute("error");
            } catch (InvalidFieldsException inv) {
                
                request.setAttribute("error", "There was an invalid field. User was not updated.");
                request.setAttribute("edit", true);
                
                try {
                    User editUser = us.get(request.getParameter("userEmail")); // get user to edit
                    request.setAttribute("editUser", editUser);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        try {
            users = us.getAll();
            roles = rs.getAll();
            
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        
        
    }

}