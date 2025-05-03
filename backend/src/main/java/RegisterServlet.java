import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
		String q1 = request.getParameter("securityQuestion1");
		String a1 = request.getParameter("securityAnswer1");
		String q2 = request.getParameter("securityQuestion2");
		String a2 = request.getParameter("securityAnswer2");

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			out.println("{\"status\":\"error\",\"message\":\"Missing email or password\"}");
			return;
		}

		try {
			UserDatabaseUtil.insertUser(email, password, q1, a1, q2, a2);
			out.println("{\"status\":\"success\",\"message\":\"Registration successful\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"status\":\"error\",\"message\":\"Registration failed\"}");
		}
	}
}
