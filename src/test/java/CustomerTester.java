import com.lqd.pojo.Branch;
import com.lqd.pojo.Customer;
import com.lqd.services.BranchService;
import com.lqd.services.CustomerService;
import com.lqd.services.jdbcService;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerTester {
    private static Connection conn;
    private static CustomerService customerService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        customerService= new CustomerService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm khách hàng với từ khóa sđt")
    public void testGetCustomers() throws SQLException {
        List<Customer> retrievedCustomers = customerService.getCustomersByPhoneNumber("01232");
        Assertions.assertNotNull(retrievedCustomers);
        Assertions.assertEquals(2, retrievedCustomers.size());
    }


    @Test
    @DisplayName("Kiểm tra thêm khách hàng")
    public void testAddCustomer() throws SQLException {
        // Tạo đối tượng khách hàng mới
        Customer customer = new Customer("Ngọc Trinh", new Date(2002 - 12 - 05), "Nữ", "0362400302", "ngọctrinh@gmail.com");

        // Thêm khách  mới vào cơ sở dữ liệu
        boolean added = customerService.addCustomer(customer);
        Assertions.assertTrue(added);

        // Lấy khách hàng theo id
        Customer retrievedCustomer = getCustomerById(customer.getId());

        Assertions.assertNotNull(retrievedCustomer);

        // Kiểm tra xem thông tin khách hàng đã được lấy đúng hay chưa
        Assertions.assertEquals(customer.getId(), retrievedCustomer.getId());
        Assertions.assertEquals(customer.getName(), retrievedCustomer.getName());
        Assertions.assertEquals(customer.getSex(), retrievedCustomer.getSex());
        Assertions.assertEquals(customer.getPhoneNumber(), retrievedCustomer.getPhoneNumber());
        Assertions.assertEquals(customer.getEmail(), retrievedCustomer.getEmail());
    }
    @Test
    @DisplayName("Kiểm tra sửa khách hàng")
    public void testUpdateCustomer() throws SQLException {
        Customer customer = new Customer("Ngọc Trinh", new Date(2002 - 12 - 06), "Nữ", "0362400203", "ngoctrinh2@gmail.com");

        // Thêm khách hàng mới vào cơ sở dữ liệu
        boolean added = customerService.addCustomer(customer);
        Assertions.assertTrue(added);

        // Sửa thông tin khách hàng
        Customer updatedCus= new Customer(customer.getId(),"Ngọc Trinh1", new Date(2002 - 12 - 05), "Nữ", "0362400233", "ngoctrinh02@gmail.com");
        boolean updated = customerService.updateCustomer(updatedCus);
        Assertions.assertTrue(updated);

        // Kiểm tra xem thông tin khách hàng đã được sửa trong cơ sở dữ liệu hay chưa
        Customer retrievedCustomer = getCustomerById(customer.getId());
        Assertions.assertEquals(updatedCus.getId(), retrievedCustomer.getId());
        Assertions.assertEquals(updatedCus.getName(), retrievedCustomer.getName());
        Assertions.assertEquals(updatedCus.getSex(), retrievedCustomer.getSex());
        Assertions.assertEquals(updatedCus.getPhoneNumber(), retrievedCustomer.getPhoneNumber());
        Assertions.assertEquals(updatedCus.getEmail(), retrievedCustomer.getEmail());
    }

    @Test
    @DisplayName("Kiểm tra xóa khách hàng")
    public void testDeleteBranch() throws SQLException {

        Customer customer = new Customer("abcabc", new Date(2002 - 12 - 05), "Nữ", "03624002323", "ngoctrinh23@gmail.com");

        // Thêm khách hàng mới vào cơ sở dữ liệu
        boolean added = customerService.addCustomer(customer);
        Assertions.assertTrue(added);

        // Xóa chi nhánh
        boolean deleted = customerService.deleteCustomer(customer.getId());
        Assertions.assertTrue(deleted);

        // Kiểm tra xem chi nhánh đã bị xóa khỏi cơ sở dữ liệu hay chưa
        Customer retrievedCustomer = getCustomerById(customer.getId());
        Assertions.assertNull(retrievedCustomer);
    }
    public Customer getCustomerById(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT * FROM customer WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Customer c = new Customer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDate("dateofbirth"),
                        rs.getString("sex"),
                        rs.getString("phonenumber"),
                        rs.getString("email"));
                return c;
            } else {
                return null;
            }
        }
    }
}
