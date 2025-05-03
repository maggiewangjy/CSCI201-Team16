import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        
    }

    public static void insertUser(String email, String password, String securityQuestion1, String securityAnswer1, String securityQuestion2, String securityAnswer2) {
        String sql = "INSERT INTO users (email, password, securityQuestion1, securityAnswer1, securityQuestion2, securityAnswer2) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, securityQuestion1);
            pstmt.setString(4, securityAnswer1);
            pstmt.setString(5, securityQuestion2);
            pstmt.setString(6, securityAnswer2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userID");
            }
            else{
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean updatePassword(String email, String newPassword, String securityAnswer1, String securityAnswer2) {
        String sql = "SELECT * FROM users WHERE email = ? AND securityAnswer1 = ? AND securityAnswer2 = ?";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, securityAnswer1);
            pstmt.setString(3, securityAnswer2);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sql = "UPDATE users SET password = ? WHERE email = ?";
                PreparedStatement pstmt2 = conn.prepareStatement(sql);
                pstmt2.setString(1, newPassword);
                pstmt2.setString(2, email);
                pstmt2.executeUpdate();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
    
