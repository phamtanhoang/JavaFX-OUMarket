/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.services;

import com.lqd.pojo.Branch;
import com.lqd.pojo.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryService {
    public List<Category> getCategories(String kw) throws SQLException {
        List<Category> results = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            String sql = "Select * from category";
            if (kw != null && !kw.isEmpty()) {
                sql += " where UPPER(categoryname) like concat('%',UPPER(?),'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category p = new Category(rs.getString("id"), rs.getString("categoryname"));
                results.add(p);
            }
        }
        return results;
    }

    public Category getCategoryByID(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT * FROM Category WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Category category = new Category(rs.getString("id"), rs.getString("categoryname"));
                return category;
            } else {
                return null;
            }
        }
    }

    public boolean deleteCategory(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "DELETE FROM category WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            return stm.executeUpdate() > 0;
        }
    }

    public boolean addCategory(Category b) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("Insert into category(id,categoryname) Values(?,?)");
            stm1.setString(1, b.getId());
            stm1.setString(2, b.getName());
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
    public boolean updateCategory(Category p) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {

            conn.setAutoCommit(false);

            String sql = "Update category set categoryname=? where id=?  ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setString(2, p.getId());
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
}
