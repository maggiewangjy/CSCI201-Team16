import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddEvent")
public class AddEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Calendar calendar = new Calendar();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String dateTime = request.getParameter("datetime");  
        String location = request.getParameter("location");
        String agenda = request.getParameter("agenda");

        if (name == null || dateTime == null || location == null || agenda == null ||
            name.isEmpty() || dateTime.isEmpty() || location.isEmpty() || agenda.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"fail\", \"message\":\"All fields are required.\"}");
            return;
        }

        try {
            // Parse datetime and calculate end time
            LocalDateTime start = LocalDateTime.parse(dateTime); 
            LocalDateTime end = start.plusHours(1);
            Timestamp startTs = Timestamp.valueOf(start);
            Timestamp endTs = Timestamp.valueOf(end);

            // Format date/month strings
            String date = start.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            String dateMonth = start.format(DateTimeFormatter.ofPattern("MMyyyy"));

            Event event = new Event(0, name, startTs, endTs, location, agenda, date, dateMonth);
            calendar.addEvent(event);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Event created.\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to create event.\"}");
        }
    }
}
