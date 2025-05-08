import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/GetEventsByDateServlet")
public class GetEventsByDateServlet extends HttpServlet {
    public GetEventsByDateServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();

        try 
        {
            BufferedReader reader = request.getReader();
            String date = gson.fromJson(reader, String.class);

            ArrayList<Event> events = EventDatabase.getEventsByDate(date);
            
            String json = gson.toJson(events);
            responseJson.add("events", gson.toJsonTree(events));
            out.println(json);
            
        }
        catch (SQLException e) {
            responseJson.addProperty("error", "Failed to fetch events: " + e.getMessage());
            out.println(gson.toJson(responseJson));
        }
    }
}