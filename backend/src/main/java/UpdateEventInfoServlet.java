import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateEventInfo")
public class UpdateEventInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int eventID = Integer.parseInt(request.getParameter("eventID"));
        String date = request.getParameter("date");
        Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
        Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));
        String location = request.getParameter("location");
        String agenda = request.getParameter("agenda");

        try {
        	int rows = EventDatabase.updateEvent(eventID, date, startTime, endTime, location, agenda);

        	if (rows > 0) {
        	    response.setStatus(HttpServletResponse.SC_OK);
        	    response.getWriter().write("{\"status\":\"success\", \"message\":\"Event updated successfully.\"}");
        	} else {
        	    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        	    response.getWriter().write("{\"status\":\"fail\", \"message\":\"Event not found.\"}");
        	}

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"fail\", \"message\":\"Server error while updating event.\"}");
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
