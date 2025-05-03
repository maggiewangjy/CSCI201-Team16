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

@WebServlet("/GetEventServlet")
public class GetEventServlet extends HttpServlet {
    public GetEventServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET request to fetch event by ID
        response.setContentType("application/json");
       
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        
        try {
            BufferedReader reader = request.getReader();
            JsonObject requestJson = gson.fromJson(reader, Event.class);
            
            // Extract eventID from the JSON request
            int eventID = requestJson.get("eventID").getAsInt();
            
            // Assuming you have a method in a utility class to retrieve an Event by ID
            Event event = EventDatase.getEventById(eventID);
            
            if (event != null) {
                // Convert event details to JSON
                JsonObject eventJson = new JsonObject();
                eventJson.addProperty("status", "success");
                eventJson.addProperty("id", event.getEventID());
                eventJson.addProperty("name", event.getName());
                eventJson.addProperty("date", event.getDate().toString());
                eventJson.addProperty("time", event.getTime().toString());
                eventJson.addProperty("notes", event.getNotes());
              
                
                // Send the response
                out.println(gson.toJson(eventJson));

            } else {
                responseJson.addProperty("status", "error");
                out.println(gson.toJson(responseJson));
            }
        } catch (Exception e) {
            responseJson.addProperty("status", "error");
            out.println(gson.toJson(responseJson));
        }
    }
}