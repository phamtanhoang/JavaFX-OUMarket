/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.lqd.pojo.ProductPromotion;
import com.lqd.pojo.Receipt;
import com.lqd.pojo.ReceiptDetail;
import com.lqd.services.ReceiptDetailService;
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
public class ReceiptDetailTester {
    private static Connection conn;
    private static ReceiptService receiptService;
    private static ReceiptDetailService receiptDetailService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        receiptDetailService = new ReceiptDetailService();

    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testAddReceiptDetail() {
        try {
            Receipt testReceipt = new Receipt("6a8e7e74-160e-49a6-81aa-67bcfa06a0f0",Date.valueOf("2023-4-18"),3,3,0,3,"64acd540-7c61-4dad-9e8f-6efba1343652","1");

            ProductPromotion productPromotion = new ProductPromotion("aa0649d8-a32e-45eb-b059-4f3a84484352",3f);
            boolean actual = receiptService.addReceipt(testReceipt);
            Assertions.assertTrue(actual);
            actual = receiptDetailService.addReceiptDetail(productPromotion,testReceipt.getId());
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM receipt_detail WHERE receiptID=? and productID =?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, testReceipt.getId());
            stm.setString(2,productPromotion.getId());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("aa0649d8-a32e-45eb-b059-4f3a84484352", rs.getString("productID"));

            sql = "Delete  FROM receipt WHERE id=?";
            stm = conn.prepareCall(sql);
            stm.setString(1, testReceipt.getId());

            stm.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testGetProductName(){

        try {
             ReceiptDetail receiptDetail = new ReceiptDetail(3f,"aa0649d8-a32e-45eb-b059-4f3a84484352","4781a975-ed8b-48af-9c6e-dddb79b8679d");

            receiptDetail.setProductID(receiptDetailService.getProductName(receiptDetail));
            Assertions.assertEquals("Bột gặt Omo",receiptDetail.getProductID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testGetReceiptDetail(){
        try {
            Receipt testReceipt = new Receipt("744e7cc1-ef8c-4a71-8b45-adde54884ab6",Date.valueOf("2023-4-18"),3,3,0,3,"64acd540-7c61-4dad-9e8f-6efba1343652","1");

            List<ReceiptDetail> receiptDetails = receiptDetailService.getReceiptDetails(testReceipt);
            Assertions.assertEquals(5, receiptDetails.size());

        } catch (SQLException ex) {
            Logger.getLogger(ReceiptDetailTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
