import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetAttendeesList")
public class GetAttendeesListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String eventID = request.getParameter("eventID");

        List<String> attendeeNames = new ArrayList<>();
        
        try (Connection conn = AttendancesDatabaseUtil.getConnection()) {
            String sql = "SELECT u.name FROM Attendance a JOIN users u ON a.email = u.email WHERE a.eventID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attendeeNames.add(rs.getString("name"));
            }

            // Build JSON array manually
            StringBuilder namesJson = new StringBuilder();
            namesJson.append("[");
            for (int i = 0; i < attendeeNames.size(); i++) {
                namesJson.append("\"").append(attendeeNames.get(i)).append("\"");
                if (i < attendeeNames.size() - 1) namesJson.append(",");
            }
            namesJson.append("]");

            String resultJson = String.format(
                "{\"status\":\"success\", \"message\":\"Attendees retrieved.\", \"data\": {\"names\": %s}}",
                namesJson.toString()
            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(resultJson);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"failed\", \"message\":\"Server error.\", \"data\": null}");
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
