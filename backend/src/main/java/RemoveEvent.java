

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveEvent
 */
@WebServlet("/RemoveEvent")
public class RemoveEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveEvent() {
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

		String eventIdStr = request.getParameter("eventID");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (eventIdStr == null || eventIdStr.isEmpty()) {
			out.println("{\"status\":\"error\",\"message\":\"Missing eventID\"}");
			return;
		}

		try {
			int eventID = Integer.parseInt(eventIdStr);

			String sql = "DELETE FROM Events WHERE eventID = ?";
			try (Connection conn = DatabaseConnectionUtil.getConnection();
				 PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, eventID);
				int rows = stmt.executeUpdate();

				if (rows > 0) {
					out.println("{\"status\":\"success\",\"message\":\"Event removed\"}");
				} else {
					out.println("{\"status\":\"error\",\"message\":\"Event not found or already deleted\"}");
				}
			}
		} catch (NumberFormatException e) {
			out.println("{\"status\":\"error\",\"message\":\"Invalid eventID format\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"status\":\"error\",\"message\":\"Server error\"}");
		}
	}
}