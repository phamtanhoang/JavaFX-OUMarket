/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.ProductPromotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Gol
 */
public class ReceiptDetailService {
    public boolean addReceiptDetail(ProductPromotion pp,String receiptID) throws SQLException{
        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("Insert into receipt_detail(quantity,receiptID,productID)Values(?,?,?)");
            stm1.setFloat(1, pp.getQuantity());
            stm1.setString(2, receiptID);
            stm1.setString(3, pp.getId());
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
