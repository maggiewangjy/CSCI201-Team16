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

@WebServlet("/AddAttendeeToEvent")
public class AddAttendeeToEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String eventID = request.getParameter("eventID");
        
        // Retrieve password from Users table
        String password = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	conn = AttendancesDatabaseUtil.getConnection();
            ps = conn.prepareStatement("SELECT password FROM Users WHERE NAME = ? AND EMAIL = ?");
            ps.setString(1, name);
            ps.setString(2, email);
            rs = ps.executeQuery();
            if (rs.next()) {
            	password = rs.getString("password");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User not found\"}");
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			AttendancesDatabaseUtil.closeResources(rs, ps, conn);
		}
        
        try {
            int eventIDInt = Integer.parseInt(eventID);
            User user = new User(name, email, password);
            AttendancesDatabaseUtil.addAttendee(eventIDInt, user);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Attendee added successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid eventID\"}");
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
