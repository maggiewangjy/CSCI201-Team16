import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			// Read the request body
			BufferedReader reader = request.getReader();
			StringBuilder jsonRequest = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonRequest.append(line);
			}

			System.out.println("Received JSON request: " + jsonRequest.toString());

			// Parse the JSON request
			JSONObject json = new JSONObject(jsonRequest.toString());
			String email = json.getString("email");
			String password = json.getString("password");
			String q1 = json.getString("securityQuestion1");
			String a1 = json.getString("securityAnswer1");
			String q2 = json.getString("securityQuestion2");
			String a2 = json.getString("securityAnswer2");

			System.out.println("Parsed values - Email: " + email + ", Q1: " + q1 + ", Q2: " + q2);

			if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
				System.out.println("Missing email or password");
				out.println("{\"status\":\"error\",\"message\":\"Missing email or password\"}");
				return;
			}

			try {
				UserDatabaseUtil.insertUser(email, password, q1, a1, q2, a2);
				System.out.println("User inserted successfully");
				out.println("{\"status\":\"success\",\"message\":\"Registration successful\"}");
			} catch (SQLException e) {
				System.err.println("SQL Error during user insertion: " + e.getMessage());
				e.printStackTrace();
				String errorMessage = e.getMessage();
				if (errorMessage.contains("Duplicate entry")) {
					out.println("{\"status\":\"error\",\"message\":\"Email already registered\"}");
				} else if (errorMessage.contains("Communications link failure")) {
					out.println("{\"status\":\"error\",\"message\":\"Database connection failed. Please try again later.\"}");
				} else {
					out.println("{\"status\":\"error\",\"message\":\"Registration failed: " + errorMessage + "\"}");
				}
			}
		} catch (Exception e) {
			System.err.println("General error during registration: " + e.getMessage());
			e.printStackTrace();
			out.println("{\"status\":\"error\",\"message\":\"Registration failed: " + e.getMessage() + "\"}");
		}
	}
}
