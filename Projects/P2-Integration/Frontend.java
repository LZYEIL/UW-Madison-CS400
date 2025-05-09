import java.io.IOException;
import java.util.List;

/**
 * Frontend class that generates HTML for user input and output.
 * Uses the backend to perform path and graph-related computations.
 */
public class Frontend implements FrontendInterface {
    // Backend private instance used to compute paths and travel times
    private BackendInterface backend;
    
    /**
     * Frontend constructor to update the backend instance.
     * 
     * @param backend the backend instance used for path computations
     * @throws IOException exception thrown if invalid file input
     */
    public Frontend(BackendInterface backend) throws IOException {
        this.backend = backend; // Update the backend
        this.backend.loadGraphData("campus.dot"); // Load .dot file
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page. This HTML output should include:
     * - a text input field with the id="start", for the start location
     * - a text input field with the id="end", for the destination
     * - a button labelled "Find Shortest Path" to request this computation
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     *
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a shortest path computation
     */
    @Override
    public String generateShortestPathPromptHTML() {
        // Construct an HTML string that contains input controls for the user
        // to request a shortest path computation
        return "<div>\n"
        + "  <input type=\"text\" id=\"start\" placeholder=\"Start Location\">\n"
        + "  <input type=\"text\" id=\"end\" placeholder=\"Destination\">\n"
        + "  <input type=\"button\" value=\"Find Shortest Path\">\n"
        + "</div>\n";
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page. This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total travel time along this path
     * Or if there is no such path, the HTML returned should instead indicate 
     * the kind of problem encountered.
     *
     * @param start is the starting location to find a shortest path from
     * @param end is the destination that this shortest path should end at
     * @return an HTML string that describes the shortest path between these
     *         two locations
     */
    @Override
    public String generateShortestPathResponseHTML(String start, String end) {
        try {
            // Get the list of locations on the shortest path from start to end from backend
            List<String> path = backend.findLocationsOnShortestPath(start, end);

            // Check if the path is null or empty, and return an error message if so
            if (path == null || path.isEmpty())
                return "<p>No path found from \"" + start + "\" to \"" + end + "\".</p>";

            // Construct the HTML outputcontains the path with the start and end locations
            String html = "<p>Shortest path from \"" + start + "\" to \"" + end + "\":</p><ol>";

            // Iterate through the path
            for (String location : path)
                html += "<li>" + location + "</li>"; // and add each location to an ordered list
            html += "</ol>";

            // Calculate the total travel time along the path
            double totalTime = 0; 
            for (Double time : backend.findTimesOnShortestPath(start, end))
                totalTime += time;

            // Add the total travel time to the HTML output
            html += "<p>Total travel time: " + String.format("%.1f", totalTime) + " seconds</p>";
            return html;

        } catch (Exception e) { // Handle any exceptions that may occur during the path computation
            // Return an error message if an exception occurs
            return "<p>Error finding path: " + e.getMessage() + "</p>";
        }
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page. This HTML output should include:
     * - a text input field with the id="from", for the start location
     * - a button labelled "Furthest Destination From" to submit this request
     * Ensure that this text field is clearly labelled, so that the user
     * can understand how to use it.
     *
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a furthest destination calculation
     */
    @Override
    public String generateFurthestDestinationFromPromptHTML() {
        // Construct an HTML string that contains input controls for the user
        // to request a furthest destination calculation
        return "<div>\n"
        + "  <input type=\"text\" id=\"from\" placeholder=\"Starting Location\">\n"
        + "  <input type=\"button\" value=\"Furthest Destination From\">\n"
        + "</div>\n";
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page. This HTML output should include:
     * - a paragraph (p) that describes the starting point being searched from
     * - a paragraph (p) that describes the furthest destination found
     * - an ordered list (ol) of locations on the path between these locations
     * Or if there is no such destination, the HTML returned should instead 
     * indicate the kind of problem encountered.
     *
     * @param start is the starting location to find the furthest dest from
     * @return an HTML string that describes the furthest destination from the
     *         specified start location
     */
    @Override
    public String generateFurthestDestinationFromResponseHTML(String start) {
        try {
            // Get the furthest destination from the backend using the start location
            String end = backend.getFurthestDestinationFrom(start);

            // Check if the end location is null or equals the start location and handle the case
            if (end == null || end.equals(start)) {
                return "<p>No valid furthest destination could be found from \"" + start + "\".</p>";
            }

            // Get the list of locations on the shortest path from start to end
            List<String> path = backend.findLocationsOnShortestPath(start, end);

            // Check if the path is null or empty, and return an error message if so
            if (path == null || path.isEmpty()) {
                return "<p>No path found from \"" + start + "\" to \"" + end + "\".</p>";
            }

            // Construct the HTML output that contains the path with the start and end locations
            String html = "<p>Searching from \"" + start + "\".</p>";
            html += "<p>Furthest destination is \"" + end + "\".</p>";
            html += "<ol>";

            // Iterate through the path
            for (String location : path)
                html += "<li>" + location + "</li>"; // and add each location to an ordered list
            html += "</ol>";

            // Calculate the total travel time along the path
            double totalTime = 0;
            for (Double time : backend.findTimesOnShortestPath(start, end))
                totalTime += time;

            // Add the total travel time to the HTML output
            html += "<p>Total travel time: " + String.format("%.1f", totalTime) + " seconds</p>";
            return html;

        } catch (Exception e) { // Handle any exceptions that may occur during the furthest destination computation
            // Return an error message if an exception occurs
            return "<p>Error finding furthest destination: " + e.getMessage() + "</p>";
        }
    }

}
