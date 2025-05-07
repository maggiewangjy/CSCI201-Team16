import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form inputs
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic validation
        if (name == null || email == null || password == null ||
            name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("All fields are required.");
            return;
        }

        try {
            // Check if the email already exists
            int userID = UserDatabaseUtil.getUser(email, password);
            if (userID != -1) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Email is already registered.");
                return;
            }

            // Insert user
            UserDatabaseUtil.insertUser(name, email, password);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Registration successful.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed due to server error.");
        }
    }
}

