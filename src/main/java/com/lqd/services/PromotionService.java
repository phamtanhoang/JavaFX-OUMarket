/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.Product;
import com.lqd.pojo.Promotion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gol
 */
public class PromotionService {

    public boolean addPromotion(Promotion p) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("Insert into promotion(id,fromdate,todate,newprice,productID)Values(?,?,?,?,?)");
            stm1.setString(1, p.getId());
            stm1.setDate(2, p.getFromDate());
            stm1.setDate(3, p.getToDate());
            stm1.setFloat(4, p.getNewPrice());
            stm1.setString(5, p.getProductID());
            stm1.executeUpdate();
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }

    }

  public Float getNewPrice(String id) throws SQLException {
    try (Connection conn = jdbcService.getConn()) {
        String sql = "SELECT  newprice " +
                     "FROM product p " +
                     " JOIN promotion o ON p.id = o.productID " +
                     "WHERE p.id = ? AND o.fromdate <= CAST(NOW() AS DATE) AND o.todate >= CAST(NOW() AS DATE)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {

            return rs.getFloat("newprice");
        } else {
            return null;
        }
    }
}

    public List<Promotion> getPromotions() throws SQLException {
        List<Promotion> results = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from promotion";

            PreparedStatement stm = conn.prepareCall(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Date fromDate = rs.getDate("fromdate");
                Date toDate = rs.getDate("todate");

                Promotion p = new Promotion(rs.getString("id"), fromDate, toDate,
                        rs.getFloat("newprice"),
                        rs.getString("productID"));
                results.add(p);
            }
        }

        return results;
    }

    public boolean updatePromotion(Promotion p) throws SQLException {

        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            String sql = "Update promotion set fromdate=?,todate=?,newprice=?,productID=? where id=?  ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setDate(1, p.getFromDate());
            stm.setDate(2, p.getToDate());
            stm.setFloat(3, p.getNewPrice());
            stm.setString(4, p.getProductID());
            stm.setString(5, p.getId());
            stm.executeUpdate();
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
    }

    public boolean deletePromotion(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "DELETE FROM promotion WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public Promotion getPromotionByProductID(String id) throws SQLException{
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from promotion";
            if (id != null && !id.isEmpty()) {
                sql += " WHERE productid =?";
            }

            PreparedStatement stm = conn.prepareCall(sql);

            if (id != null && !id.isEmpty()) {
                stm.setString(1, id);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Promotion p = new Promotion(rs.getString("id"),
                        rs.getDate("fromdate"),
                        rs.getDate("todate"),
                        rs.getFloat("newprice"),
                        rs.getString("productID"));

                return p;
            }

            return null;
        }
    }
}
