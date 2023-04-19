import com.lqd.pojo.Category;
import com.lqd.pojo.Customer;
import com.lqd.pojo.User;
import com.lqd.services.CustomerService;
import com.lqd.services.UserService;
import com.lqd.services.jdbcService;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserTester {
    private static Connection conn;
    private static UserService userService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        userService= new UserService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm danh sách nguòi dùng với từ khóa")
    public void testGetUsers() throws SQLException {
        List<User> retrievedUsers = userService.getUsers("Hoàng");
        Assertions.assertNotNull(retrievedUsers);
        Assertions.assertEquals(1, retrievedUsers.size());
    }
    @Test
    @DisplayName("Kiểm tra thêm người dùng")
    public void testAddUser() throws SQLException {
        // Tạo đối tượng người dùng mới
        User user = new User( "Nguyễn Văn A", Date.valueOf("2002 - 12 - 06"),"Nam", "0909123456", "123 ABC St.", "staff", "nguyenvana@gmail.com", "nguyenvana", "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=", "71c471a5-ce70-45bc-8b48-e3fbf5e78070");

        // Thêm người dùng mới vào cơ sở dữ liệu
        boolean added = userService.addUser(user);
        Assertions.assertTrue(added);

        // Lấy người dùng theo id
        User addedUser = getUserById(user.getId());

        // Kiểm tra xem người dùng đã được thêm vào cơ sở dữ liệu hay chưa
        Assertions.assertNotNull(addedUser);

        // Kiểm tra xem thông tin người dùng đã được thiết lập đúng hay không
        Assertions.assertEquals(user.getName(), addedUser.getName());
        Assertions.assertEquals(user.getSex(), addedUser.getSex());
        Assertions.assertEquals(user.getPhoneNumber(), addedUser.getPhoneNumber());
        Assertions.assertEquals(user.getAddress(), addedUser.getAddress());
        Assertions.assertEquals(user.getRole(), addedUser.getRole());
        Assertions.assertEquals(user.getEmail(), addedUser.getEmail());
        Assertions.assertEquals(user.getUsername(), addedUser.getUsername());
        Assertions.assertEquals(user.getPassword(), addedUser.getPassword());
        Assertions.assertEquals(user.getBranchID(), addedUser.getBranchID());
        Assertions.assertEquals(user.getDateOfBirth(),addedUser.getDateOfBirth());
    }

    @Test
    @DisplayName("Kiểm tra cập nhật người dùng")
    public void testUpdateUser() throws SQLException {
        // Tạo đối tượng người dùng mới
        User user = new User("Nguyễn Văn B",  Date.valueOf("2002 - 12 - 06"), "Nam", "0909123457", "123 ABC St.", "staff", "nguyenvanb@gmail.com", "nguyenvanb", "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=", "71c471a5-ce70-45bc-8b48-e3fbf5e78070");

        // Thêm người dùng mới vào cơ sở dữ liệu
        userService.addUser(user);

        // Cập nhật thông tin người dùng
        user.setName("Nguyễn Văn Bảo");
        user.setAddress("456 XYZ St.");
        user.setPhoneNumber("0987654321");
        user.setEmail("nguyenvanb@gmail.com");
        boolean updated = userService.updateUser(user);
        Assertions.assertTrue(updated);

        // Lấy thông tin người dùng theo id
        User updatedUser = getUserById(user.getId());

        // Kiểm tra xem thông tin người dùng đã được cập nhật hay chưa
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(user.getName(), updatedUser.getName());
        Assertions.assertEquals(user.getSex(), updatedUser.getSex());
        Assertions.assertEquals(user.getPhoneNumber(), updatedUser.getPhoneNumber());
        Assertions.assertEquals(user.getAddress(), updatedUser.getAddress());
        Assertions.assertEquals(user.getRole(), updatedUser.getRole());
        Assertions.assertEquals(user.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(user.getUsername(), updatedUser.getUsername());
        Assertions.assertEquals(user.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(user.getBranchID(), updatedUser.getBranchID());
        Assertions.assertEquals(user.getDateOfBirth(), updatedUser.getDateOfBirth());
    }

    @Test
    @DisplayName("Kiểm tra xóa người dùng")
    public void testDeleteUser() throws SQLException {
        // Tạo một người dùng mới để xóa
        User user = new User("Nguyễn Văn C",  Date.valueOf("2002 - 12 - 06"),"Nam", "0909123465", "123 ABC St.", "staff", "nguyenvanc@gmail.com", "nguyenvanc", "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=", "71c471a5-ce70-45bc-8b48-e3fbf5e78070");
        userService.addUser(user);

        // Xóa người dùng theo id
        boolean deleted = userService.deleteUser(user.getId());
        Assertions.assertTrue(deleted);

        // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu hay không
        User deletedUser = getUserById(user.getId());
        Assertions.assertNull(deletedUser);
    }

    @Test
    @DisplayName("Kiểm tra đăng nhập")
    public void testCheckUser() throws SQLException {
        // Tạo tài khoản người dùng mới
        User user = new User("Nguyễn Văn D",  Date.valueOf("2002 - 12 - 06"),"Nam", "0909123423", "123 ABC St.", "staff", "nguyenvand@gmail.com", "nguyenvand", "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=", "71c471a5-ce70-45bc-8b48-e3fbf5e78070");
        userService.addUser(user);

        // Kiểm tra đăng nhập với tài khoản đã tạo
        User loggedUser = userService.checkUser(user.getUsername(), user.getPassword());
        Assertions.assertNotNull(loggedUser);

        // Kiểm tra xem thông tin người dùng đã được thiết lập đúng hay không
        Assertions.assertEquals(user.getName(), loggedUser.getName());
        Assertions.assertEquals(user.getSex(), loggedUser.getSex());
        Assertions.assertEquals(user.getPhoneNumber(), loggedUser.getPhoneNumber());
        Assertions.assertEquals(user.getAddress(), loggedUser.getAddress());
        Assertions.assertEquals(user.getRole(), loggedUser.getRole());
        Assertions.assertEquals(user.getEmail(), loggedUser.getEmail());
        Assertions.assertEquals(user.getUsername(), loggedUser.getUsername());
        Assertions.assertEquals(user.getPassword(), loggedUser.getPassword());
        Assertions.assertEquals(user.getBranchID(), loggedUser.getBranchID());
        Assertions.assertEquals(user.getDateOfBirth(), loggedUser.getDateOfBirth());

        // Kiểm tra đăng nhập với tài khoản không tồn tại
        loggedUser = userService.checkUser("nonexistentuser", "invalidpassword");
        Assertions.assertNull(loggedUser);

        // Kiểm tra đăng nhập với mật khẩu sai
        loggedUser = userService.checkUser(user.getUsername(), "invalidpassword");
        Assertions.assertNull(loggedUser);
    }
    public User getUserById(String id) throws SQLException {
        try (Connection conn = jdbcService.getConn()) {
            String sql = "SELECT * FROM user WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDate("dateofbirth"),
                        rs.getString("sex"),
                        rs.getString("phonenumber"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("branchID")
                );
                return user;
            } else {
                return null;
            }
        }
    }
    @Test
    public void testAddDuplicate(){
        User user = new User("Nguyễn Văn E",  Date.valueOf("2002 - 12 - 06"),"Nam", "0909123423", "123 ABC St.", "staff", "nguyenvand@gmail.com", "nguyenvand", "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=", "71c471a5-ce70-45bc-8b48-e3fbf5e78070");


        try {
            boolean actual = userService.addUser(user);
            Assertions.assertTrue(actual);

            Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> userService.addUser(user));

            String sql = "DELETE  FROM user WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, user.getId());

            Assertions.assertEquals(1,stm.executeUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
