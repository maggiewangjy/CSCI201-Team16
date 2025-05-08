import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListEvents")
public class ListEventsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ListEventsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // i had to look up cors header so hopefully this is right
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json; charset=UTF-8");

        String sql = "SELECT EventID, EventName, Date, StartTime, EndTime, Location, Agenda "
                   + "FROM Event "
                   + "ORDER BY Date, StartTime";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();
             PrintWriter out = response.getWriter()) {

            StringBuilder json = new StringBuilder();
            json.append("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                    .append("\"eventID\":").append(rs.getInt("EventID")).append(",")
                    .append("\"name\":\"").append(escapeJson(rs.getString("EventName"))).append("\",")
                    .append("\"date\":\"").append(rs.getString("Date")).append("\",")
                    .append("\"startTime\":\"").append(rs.getString("StartTime")).append("\",")
                    .append("\"endTime\":\"").append(rs.getString("EndTime")).append("\",")
                    .append("\"location\":\"").append(escapeJson(rs.getString("Location"))).append("\",")
                    .append("\"agenda\":\"").append(escapeJson(rs.getString("Agenda"))).append("\"")
                    .append("}");
                first = false;
            }

            json.append("]");
            out.print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            try (PrintWriter out = response.getWriter()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"status\":\"error\",\"message\":\"Server error occurred\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }
}
