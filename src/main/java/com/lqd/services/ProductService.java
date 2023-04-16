/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gol
 */
public class ProductService {

    public boolean addProduct(Product p) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("Insert into product(id,name,unit,price,origin,categoryID)Values(?,?,?,?,?,?)");
            stm1.setString(1, p.getId());
            stm1.setString(2, p.getName());
            stm1.setString(3, p.getUnit());
            stm1.setFloat(4, p.getPrice());
            stm1.setString(5, p.getOrigin());
            stm1.setString(6, p.getCategoryID());
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

    public List<Product> getProducts(String kw) throws SQLException {
        List<Product> results = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from product";
            if (kw != null && !kw.isEmpty()) {
                sql += " where UPPER(name) like concat('%',UPPER(?),'%')";
            }
            sql += " ORDER BY name";
            PreparedStatement stm = conn.prepareCall(sql);

            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getString("categoryID"));
                results.add(p);
            }
        }
        return results;
    }

    public Product getProductbyName(String kw) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from product";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE name = concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);

            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getString("categoryID"));
                return p;
            }
            return null;
        }
    }
       public Product getProductbyID(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from product";
            if (id != null && !id.isEmpty()) {
                sql += " WHERE id =?";
            }

            PreparedStatement stm = conn.prepareCall(sql);

            if (id != null && !id.isEmpty()) {
                stm.setString(1, id);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getString("categoryID"));

                return p;
            }

            return null;
        }
    }

    public boolean updateProduct(Product p) throws SQLException {

        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            String sql = "Update product set name=?,unit=?,price=?,origin=?,categoryID=? where id=?  ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setString(2, p.getUnit());
            stm.setFloat(3, p.getPrice());
            stm.setString(4, p.getOrigin());
            stm.setString(5, p.getCategoryID());
            stm.setString(6, p.getId());
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

    public boolean deleteProduct(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "DELETE FROM product WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }
    
}

