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
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        //Response will have HTTP status and json status
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Empty fields
        if (name == null || email == null || password == null ||
            name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"All fields are required.\"}");
            return;
        }

        try {
            int userID = UserDatabaseUtil.getUser(email, password);
            
            // Email already taken
            if (userID != -1) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Email already registered.\"}");
                return;
            }

            // Insert new user
            UserDatabaseUtil.insertUser(name, email, password);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Registration successful.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Server error. Please try again later.\"}");
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
