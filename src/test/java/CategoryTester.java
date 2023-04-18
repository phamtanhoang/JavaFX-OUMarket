
import com.lqd.pojo.Category;
import com.lqd.services.CategoryService;
import com.lqd.services.jdbcService;
import java.sql.Connection;
import java.sql.SQLException;
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
 *
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
    public void testGetCategorybyId(){
            try {
                Category cate = s.getCategoryByID("1");
                Assertions.assertEquals("Dầu Ăn",cate.getName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }
}
