import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private int eventID;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String notes;
    private List<String> toDo;
    private List<User> attendees;

    public Event(int eventID, LocalDate date, LocalTime time, String name, String notes) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.name = name;
        this.notes = notes;
        this.toDo = new ArrayList<>();
        this.attendees = new ArrayList<>();
        loadToDos();
        loadAttendees();
    }

    public Event(LocalDate date, LocalTime time, String name, String notes) {
        this(-1, date, time, name, notes);
    }

    // Add to-do
    public void addToDo(String task) {
        String sql = "INSERT INTO Agenda (eventID, task, completion) VALUES (?, ?, false)";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventID);
            ps.setString(2, task);
            ps.executeUpdate();
            toDo.add(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeToDo(String task) {
        String sql = "DELETE FROM Agenda WHERE eventID = ? AND task = ?";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventID);
            ps.setString(2, task);
            ps.executeUpdate();
            toDo.remove(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAttendee(User user) {
        String sql = "INSERT INTO Participants (userID, name, email, status, eventID) VALUES (?, ?, ?, 'confirmed', ?)";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, member.getUserID());
            ps.setString(2, member.getName());
            ps.setString(3, member.getEmail());
            ps.setInt(4, eventID);
            ps.executeUpdate();
            attendees.add(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAttendee(User user) {
        String sql = "DELETE FROM Participants WHERE eventID = ? AND userID = ?";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventID);
            ps.setInt(2, member.getUserID());
            ps.executeUpdate();
            attendees.remove(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyNotes(String newNotes) {
        String sql = "UPDATE Events SET notes = ? WHERE eventID = ?";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newNotes);
            ps.setInt(2, eventID);
            ps.executeUpdate();
            this.notes = newNotes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void loadToDos() {
        String sql = "SELECT task FROM Agenda WHERE eventID = ?";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) toDo.add(rs.getString("task"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAttendees() {
        String sql = "SELECT userID, name, email FROM Participants WHERE eventID = ?";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    rs.getString("email")
                );
                attendees.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public int getEventID() { return eventID; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getName() { return name; }
    public String getNotes() { return notes; }
}
