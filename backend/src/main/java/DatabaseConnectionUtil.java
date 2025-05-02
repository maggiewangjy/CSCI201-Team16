import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CSCI201-Team16"; // edit based on your url path
    private static final String DB_USER = "root"; // your username
    private static final String DB_PASSWORD = "root"; // your password
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}