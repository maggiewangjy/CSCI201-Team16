import java.time.LocalDate;
import java.time.LocalTime;
<<<<<<< Updated upstream
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
=======
import java.sql.Timestamp;
>>>>>>> Stashed changes

public class Event {
    private int eventID;
    private String name;
    private java.sql.Timestamp startTime;
    private java.sql.Timestamp endTime;
    private String location;
    private String agenda;
    private String date;
    private String dateMonth;
   

    public Event(int eventID, String name, String startTime, String endTime, String location, String agenda, String date, String dateMonth) {
        this.eventID = eventID;
        this.name = name;
<<<<<<< Updated upstream
        this.notes = notes;
        this.toDo = new ArrayList<>();
        this.attendees = new ArrayList<>();
        loadToDos();
        loadAttendees();
=======
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.agenda = agenda;
        this.date = date;
>>>>>>> Stashed changes
    }

    public Event(LocalDate date, LocalTime time, String name, String notes) {
        this(-1, date, time, name, notes);
    }
  
    // Getters
    public int getEventID() { return eventID; }
    public String getName() { return name; }
<<<<<<< Updated upstream
    public String getNotes() { return notes; }
=======
    public java.sql.Timestamp getStartTime() { return startTime; }
    public java.sql.Timestamp getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public String getAgenda() { return agenda; }
    
>>>>>>> Stashed changes
}
