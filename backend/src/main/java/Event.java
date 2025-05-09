import java.sql.Time;
import java.text.SimpleDateFormat;

public class Event {
    private int eventID;
    private String name;
    private Time startTime;
    private Time endTime;
    private String location;
    private String agenda;
    private String date;
    private String dateMonth;

    public Event(int eventID, String name, Time startTime, Time endTime, String location, String agenda, String date, String dateMonth) {
        this.eventID = eventID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.agenda = agenda;
        this.date = date;
        this.dateMonth = dateMonth;
    }

    public int getEventID() { return eventID; }
    public String getName() { return name; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public String getAgenda() { return agenda; }
    public String getDate() { return date; }
    public String getDateMonth() { return dateMonth; }
    
    public String displayDetails() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Event ID: ").append(eventID).append("\n")
          .append("Name: ").append(name).append("\n")
          .append("Date (MMDDYYYY): ").append(date).append("\n")
          .append("Start Time: ").append(timeFormat.format(startTime)).append("\n")
          .append("End Time: ").append(timeFormat.format(endTime)).append("\n")
          .append("Location: ").append(location).append("\n")
          .append("Agenda: ").append(agenda);
        return sb.toString();
    }
}
