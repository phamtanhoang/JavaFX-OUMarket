import com.lqd.pojo.Branch;
import com.lqd.services.BranchService;
import com.lqd.services.jdbcService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BranchTester {

    private static Connection conn;
    private static BranchService branchService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = jdbcService.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        branchService = new BranchService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm danh sách chi nhánh với từ khóa")
    public void testGetBranchs() throws SQLException {
        List<Branch> retrievedBranchs = branchService.getBranchs("oumarket");
        Assertions.assertNotNull(retrievedBranchs);
        Assertions.assertEquals(4, retrievedBranchs.size());
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm chi nhánh theo ID")
    public void testGetBranchByID() throws SQLException {
        // Tìm kiếm chi nhánh theo ID
        Branch retrievedBranch = branchService.getBranchByID("0210ea9c-7811-417f-945e-5f6478e23fa3");
        Assertions.assertNotNull(retrievedBranch);
        Assertions.assertEquals("OUMarket Âu Cơ", retrievedBranch.getName());
        Assertions.assertEquals("254/32 Âu Cơ", retrievedBranch.getAdress());
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm chi nhánh theo tên")
    public void testGetBranchByName() throws SQLException {
        // Tìm kiếm chi nhánh theo tên
        Branch retrievedBranch = branchService.getBranchByName("OUMarket Âu Cơ");
        Assertions.assertNotNull(retrievedBranch);
        Assertions.assertEquals("0210ea9c-7811-417f-945e-5f6478e23fa3", retrievedBranch.getId());
        Assertions.assertEquals("254/32 Âu Cơ", retrievedBranch.getAdress());
    }

    @Test
    @DisplayName("Kiểm tra thêm chi nhánh")
    public void testAddBranch() throws SQLException {
        // Tạo đối tượng chi nhánh mới
        Branch branch = new Branch( "Branch 001", "123 Main St.");

        // Thêm chi nhánh mới vào cơ sở dữ liệu
        boolean added = branchService.addBranch(branch);
        Assertions.assertTrue(added);

        // Lấy chi nhánh theo ID
        Branch addedBranch = branchService.getBranchByID(branch.getId());
        // Kiểm tra xem chi nhánh đã được thêm vào cơ sở dữ liệu hay chưa
        Assertions.assertNotNull(addedBranch);
        // Kiểm tra xem ID của chi nhánh đã được thiết lập đúng hay không
        Assertions.assertEquals(branch.getId(), addedBranch.getId());
        Assertions.assertEquals(branch.getName(), addedBranch.getName());
        Assertions.assertEquals(branch.getAdress(), addedBranch.getAdress());
    }
    @Test
    @DisplayName("Kiểm tra sửa chi nhánh")
    public void testUpdateBranch() throws SQLException {
        Branch branch = new Branch("Branch 002", "654 Main St.");

        // Thêm chi nhánh mới vào cơ sở dữ liệu
        boolean added = branchService.addBranch(branch);
        Assertions.assertTrue(added);

        // Sửa thông tin chi nhánh
        Branch updatedBranch = new Branch(branch.getId(), "Branch 2", "456 Second St.");
        boolean updated = branchService.updateBranch(updatedBranch);
        Assertions.assertTrue(updated);

        // Kiểm tra xem thông tin chi nhánh đã được sửa trong cơ sở dữ liệu hay chưa
        Branch retrievedBranch = branchService.getBranchByID(branch.getId());
        Assertions.assertEquals(updatedBranch.getId(), retrievedBranch.getId());
        Assertions.assertEquals(updatedBranch.getName(), retrievedBranch.getName());
        Assertions.assertEquals(updatedBranch.getAdress(), retrievedBranch.getAdress());
    }

    @Test
    @DisplayName("Kiểm tra xóa chi nhánh")
    public void testDeleteBranch() throws SQLException {
        Branch branch = new Branch( "Branch 003", "789 Main St.");

        // Thêm chi nhánh mới vào cơ sở dữ liệu
        boolean added = branchService.addBranch(branch);
        Assertions.assertTrue(added);
        // Xóa chi nhánh
        boolean deleted = branchService.deleteBranch(branch.getId());
        Assertions.assertTrue(deleted);

        // Kiểm tra xem chi nhánh đã bị xóa khỏi cơ sở dữ liệu hay chưa
        Branch retrievedBranch = branchService.getBranchByID(branch.getId());
        Assertions.assertNull(retrievedBranch);
    }
}
