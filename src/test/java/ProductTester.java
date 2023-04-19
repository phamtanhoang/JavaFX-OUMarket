/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.lqd.pojo.Category;
import com.lqd.pojo.Product;
import com.lqd.pojo.Promotion;
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
        Product prod = new Product("Bánh ngọt","Bịch",3,"Việt Nam","10");

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
        Product prod = new Product("testProduct","Bánh ngọt","Bịch",3,"Việt Nam","10");

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

            sql = "DELETE  FROM product WHERE id=?";
            stm = conn.prepareCall(sql);
            stm.setString(1, prod.getId());

            Assertions.assertEquals(1,stm.executeUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testGetProductById(){
        Product prod = new Product("testProduct","Bánh ngọt","Bịch",3,"Việt Nam","10");


        try {
            s.getProductbyID(prod.getId());
            Assertions.assertEquals("Bánh ngọt",prod.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateProduct(){
        Product prod = new Product("testProduct","Bánh ngọt","Bịch",3,"Việt Nam","10");


        try {
            boolean actual = s.addProduct(prod);
            Assertions.assertTrue(actual);

            prod.setName("Bánh mặn");
            actual = s.updateProduct(prod);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM product WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, prod.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Bánh mặn", rs.getString("name"));

            sql = "DELETE  FROM product WHERE id=?";
            stm = conn.prepareCall(sql);
            stm.setString(1, prod.getId());

            Assertions.assertEquals(1,stm.executeUpdate());
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
        Product prod = new Product("testProduct","Bánh ngọt","Bịch",3,"Việt Nam","10");



        try {
            boolean actual = s.addProduct(prod);
            Assertions.assertTrue(actual);

            actual = s.deleteProduct(prod.getId());
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
@Test
    public void testProductNotNull() {
        try {
            List<Product> products = s.getProducts(null);
            long r = products.stream().filter(c -> c.getName() == null).count();
            Assertions.assertTrue(r == 0);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testAddDuplicate(){
        Product prod = new Product("testProduct","Bánh ngọt","Bịch",3,"Việt Nam","10");

        try {
            boolean actual = s.addProduct(prod);
            Assertions.assertTrue(actual);

            Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> s.addProduct(prod));
            String sql = "DELETE  FROM product WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, prod.getId());

            Assertions.assertEquals(1,stm.executeUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
