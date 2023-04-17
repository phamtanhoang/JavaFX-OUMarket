/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.Receipt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Receipt> getReceipts() throws SQLException {
        List<Receipt> results = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from receipt";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Receipt r = new Receipt(rs.getString("id"),
                        rs.getDate("createddate"),
                        rs.getFloat("temptotal"),
                        rs.getFloat("promotiontotal"),
                        rs.getFloat("birthday"),
                        rs.getFloat("total"),
                        rs.getString("staffID"),
                        rs.getString("customerID"));
                results.add(r);
            }
        }
        return results;
    }
    public String getStaffName(Receipt r) throws SQLException {
        String staffName=null;
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT u.name From receipt r join user u on r.staffID = u.id where r.staffID = ? ";

            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1,r.getStaffID());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                staffName = rs.getString("name");
            }
        }
        return staffName;
    }
    public String getCustomerName(Receipt r) throws SQLException {
        String cusName=null;
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT c.name From receipt r join customer c on r.customerID = c.id where r.customerID = ? ";

            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1,r.getCustomerID());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                cusName = rs.getString("name");
            }
        }
        return cusName;
    }
    public String getBranchAddress(Receipt r)throws SQLException{
        String branchAddress=null;
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT b.name From receipt r join user u on r.staffID = u.id join branch b on u.branchID = b.id where r.staffID = ? ";

            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1,r.getCustomerID());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                branchAddress = rs.getString("name");
            }
        }
        return branchAddress;
    }

    public boolean deleteReceipt(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "DELETE FROM receipt WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            return stm.executeUpdate() > 0;
        }
    }
}
