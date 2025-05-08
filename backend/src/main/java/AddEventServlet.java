import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        String dateStr = request.getParameter("date");      
        String startStr = request.getParameter("start");    
        String endStr = request.getParameter("end");       
        String location = request.getParameter("location");
        String agenda = request.getParameter("agenda");

        if (name == null || dateStr == null || startStr == null || endStr == null ||
            location == null || agenda == null ||
            name.isEmpty() || dateStr.isEmpty() || startStr.isEmpty() || endStr.isEmpty() ||
            location.isEmpty() || agenda.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"fail\", \"message\":\"All fields are required.\"}");
            return;
        }

        try {
            // Combine date and times into LocalDateTime
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime startTime = LocalTime.parse(startStr);
            LocalTime endTime = LocalTime.parse(endStr);

            LocalDateTime start = LocalDateTime.of(date, startTime);
            LocalDateTime end = LocalDateTime.of(date, endTime);

            Timestamp startTs = Timestamp.valueOf(start);
            Timestamp endTs = Timestamp.valueOf(end);

            // Format MMDDYYYY and MMyyyy for DB
            String dateFormatted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            String dateMonth = date.format(DateTimeFormatter.ofPattern("MMyyyy"));

            Event event = new Event(0, name, startTs, endTs, location, agenda, dateFormatted, dateMonth);
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
