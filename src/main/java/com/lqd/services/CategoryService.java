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
    public List<Category> getCategories() throws SQLException {
        List<Category> cates = new ArrayList<>();
        try (Connection conn = jdbcService.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM category");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("categoryname");
                cates.add(new Category(id, name));
            }
        }
        return cates;
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


}
