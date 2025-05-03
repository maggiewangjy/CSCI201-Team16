import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/ClubEventsDB";
    private static final String USER = "root";
    private static final String PASSWORD = "zhuxiaoyuan2003";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(String email, String password, String securityQuestion1, String securityAnswer1, String securityQuestion2, String securityAnswer2) throws SQLException {
        String sql = "INSERT INTO Users (name, password, role, securityQuestion1, securityAnswer1, securityQuestion2, securityAnswer2) VALUES (?, ?, 'admin', ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email); // Using email as name for now
            pstmt.setString(2, password);
            pstmt.setString(3, securityQuestion1);
            pstmt.setString(4, securityAnswer1);
            pstmt.setString(5, securityQuestion2);
            pstmt.setString(6, securityAnswer2);
            pstmt.executeUpdate();
        } finally {
            close(conn, pstmt, null);
        }
    }

    public static int getUser(String email, String password) {
        String sql = "SELECT * FROM Users WHERE name = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userID");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public static boolean updatePassword(String email, String newPassword, String securityAnswer1, String securityAnswer2) {
        String sql = "SELECT * FROM Users WHERE name = ? AND securityAnswer1 = ? AND securityAnswer2 = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, securityAnswer1);
            pstmt.setString(3, securityAnswer2);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sql = "UPDATE Users SET password = ? WHERE name = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
                    pstmt2.setString(1, newPassword);
                    pstmt2.setString(2, email);
                    pstmt2.executeUpdate();
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, pstmt, rs);
        }
    }
}
    
