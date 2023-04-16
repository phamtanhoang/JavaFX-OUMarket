/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import java.nio.channels.ConnectionPendingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gol
 */
public class jdbcService {
    private static Connection conn;

   
    static{
        
        try {        
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(jdbcService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConn() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost/oumarket", "root", "Hoang2002");
    }
    
}
