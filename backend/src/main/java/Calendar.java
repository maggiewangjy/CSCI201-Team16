import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<Event> events;

    public Calendar() {
        events = new ArrayList<>();
        loadEventsFromDB();
    }

    public void addEvent(Event event) {
        String sql = "INSERT INTO Events (name, startTime, endTime, location, agenda, date, dateMonth) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = UserDatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, event.getName());
            ps.setTimestamp(2, event.getStartTime());
            ps.setTimestamp(3, event.getEndTime());
            ps.setString(4, event.getLocation());
            ps.setString(5, event.getAgenda());
            ps.setString(6, event.getDate());       // MMDDYYYY (already formatted)
            ps.setString(7, event.getDateMonth());  // MMyyyy (already formatted)
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int newID = keys.getInt(1);
                Event newEvent = new Event(
                    newID,
                    event.getName(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getLocation(),
                    event.getAgenda(),
                    event.getDate(),
                    event.getDateMonth()
                );
                events.add(newEvent);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEvent(Event event) {
        String sql = "DELETE FROM Events WHERE eventID = ?";
        try (Connection conn = UserDatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, event.getEventID());
            ps.executeUpdate();
            events.remove(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> viewCalendar() {
        return events;
    }

    private void loadEventsFromDB() {
        String sql = "SELECT eventID, name, startTime, endTime, location, agenda, date, dateMonth FROM Events";
        try (Connection conn = UserDatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
                );
                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
