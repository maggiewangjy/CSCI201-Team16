import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<Event> events;

    public Calendar() {
        events = new ArrayList<>();
        loadEventsFromDB();
    }

    public void addEvent(Event event) {
        String sql = "INSERT INTO Events (name, startTime, endTime, location, agenda, date, dateMonth, createdBy) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection conn = UserDatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, event.getName());
            ps.setTimestamp(2, event.getStartTime());
            ps.setTimestamp(3, event.getEndTime());
            ps.setString(4, event.getLocation());
            ps.setString(5, event.getAgenda());
            ps.setString(6, event.getDate());
            ps.setString(7, event.getDateMonth());

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
        String sql = "SELECT eventID, name, startTime, notes FROM Events";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("eventID");
                String name = rs.getString("name");
                LocalDate date = rs.getTimestamp("startTime").toLocalDateTime().toLocalDate();
                LocalTime time = rs.getTimestamp("startTime").toLocalDateTime().toLocalTime();
                String notes = rs.getString("notes");

                Event event = new Event(id, date, time, name, notes);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
