import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/RemoveAttendeeFromEvent")
public class RemoveAttendeeFromEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Read JSON request body
        String input = "";
        String line = "";
        BufferedReader br = request.getReader();
        while((line = br.readLine()) != null) {
            input += line;
        }
        
        // Parse JSON
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(input, JsonObject.class);
        
        String name = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();
        String eventID = jsonObject.get("eventID").getAsString();
        
        System.out.println("Received request - name: " + name + ", email: " + email + ", eventID: " + eventID);
        
        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"failed\", \"message\": \"Name and email are required\"}");
            return;
        }
        
        try {
            int eventIDInt = Integer.parseInt(eventID);
            User user = new User(name, email, ""); // Password not needed for attendance
            AttendancesDatabaseUtil.removeAttendee(eventIDInt, user);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Attendee removed successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"failed\", \"message\": \"Invalid eventID\"}");
        }
	}
	
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);
    }    
}
