import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDatabase {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ClubEventsDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
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

    public static void addEvent(Event event) 
    {
        Connection conn = null;
        PreparedStatement ps = null;

        try 
        {
            conn = getConnection(); 
            ps = conn.prepareStatement("INSERT INTO Events (name, startTime, endTime, location, agenda, date, dateMonth) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, event.getName());
            ps.setTimestamp(2, event.getStartTime());
            ps.setTimestamp(3, event.getEndTime());
            ps.setString(4, event.getLocation());
            ps.setString(5, event.getAgenda());
            ps.setString(6, event.getDate());
            ps.setString(7, event.getDateMonth());
            ps.executeUpdate();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            closeResources(null, ps, conn);
        }
    }

    public static List<Event> getEventByMonth(String month) {
        List<Event> events = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM Events WHERE dateMonth = ?");
            ps.setString(1, month);
            rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("eventID"),
                    rs.getString("name"),
                    rs.getTimestamp("startTime"),
                    rs.getTimestamp("endTime"),
                    rs.getString("location"),
                    rs.getString("agenda"),
                    rs.getString("date"),
                    rs.getString("dateMonth")
                )   ;
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }

        return events;
    }

    public static List<Event> getEventsByDate(LocalDate date) {
        List<Event> events = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM Events WHERE date = ?");
            ps.setDate(1, Date.valueOf(date));
            rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("eventID"),
                    rs.getString("name"),
                    rs.getTimestamp("startTime"),
                    rs.getTimestamp("endTime"),
                    rs.getString("agenda"),
                    rs.getString("location"),
                    rs.getString("date"),
                    rs.getString("dateMonth")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }

        return events;
    }

    public static void deleteEvent(int eventID) 
    {
        Connection conn = null;
        PreparedStatement ps = null;

        try 
        {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM Events WHERE eventID = ?");
            ps.setInt(1, eventID);
            ps.executeUpdate();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            closeResources(null, ps, conn);
        }
    }

    public static int updateEvent(int eventID, String date, Timestamp startTime, Timestamp endTime, String location, String agenda) 
    {
        Connection conn = null;
        PreparedStatement ps = null;
        // Added to track number of affected rows
        int rows = 0;

        try 
        {
            conn = getConnection();
            ps = conn.prepareStatement("UPDATE Events SET date = ?, startTime = ?, endTime = ?, location = ?, agenda = ? WHERE eventID = ?");
            ps.setString(1, date);
            ps.setTimestamp(2, startTime);
            ps.setTimestamp(3, endTime);
            ps.setString(4, location);
            ps.setString(5, agenda);
            ps.setInt(6, eventID);  
            // Updated to get number of affected rows
            rows = ps.executeUpdate();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();    
        }
        finally 
        {
            closeResources(null, ps, conn);
        }
        return rows;
    }

    public static int getTotalEvents() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT COUNT(eventID) AS totalEvents FROM Events");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalEvents");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
        return -1; // error
    }
}
