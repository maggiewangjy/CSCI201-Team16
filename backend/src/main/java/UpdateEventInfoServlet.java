import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String eventID = request.getParameter("eventID");
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String location = request.getParameter("location");
        String agenda = request.getParameter("agenda");

        // TODO do all fields need to be filled out? Check with team
        if (eventID == null || date == null || startTime == null || endTime == null || location == null || agenda == null ||
            eventID.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || location.isEmpty() || agenda.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"fail\", \"message\":\"All fields are required.\"}");
            return;
        }

        try (Connection conn = EventDatabase.getConnection()) {
            String sql = "UPDATE events SET date = ?, startTime = ?, endTime = ?, location = ?, agenda = ? WHERE eventID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setString(2, startTime);
            stmt.setString(3, endTime);
            stmt.setString(4, location);
            stmt.setString(5, agenda);
            stmt.setString(6, eventID);

            int rows = stmt.executeUpdate();
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
}
