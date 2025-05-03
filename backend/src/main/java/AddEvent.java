import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddEvent
 */
@WebServlet("/AddEvent")
public class AddEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String dateStr = request.getParameter("date");  
		String timeStr = request.getParameter("time");   
		String notes = request.getParameter("notes");

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (name == null || dateStr == null || timeStr == null || notes == null ||
			name.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
			out.println("{\"status\":\"error\",\"message\":\"Missing required fields\"}");
			return;
		}

		try {
			LocalDate date = LocalDate.parse(dateStr);
			LocalTime time = LocalTime.parse(timeStr);

			String sql = "INSERT INTO Events (name, date, time, notes) VALUES (?, ?, ?, ?)";
			try (Connection conn = DatabaseConnectionUtil.getConnection();
				 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				stmt.setString(1, name);
				stmt.setDate(2, Date.valueOf(date));
				stmt.setTime(3, Time.valueOf(time));
				stmt.setString(4, notes);

				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 0) {
					out.println("{\"status\":\"error\",\"message\":\"Event insertion failed\"}");
					return;
				}

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					int eventId = rs.getInt(1);
					out.println("{\"status\":\"success\",\"eventID\":" + eventId + ",\"message\":\"Event added\"}");
				} else {
					out.println("{\"status\":\"error\",\"message\":\"No event ID generated\"}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"status\":\"error\",\"message\":\"Server error occurred\"}");
		}
	}
}
