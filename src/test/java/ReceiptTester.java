/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.lqd.pojo.Receipt;
import com.lqd.services.ReceiptService;
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
public class ReceiptTester {
    private static Connection conn;
    private static ReceiptService s;
    private static Receipt receipt;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new ReceiptService();
        receipt = new Receipt("test",Date.valueOf("2023-4-18"),3,3,0,3,"64acd540-7c61-4dad-9e8f-6efba1343652","3");

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
    public void testAddReceiptSuccessful() {
        try {
            boolean actual = s.addReceipt(receipt);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM receipt WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, receipt.getId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("test", rs.getString("id"));

            sql = "Delete  FROM receipt WHERE id=?";
            stm = conn.prepareCall(sql);
            stm.setString(1, receipt.getId());

            stm.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testGetStaffName(){

        try {
            receipt.setStaffID( s.getStaffName(receipt));
            Assertions.assertEquals("Lê Quang Đạt",receipt.getStaffID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetCustomerName(){

        try {
            Receipt testReceipt = new Receipt("3a8e7e74-160e-49a6-81aa-67bcfa06a0f0",Date.valueOf("2023-4-18"),3,3,0,3,"64acd540-7c61-4dad-9e8f-6efba1343652","1");
            testReceipt.setCustomerID( s.getCustomerName(testReceipt));
            Assertions.assertEquals("Quang Đạt",testReceipt.getCustomerID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testGetReceipt(){
        try {
            List<Receipt> receipts = s.getReceipts();
            Assertions.assertEquals(5, receipts.size());

        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
