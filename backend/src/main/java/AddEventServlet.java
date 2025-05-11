import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

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

            Time startSqlTime = Time.valueOf(startTime);
            Time endSqlTime = Time.valueOf(endTime);

            // Format MMDDYYYY and MMyyyy for DB
            String dateFormatted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            String dateMonth = date.format(DateTimeFormatter.ofPattern("MMyyyy"));

            Event event = new Event(0, name, startSqlTime, endSqlTime, location, agenda, dateFormatted, dateMonth);
            EventDatabase.addEvent(event);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Event created.\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to create event.\"}");
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
