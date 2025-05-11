import java.sql.*;
import java.util.ArrayList;

public class AttendancesDatabaseUtil {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ClubEventsDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

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

    public static void addAttendee(int eventID, User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO Attendance (name, email, eventID) VALUES (?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, eventID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public static void removeAttendee(int eventID, User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM Attendance WHERE eventID = ? AND email = ?");
            ps.setInt(1, eventID);
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    public static int getAttendeeCountForEvent(int eventID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT COUNT(attendanceID) AS totalAttendees FROM Attendance WHERE eventID = ?");
            ps.setInt(1, eventID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalAttendees");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
        return -1; // error
    }

    public static ArrayList<String> getAllAttendeesForEvent(int eventID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> attendeeNames = new ArrayList<String>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT name FROM Attendance WHERE eventID = ?");
            ps.setInt(1, eventID);
            rs = ps.executeQuery();
            while (rs.next()) {
                attendeeNames.add(rs.getString("name"));
            }
            return attendeeNames;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
        return null; // error
    }

    public static int getAllEventsforAttendee(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT COUNT(attendanceID) AS totalEventsAttended FROM Attendance WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalEventsAttended");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
        return -1; // error
    }
}
