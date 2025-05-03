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

@WebServlet("/GetEventByDateServlet")
public class GetEventByDateServlet extends HttpServlet {
    public GetEventByDateServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        
        String dataParam = request.getParameter("date");
        if (dataParam == null || dataParam.isEmpty()) {
            responseJson.addProperty("error", "Date parameter is required");
            out.println(gson.toJson(responseJson));
            return;
        }
        
        LocalDate queryDate;
        try {
           queryDate = LocalDate.parse(dataParam);
        } catch (Exveption e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Use YYYY-MM-DD.");
            out.println(gson.toJson(responseJson));
            return;
        }

        try {

            ArrayList<Event> events = Event.getEventsByDate(queryDate);
            responseJson.add("events", gson.toJsonTree(events));
            out.println(gson.toJson(responseJson));
            
        } catch (SQLException e) {
            responseJson.addProperty("error", "Failed to fetch events: " + e.getMessage());
            out.println(gson.toJson(responseJson));
        }
    }
}