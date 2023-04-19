/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.lqd.services;


import com.lqd.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
*
* @author Gol
*/
public class UserService {


   public boolean addUser(User e) throws SQLException {
       try (Connection conn = jdbcService.getConn()) {
           conn.setAutoCommit(false);
           PreparedStatement stm1 = conn.prepareStatement("Insert into user(id,name,dateofbirth,sex,phonenumber,address,role,email,username,password,branchID)Values(?,?,?,?,?,?,?,?,?,?,?)");
           stm1.setString(1, e.getId());
           stm1.setString(2, e.getName());
           stm1.setDate(3, e.getDateOfBirth());
           stm1.setString(4, e.getSex());
           stm1.setString(5, e.getPhoneNumber());
           stm1.setString(6,e.getAddress());
           stm1.setString(7, e.getRole());
           stm1.setString(8, e.getEmail());
           stm1.setString(9, e.getUsername());
           stm1.setString(10, e.getPassword());
           stm1.setString(11, e.getBranchID());


           stm1.executeUpdate();
           try {
               conn.commit();
               return true;
           } catch (SQLException ex) {
               System.err.println(ex.getMessage());
               return false;
           }
       }


   }


   public List<User> getUsers(String kw) throws SQLException {
       List<User> results = new ArrayList<>();
       try (Connection conn = jdbcService.getConn()) {
           String sql = "Select * from user";
           if (kw != null && !kw.isEmpty()) {
               sql += " where  UPPER(name) like concat('%',UPPER(?),'%')";
           }

           PreparedStatement stm = conn.prepareCall(sql);
           if (kw != null && !kw.isEmpty()) {
               stm.setString(1, kw);
           }
           ResultSet rs = stm.executeQuery();


           while (rs.next()) {
               User c = new User(rs.getString("id"),
                       rs.getString("name"),
                       rs.getDate("dateofbirth"),
                       rs.getString("sex"),
                       rs.getString("phonenumber"),
                       rs.getString("address"),
                       rs.getString("role"),
                       rs.getString("email"),
                       rs.getString("username"),
                       rs.getString("password"),
                       rs.getString("branchID")
               );
               results.add(c);
           }
       }


       return results;
   }

//   public User getUser(String id) throws SQLException {
//       try (Connection conn = jdbcService.getConn()) {
//           String sql = "Select * from user where name=?";
//
//           PreparedStatement stm = conn.prepareCall(sql);
//
//           stm.setString(1, id);
//           ResultSet rs = stm.executeQuery();
//           User c = new User(rs.getString("id"),
//                       rs.getString("name"),
//                       rs.getDate("dateofbirth"),
//                       rs.getString("sex"),
//                       rs.getString("phonenumber"),
//                       rs.getString("address"),
//                       rs.getString("role"),
//                       rs.getString("email"),
//                       rs.getString("username"),
//                       rs.getString("password"),
//                       rs.getString("branchID")
//           );
//           return c;
//       }
//   }

   public boolean updateUser(User c) throws SQLException {
       try (Connection conn = jdbcService.getConn()) {
           conn.setAutoCommit(false);
           String sql = "Update User set name=?, address=?, dateofbirth=?, sex=?, phonenumber=?, email=?, branchid=?, role=?, username=?, password=?  where id=?  ";
           PreparedStatement stm = conn.prepareCall(sql);
           stm.setString(1, c.getName());
           stm.setString(2, c.getAddress());
           stm.setDate(3, c.getDateOfBirth());
           stm.setString(4, c.getSex());
           stm.setString(5, c.getPhoneNumber());
           stm.setString(6, c.getEmail());
           stm.setString(7, c.getBranchID());
           stm.setString(8, c.getRole());
           stm.setString(9, c.getUsername());
           stm.setString(10, c.getPassword());
           stm.setString(11, c.getId());


           stm.executeUpdate();


           try {
               conn.commit();
               return true;
           } catch (SQLException ex) {
               System.err.println(ex.getMessage());
               return false;
           }
       }
   }


   public boolean deleteUser(String id) throws SQLException {
       try (Connection conn = jdbcService.getConn()) {
           String sql = "DELETE FROM User WHERE id=?";
           PreparedStatement stm = conn.prepareCall(sql);
           stm.setString(1, id);


           return stm.executeUpdate() > 0;
       }
   }

    public User checkUser(String username, String password) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from user where username=? and password = ?";

            PreparedStatement stm = conn.prepareCall(sql);

            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User c = new User(rs.getString("id"),
                        rs.getString("name"),
                        rs.getDate("dateofbirth"),
                        rs.getString("sex"),
                        rs.getString("phonenumber"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("branchID")
                );
                return c;
            }
        }
        return null;
    }
}

