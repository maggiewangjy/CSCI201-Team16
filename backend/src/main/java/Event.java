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


    // Getters
    public int getEventID() { return eventID; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getName() { return name; }
    public String getNotes() { return notes; }
}
