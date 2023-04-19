/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.lqd.pojo.Promotion;
import com.lqd.services.PromotionService;
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
public class PromotionTester {
    private static Connection conn;
    private static PromotionService s;
    private static final Promotion promo = new Promotion(Date.valueOf("2023-04-18"),Date.valueOf("2023-04-18"),3000,"aa0649d8-a32e-45eb-b059-4f3a84484352");

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new PromotionService();
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
    public void testAddPromotion() {

        try {
            boolean actual = s.addPromotion(promo);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM promotion WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, promo.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("aa0649d8-a32e-45eb-b059-4f3a84484352", rs.getString("productID"));

            s.deletePromotion(promo.getId());
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testGetPromotions(){
        try {
            List<Promotion> promotions = s.getPromotions();
            Assertions.assertEquals(8, promotions.size());

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test public void testGetNewPrice(){
        try {
            Float newPrice = s.getNewPrice("aa0649d8-a32e-45eb-b059-4f3a84484352");
            Assertions.assertEquals(3000,newPrice);

            newPrice = s.getNewPrice("asd");
            Assertions.assertNull(newPrice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


  @Test
    public void testUpdateProduct(){
        try {
            Promotion testPromo = new Promotion("4b0b7d7b-8255-44dc-9618-33e07d7f5271", Date.valueOf("2023-04-18"),Date.valueOf("2023-04-18"),3000,"aa0649d8-a32e-45eb-b059-4f3a84484352");

            boolean actual = s.updatePromotion(testPromo);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM promotion WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, testPromo.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals(3000, rs.getFloat("newprice"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testDelete() {

        try {
            boolean actual = s.deletePromotion("promoid");
            Assertions.assertFalse(actual);

            Promotion testPromo = new Promotion("testID", Date.valueOf("2023-04-18"),Date.valueOf("2023-04-18"),3000,"aa0649d8-a32e-45eb-b059-4f3a84484352");
            s.addPromotion(testPromo);
            actual = s.deletePromotion("testID");
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM product WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, testPromo.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
