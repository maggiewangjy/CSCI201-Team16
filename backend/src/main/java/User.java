public class User {
    private String firstName;
    private String lastName;
    private int userID;

    public User(int userID, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }
    
    public String getFirstName () {
        return firstName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
