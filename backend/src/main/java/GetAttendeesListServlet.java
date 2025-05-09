import java.io.IOException;
import java.util.ArrayList;

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

        try {
            int eventID = Integer.parseInt(request.getParameter("eventID"));
            ArrayList<String> attendeeNames = AttendancesDatabaseUtil.getAllAttendeesForEvent(eventID);

            if (attendeeNames == null) {
                throw new Exception("Failed to retrieve attendees.");
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
