/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.ProductPromotion;
import com.lqd.pojo.Promotion;
import com.lqd.pojo.Receipt;
import com.lqd.pojo.ReceiptDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public String getProductName(ReceiptDetail r) throws SQLException {
        String productName=null;
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT p.name From receipt_detail r join product p on r.productID = p.id  where r.productID = ?";

            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1,r.getProductID());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                productName = rs.getString("name");
            }
        }
        return productName;
    }
    public List<ReceiptDetail> getReceiptDetails(Receipt r) throws SQLException {
        List<ReceiptDetail> results = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from receipt_detail where receiptID = ?";

            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1,r.getId());
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                ReceiptDetail receiptDetail = new ReceiptDetail(rs.getFloat("quantity"),
                        rs.getString("productID"),
                        rs.getString("receiptID"));
                results.add(receiptDetail);
            }
        }

        return results;
    }
}
