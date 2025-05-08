import java.sql.*;

public class AttendancesDatabaseUtil {


    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CSCI201-Team16";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    public static void closeResources(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void registerMember(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO attendance (eventID, email, name) VALUES (?, ?, ?)");
            // add info
            rs = ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources(rs, ps, conn);
        }
    }
}
