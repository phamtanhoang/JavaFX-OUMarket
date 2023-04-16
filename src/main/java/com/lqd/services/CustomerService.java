/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.Customer;
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
public class CustomerService {

    public List<Customer> getCustomersByPhoneNumber(String kw) throws SQLException {
          List<Customer> results = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from customer";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE phonenumber like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);

            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Customer c = new Customer(rs.getString("id"),
                        rs.getString("name"),
                        rs.getDate("dateofbirth"),
                        rs.getString("sex"),
                        rs.getString("phonenumber"),
                        rs.getString("email"));
                results.add(c);
            }
        }

        return results;
    }

 
}
