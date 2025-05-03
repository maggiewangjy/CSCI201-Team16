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

/**
 * Servlet implementation class ListEventsServlets
 */
@WebServlet("/ListEventsServlets")
public class ListEventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListEventsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String sql = "SELECT eventID, name, date, time, notes FROM Events ORDER BY date, time";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder json = new StringBuilder();
            json.append("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                    .append("\"eventID\":").append(rs.getInt("eventID")).append(",")
                    .append("\"name\":\"").append(escapeJson(rs.getString("name"))).append("\",")
                    .append("\"date\":\"").append(rs.getDate("date")).append("\",")
                    .append("\"time\":\"").append(rs.getTime("time")).append("\",")
                    .append("\"notes\":\"").append(escapeJson(rs.getString("notes"))).append("\"")
                    .append("}");
                first = false;
            }

            json.append("]");
            out.print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"Server error occurred\"}");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // Simple JSON escape helper
    private String escapeJson(String str) {
        return str == null ? "" : str.replace("\"", "\\\"").replace("\n", "\\n");
    }
}