/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.User;
import dataaccess.UserDB;
import java.util.List;
import exceptions.InvalidFieldsException;

/**
 *
 * @author allen
 */
public class UserService {
    
    private final UserDB accessUsers = new UserDB();
    
    
    public List<User> getAll() throws Exception {
        
        List<User> users = accessUsers.getAll();
        
        return users;
    }
    
    
    public User get(String email) throws Exception {
        
        User user = accessUsers.get(email);
        
        return user;
    }
    
    
    public void insert(User user) throws Exception, InvalidFieldsException {
       
        
        // check all attributes are valid
        if (user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null ||
                user.getEmail().equals("") || user.getFirstName().equals("") || user.getLastName().equals("") || user.getPassword().equals("")) {
            throw new InvalidFieldsException();
        } else {
            accessUsers.insert(user);
        }
        
    }
    
    
    public void update(User user) throws Exception, InvalidFieldsException {
        
        
        if (user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null ||
                user.getEmail().equals("") || user.getFirstName().equals("") || user.getLastName().equals("") || user.getPassword().equals("")) {
            throw new InvalidFieldsException();
        } else {
            
            User oldUser = accessUsers.get(user.getEmail());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setPassword(user.getPassword());
            oldUser.setRole(user.getRole());
            accessUsers.update(oldUser);
        }
        
        
    }
    
    
    public void delete(String email) throws Exception {
        accessUsers.delete(email);
    }
}