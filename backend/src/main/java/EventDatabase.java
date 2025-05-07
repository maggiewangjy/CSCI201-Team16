import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import java.sql.Timestamp;

public class EventDatabase {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CSCI201-Team16";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
  
    private static void closeResources(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
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

    public static void removeAttendee (int eventID, String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM Attendance WHERE eventID = ? AND email = ?");
            ps.setInt(1, eventID);
            ps.setInt(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    public void deleteEvent(int eventID) 
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

    public void updateEvent(int eventID, String date, Timestamp startTime, Timestamp endTime, String location, String agenda) 
    {
        Connection conn = null;
        PreparedStatement ps = null;

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

    public String displayDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event: ").append(name).append("\n")
          .append("Date: ").append(date).append("\n")
          .append("Start Time: ").append(startTime).append("\n")
          .append("End Time: ").append(endTime).append("\n")
          .append("Agenda: ").append(agenda).append("\n")
        sb.append("Attendees:\n");
        for (User m : attendees) sb.append("- ").append(m.getName()).append("\n"); // this needs to be changed with new attendance
        return sb.toString();
    }
}}