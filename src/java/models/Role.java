/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author allen
 */
public class Role implements Serializable {
    private int id;
    private String roleName;
    
    public Role() {
        
    }
    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public void setRoleName (String roleName) {
        this.roleName = roleName;
    }
    
    public int getId () {
        return this.id;
    }
    public String getRoleName() {
        return this.roleName;
    }
}