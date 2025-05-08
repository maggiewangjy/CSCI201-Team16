import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetAttendanceByMember")
public class GetAttendanceByMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"fail\", \"message\":\"Email is required.\", \"data\": null}");
            return;
        }

        int userAttendance = 0;
        int totalEvents = 0;

        try (Connection conn = UserDatabaseUtil.getConnection()) {
            // Count attendance for this email
            PreparedStatement attendanceStmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM Attendance WHERE email = ?");
            attendanceStmt.setString(1, email);
            ResultSet rs1 = attendanceStmt.executeQuery();
            if (rs1.next()) {
                userAttendance = rs1.getInt("count");
            }

            // Count total events
            PreparedStatement eventStmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM events");
            ResultSet rs2 = eventStmt.executeQuery();
            if (rs2.next()) {
                totalEvents = rs2.getInt("count");
            }

            double percentage = (double) userAttendance / totalEvents * 100;

            response.setStatus(HttpServletResponse.SC_OK);
            String json = String.format(
                "{\"status\":\"success\", \"message\":\"Attendance calculated.\", \"data\": {\"percentage\": %.2f}}",
                percentage
            );
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Server error.\", \"data\": null}");
        }
    }
}
