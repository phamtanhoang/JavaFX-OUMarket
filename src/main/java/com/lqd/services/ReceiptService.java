/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.Receipt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Gol
 */
public class ReceiptService {

    public boolean addReceipt(Receipt r) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("Insert into receipt(id,createddate,temptotal,promotiontotal,birthday,total,staffID,customerID)Values(?,?,?,?,?,?,?,?)");
            stm1.setString(1, r.getId());
            stm1.setDate(2, r.getCreatedDate());
            stm1.setFloat(3, r.getTempTotal());
            stm1.setFloat(4, r.getPromotionTotal());
            stm1.setFloat(5, r.getBirthDay());
            stm1.setFloat(6, r.getTotal());
            stm1.setString(7, r.getStaffID());
            stm1.setString(8, r.getCustomerID());

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
}
