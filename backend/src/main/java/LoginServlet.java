import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        //Response will have HTTP status and json status
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String input = "";
		String line = "";
		BufferedReader br = request.getReader();
		while((line = br.readLine()) != null) {
			input += line;
		}
		
		Gson gson = new Gson();
		User user = gson.fromJson(input, User.class);
		
		String email = user.getEmail();
		String password = user.getPassword();
        
        // Empty fields
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"fail\", \"message\":\"Email and password are required.\", \"data\": null}");
            return;
        }

        try {
            User fetchedUser = UserDatabaseUtil.getUser(email, password);
            
            // userID found and successful login
            if (user != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                String json = String.format(
                    "{\"status\":\"success\", \"message\":\"Login successful.\", \"data\": {\"userID\": %d, \"name\": \"%s\", \"email\": \"%s\"}}",
                    1, fetchedUser.getName(), fetchedUser.getEmail()
                );
                response.getWriter().write(json);
            // One or both of email and password do not match and fail log in
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Invalid email or password.\", \"data\": null}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Server error during login.\", \"data\": null}");
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
