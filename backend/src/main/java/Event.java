import java.time.LocalDate;
import java.time.LocalTime;
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
    }

    public int getEventID() { return eventID; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getName() { return name; }
    public String getNotes() { return notes; }

    public List<String> getToDo() { return toDo; }
    public List<User> getAttendees() { return attendees; }

    public void setToDo(List<String> toDo) { this.toDo = toDo; }
    public void setAttendees(List<User> attendees) { this.attendees = attendees; }

    public void addToDo(String task) { this.toDo.add(task); }
    public void removeToDo(String task) { this.toDo.remove(task); }

    public void addAttendee(User user) { this.attendees.add(user); }
    public void removeAttendee(User user) { this.attendees.remove(user); }
}
