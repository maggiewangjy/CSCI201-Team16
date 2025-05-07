import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");

		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();

		Gson gson = new Gson();
		JsonObject jsonResponse = new JsonObject();
		try {
			BufferedReader reader = request.getReader();
			Event event = gson.fromJson(reader, Event.class);
			EventDatabase.addEvent(event);
			jsonResponse.addProperty("success", true);
		} catch (Exception e) {
			jsonResponse.addProperty("success", false);
			jsonResponse.addProperty("error", e.getMessage());
		}
		out.print(jsonResponse.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
