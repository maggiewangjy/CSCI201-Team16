public class Member extends User{
    private String email;
    private Boolean notificationEnabled;
    private Map<Event, Boolean> attendanceStatus;

    public Member (String first, String last, String email, Boolean notif, Map<Event, Boolean> attendance) {
        super(first, last);
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

    public void checkAttendance() {
        // TODO
    }

    public String viewEventDetails() {
        return "placeholder";
        // TODO
    }
}
