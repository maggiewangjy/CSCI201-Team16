import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDatabase {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CSCI201-Team16"; // edit based on your url path
    private static final String DB_USER = "root"; // your username
    private static final String DB_PASSWORD = "root"; // your password
    

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
    
    public static void closeResources(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        }
    }

    // Add to-do
    public void addToDo(String task) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO Agenda (eventID, task, completion) VALUES (?, ?, false)";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eventID);
            ps.setString(2, task);
            ps.executeUpdate();
            toDo.add(task);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public void removeToDo(String task) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "DELETE FROM Agenda WHERE eventID = ? AND task = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eventID);
            ps.setString(2, task);
            ps.executeUpdate();
            toDo.remove(task);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public void addAttendee(User member) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO Participants (userID, name, email, status, eventID) VALUES (?, ?, ?, 'confirmed', ?)";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, member.getUserID());
            ps.setString(2, member.getName());
            ps.setString(3, member.getEmail());
            ps.setInt(4, eventID);
            ps.executeUpdate();
            attendees.add(member);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public void removeAttendee(User member) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "DELETE FROM Participants WHERE eventID = ? AND userID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eventID);
            ps.setInt(2, member.getUserID());
            ps.executeUpdate();
            attendees.remove(member);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public void modifyNotes(String newNotes) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "UPDATE Events SET notes = ? WHERE eventID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newNotes);
            ps.setInt(2, eventID);
            ps.executeUpdate();
            this.notes = newNotes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public static Event getEventByID(int eventID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Event event = null;

        String sql = "SELECT * FROM Events WHERE eventID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eventID);
            rs = ps.executeQuery();
            if (rs.next()) {
                event = new Event(
                    rs.getInt("eventID"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    rs.getString("notes")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
        
        return event;
    }

    public static List<Event> getEventsByDate(LocalDate date) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();

        String sql = "SELECT * FROM Events WHERE date = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(date));
            rs = ps.executeQuery();
            
            while(rs.next()) {
                events.add(new Event(
                    rs.getInt("eventID"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    rs.getString("notes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
        
        return events;
    }

    public String displayDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event: ").append(name).append("\n")
          .append("Date: ").append(date).append("\n")
          .append("Time: ").append(time).append("\n")
          .append("Notes: ").append(notes).append("\n")
          .append("To-Do:\n");
        for (String task : toDo) sb.append("- ").append(task).append("\n");
        sb.append("Attendees:\n");
        for (User m : attendees) sb.append("- ").append(m.getName()).append("\n");
        return sb.toString();
    }

    public void loadToDos() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT task FROM Agenda WHERE eventID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eventID);
            rs = ps.executeQuery();
            while (rs.next()) {
                toDo.add(rs.getString("task"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    public void loadAttendees() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT userID, name, email FROM Participants WHERE eventID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eventID);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    rs.getString("email")
                );
                attendees.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

   
}