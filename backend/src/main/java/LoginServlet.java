

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			out.println("{\"status\":\"error\",\"message\":\"Missing email or password\"}");
			return;
		}

		try {
			int userId = UserDatabaseUtil.getUser(email, password);
			if (userId != -1) {
				out.println("{\"status\":\"success\",\"message\":\"Login successful\", \"userID\":" + userId + "}");
			} else {
				out.println("{\"status\":\"error\",\"message\":\"Invalid credentials\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"status\":\"error\",\"message\":\"Server error\"}");
		}
	}
}
