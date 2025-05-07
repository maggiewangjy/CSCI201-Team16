import java.util.Map;

public class Member extends User{
    private String email;
    private Boolean notificationEnabled;
    private Map<Event, Boolean> attendanceStatus;

    public Member (int userID, String first, String last, String email, Boolean notif, Map<Event, Boolean> attendance) {
        super(userID, first, last);
        this.email = email;
        notificationEnabled = notif;
        attendanceStatus = attendance;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail () {
        return email;
    }

    public void toggleNotification () {
        notificationEnabled = !notificationEnabled;
    }

    public void checkAttendance(Event event) {
    	if (attendanceStatus != null && attendanceStatus.containsKey(event)) {
            boolean attended = attendanceStatus.get(event);
            System.out.println(attended ? "Attended" : "Did not attend");
        } else {
            System.out.println("No attendance record for this event.");
        }
    }

    public String viewEventDetails() {
    	StringBuilder sb = new StringBuilder();
        sb.append("Event Attendance Details:\n");

        if (attendanceStatus == null || attendanceStatus.isEmpty()) {
            sb.append("No events found.");
        } else {
            for (Map.Entry<Event, Boolean> entry : attendanceStatus.entrySet()) {
                Event event = entry.getKey();
                boolean attended = entry.getValue();
                sb.append("- ").append(event.getName())  // Assumes Event has getName()
                  .append(" | Attended: ").append(attended ? "Yes" : "No").append("\n");
            }
        }

        return sb.toString();
    }
}