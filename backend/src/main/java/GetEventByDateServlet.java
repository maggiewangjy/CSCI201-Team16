import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/GetEventByDate")
public class GetEventByDateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Pass in MMDDYYYY
        String dateParam = request.getParameter("date"); 

        try {
            LocalDate date = LocalDate.parse(dateParam, formatter);
            List<Event> events = EventDatabase.getEventsByDate(date);

            // Query returns no events for specific date
            if (events.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"status\":\"success\", \"message\":\"No events found.\", \"data\": []}");
                return;
            }

            String eventsJson = new Gson().toJson(events);
            String jsonResponse = String.format(
                "{\"status\":\"success\", \"message\":\"Events retrieved.\", \"data\": %s}",
                eventsJson
            );
            
            // Send list of events for that day back to front-end
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Server error.\", \"data\": null}");
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
