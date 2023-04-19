/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.lqd.pojo.Product;
import com.lqd.services.ProductService;
import com.lqd.services.jdbcService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.*;
import java.sql.*;

/**
 *
 * @author admin
 */
public class ProductTester {
    private static Connection conn;
    private static  ProductService s;
    private static final Product prod = new Product("Bánh ngọt","Bịch",3,"Việt Nam","10");

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new ProductService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testAddSuccessful() {
        try {
            boolean actual = s.addProduct(prod);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM product WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, prod.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Bánh ngọt", rs.getString("name"));
            Assertions.assertEquals(10, rs.getInt("categoryID"));
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testGetProductById(){
        try {
            s.getProductbyID(prod.getId());
            Assertions.assertEquals("Bánh ngọt",prod.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateProduct(){
        try {
            prod.setUnit("Gói");

           boolean actual = s.updateProduct(prod);
            Assertions.assertTrue(actual);

//            String sql = "SELECT * FROM product WHERE id=?";
//            PreparedStatement stm = conn.prepareCall(sql);
//            stm.setString(1, prod.getId());
//
//            ResultSet rs = stm.executeQuery();
//            if(rs.next()) {
//                Assertions.assertNotNull(rs.next());
//                Assertions.assertEquals("Gói", rs.getString("unit"));
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testSearch() {
        try {
            List<Product> products = s.getProducts("Omo");
            Assertions.assertEquals(1, products.size());

            for (Product q: products)
                Assertions.assertTrue(q.getName().contains("Omo"));
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testDeleteWrongId() {
        String id = "48204dad-c33d-4a8e-a727-063f22496807";
        try {
            boolean actual = s.deleteProduct(id);
            Assertions.assertFalse(actual);

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testDelete() {
        try {
            boolean actual = s.deleteProduct(prod.getId());
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM product WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, prod.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testGetProducts(){
        try {
            List<Product> products = s.getProducts(null);
            Assertions.assertEquals(4, products.size());

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
