
import com.lqd.pojo.Category;
import com.lqd.pojo.Product;
import com.lqd.services.CategoryService;
import com.lqd.services.jdbcService;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author admin
 */
public class CategoryTester {

    private static Connection conn;
    private static CategoryService s;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new CategoryService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testNameNotNull() {
        try {
            List<Category> cates = s.getCategories(null);
            long r = cates.stream().filter(c -> c.getName() == null).count();
            Assertions.assertTrue(r == 0);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testNameUnique() {
        try {
            List<Category> cates = s.getCategories(null);
            List<String> names = cates.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
            Set<String> testNames = new HashSet<>(names);
            Assertions.assertEquals(names.size(), testNames.size());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetCategorybyId() {
        try {
            Category cate = s.getCategoryByID("1");
            Assertions.assertEquals("Dầu Ăn", cate.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddCategory() {
        try {
            Category cate = new Category("testID", "TestCategory");

            boolean actual = s.addCategory(cate);
            Assertions.assertTrue(actual);

            PreparedStatement stm = conn.prepareCall("SELECT * FROM category WHERE id=?");
            stm.setString(1, cate.getId());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("TestCategory", rs.getString("categoryname"));

            stm = conn.prepareCall("delete from category where id = ?");
            stm.setString(1, cate.getId());
            Assertions.assertEquals(1, stm.executeUpdate());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testUpdateCategory(){
        Category cate = new Category("testID", "TestCategory");
        Category updatedCate = new Category("testID", "UpdatedCategory");
        try {
            boolean actual = false;
            actual = s.addCategory(cate);
            Assertions.assertTrue(actual);
            actual = s.updateCategory(updatedCate);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM category WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, cate.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("UpdatedCategory", rs.getString("categoryname"));

            stm = conn.prepareCall("delete from category where id = ?");
            stm.setString(1, cate.getId());
            Assertions.assertEquals(1, stm.executeUpdate());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testDeleteCategory() {
        Category cate = new Category("testID", "TestCategory");
        try {
            boolean actual = false;
            actual = s.addCategory(cate);
            Assertions.assertTrue(actual);

            actual = s.deleteCategory(cate.getId());
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM category WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, cate.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testAddDuplicate(){
        Category cate = new Category("testCategory","Test");

        try {
            boolean actual = s.addCategory(cate);
            Assertions.assertTrue(actual);

            Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> s.addCategory(cate));

            String sql = "DELETE  FROM category WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, cate.getId());

            Assertions.assertEquals(1,stm.executeUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

