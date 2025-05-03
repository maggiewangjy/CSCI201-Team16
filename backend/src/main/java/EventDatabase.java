import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventDatabase {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CSCI201-Team16";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
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

    public static Event getEventByID(int eventID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Event event = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM Events WHERE eventID = ?");
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
                loadToDos(event);
                loadAttendees(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }

        return event;
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
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    rs.getString("notes")
                );
                loadToDos(event);
                loadAttendees(event);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }

        return events;
    }

    public static void addToDo(int eventID, String task) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO Agenda (eventID, task, completion) VALUES (?, ?, false)");
            ps.setInt(1, eventID);
            ps.setString(2, task);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    public static void removeToDo(int eventID, String task) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM Agenda WHERE eventID = ? AND task = ?");
            ps.setInt(1, eventID);
            ps.setString(2, task);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    public static void addAttendee(int eventID, User user) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO Participants (userID, name, email, status, eventID) VALUES (?, ?, ?, 'confirmed', ?)");
            ps.setInt(1, user.getUserID());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, eventID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    public static void removeAttendee(int eventID, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM Participants WHERE eventID = ? AND userID = ?");
            ps.setInt(1, eventID);
            ps.setInt(2, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    public static void modifyNotes(int eventID, String newNotes) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("UPDATE Events SET notes = ? WHERE eventID = ?");
            ps.setString(1, newNotes);
            ps.setInt(2, eventID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, conn);
        }
    }

    private static void loadToDos(Event event) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT task FROM Agenda WHERE eventID = ?");
            ps.setInt(1, event.getEventID());
            rs = ps.executeQuery();

            List<String> tasks = new ArrayList<>();
            while (rs.next()) {
                tasks.add(rs.getString("task"));
            }
            event.setToDo(tasks);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    private static void loadAttendees(Event event) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT userID, name, email FROM Participants WHERE eventID = ?");
            ps.setInt(1, event.getEventID());
            rs = ps.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }
            event.setAttendees(users);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, conn);
        }
    }
}
