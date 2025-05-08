import java.sql.Timestamp;

public class Event {
    private int eventID;
    private String name;
    private java.sql.Timestamp startTime;
    private java.sql.Timestamp endTime;
    private String location;
    private String agenda;
    private String date;
    private String dateMonth;
   

    public Event(int eventID, String name, Timestamp startTime, Timestamp endTime, String location, String agenda, String date, String dateMonth) {
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
    public java.sql.Timestamp getStartTime() { return startTime; }
    public java.sql.Timestamp getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public String getAgenda() { return agenda; }
    public String getDate() { return date; }
    public String getDateMonth() { return dateMonth; }
}
