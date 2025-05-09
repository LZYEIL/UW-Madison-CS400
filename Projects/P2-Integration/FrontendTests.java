import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FrontendTests {

    /**
     * frontendTest1:
     * Tests the two prompt-generating methods:
     * - generateShortestPathPromptHTML()
     * - generateFurthestDestinationFromPromptHTML()
     *
     * Each test checks for the presence of required input fields and buttons
     * in the generated HTML, including basic edge checks (non-null and non-empty).
     */
    @Test
    public void frontendTest1() {
        // Set up Frontend with the Backend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        BackendInterface backend = new Backend_Placeholder(graph);
        FrontendInterface frontend = null;
        try {
            frontend = new Frontend(backend);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail();
        }

        // Test case 1: generateShortestPathPromptHTML()
        String shortestHTML = frontend.generateShortestPathPromptHTML();

        Assertions.assertNotNull(shortestHTML, "shortestHTML should not be null");
        Assertions.assertTrue(shortestHTML.contains("id=\"start\""), "shortestHTML should contain id=\"start\"");
        Assertions.assertTrue(shortestHTML.contains("id=\"end\""), "shortestHTML should contain id=\"end\"");
        Assertions.assertTrue(shortestHTML.contains("Find Shortest Path"), "shortestHTML should contain 'Find Shortest Path'");

        // Test case 2: generateFurthestDestinationFromPromptHTML()
        String furthestHTML = frontend.generateFurthestDestinationFromPromptHTML();
        
        Assertions.assertNotNull(furthestHTML, "furthestHTML should not be null");
        Assertions.assertTrue(furthestHTML.contains("id=\"from\""), "furthestHTML should contain id=\"from\"");
        Assertions.assertTrue(furthestHTML.contains("Furthest Destination From"), "furthestHTML should contain 'Furthest Destination From'");
    }

    /**
     * frontendTest2:
     * Tests generateShortestPathResponseHTML(start, end) with:
     * - A valid path (expected list and time)
     * - An invalid start location (should show error)
     * - A reversed path (should fail, but placeholder returns partial result)
     */
    @Test
    public void frontendTest2() {
        // Set up Frontend with the Backend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        BackendInterface backend = new Backend_Placeholder(graph);
        FrontendInterface frontend = null;
        try {
            frontend = new Frontend(backend);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }

        // Test case 1: Valid path from "Union South" to "Weeks Hall for Geological Sciences"
        String html1 = frontend.generateShortestPathResponseHTML("Union South", "Weeks Hall for Geological Sciences");

        Assertions.assertNotNull(html1, "html1 should not be null"); // Should return valid HTML
        Assertions.assertTrue(html1.contains("<ol>"), "html1 should contain <ol>"); // Should include ordered list
        Assertions.assertTrue(html1.contains("Union South"), "html1 should include 'Union South'"); // Start location appears
        Assertions.assertTrue(html1.contains("Weeks Hall for Geological Sciences"), "html1 should include destination"); // End location appears
        Assertions.assertTrue(html1.contains("Total travel time"), "html1 should contain 'Total travel time'"); // Travel time is shown

        // Test case 2: Invalid start location
        String html2 = frontend.generateShortestPathResponseHTML("Invalid Start", "Weeks Hall for Geological Sciences");

        Assertions.assertNotNull(html2, "html2 should not be null"); // Should still return HTML
        Assertions.assertTrue(html2.toLowerCase().contains("no path"), "html2 should contain error message"); // Should show error message

        // Test case 3: Start comes after end in placeholder path
        String html3 = frontend.generateShortestPathResponseHTML("Weeks Hall for Geological Sciences", "Union South");

        Assertions.assertNotNull(html3, "html3 should not be null"); // Should return HTML

        Assertions.assertTrue(html3.contains("<ol>"), "html3 should contain a list even if the path is partial"); // Return the ordered list
    }

    /**
     * frontendTest3:
     * Tests generateFurthestDestinationFromResponseHTML(start) with:
     * - A valid input that should return full path and travel time
     * - An invalid start that should return an error
     * - A case where no furthest destination is found (based on placeholder limits)
     */
    @Test
    public void frontendTest3() {
        // Set up Frontend with the Backend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        BackendInterface backend = new Backend_Placeholder(graph);
        FrontendInterface frontend = null;
        try {
            frontend = new Frontend(backend);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }

        // Test case 1: Valid start location
        String html1 = frontend.generateFurthestDestinationFromResponseHTML("Union South");

        Assertions.assertNotNull(html1, "html1 should not be null"); // Should return HTML
        Assertions.assertTrue(html1.contains("Union South"), "html1 should mention start"); // Start appears
        Assertions.assertTrue(html1.contains("Furthest destination"), "html1 should mention furthest destination"); // Destination info
        Assertions.assertTrue(html1.contains("<ol>"), "html1 should contain path list"); // Ordered list appears
        Assertions.assertTrue(html1.contains("Total travel time"), "html1 should include travel time"); // Time paragraph appears

        // Test case 2: Invalid start location
        String html2 = frontend.generateFurthestDestinationFromResponseHTML("Invalid Start");

        Assertions.assertNotNull(html2, "html2 should not be null"); // Should still return HTML
        Assertions.assertTrue(
            html2.toLowerCase().contains("error") || html2.toLowerCase().contains("no path"),
            "html2 should show error message for invalid start"
        ); // Should show error message

        // Test case 3: Start equals end — placeholder returns same node (edge case)
        // Placeholder backend returns "Weeks Hall for Geological Sciences" as furthest from itself
        String html3 = frontend.generateFurthestDestinationFromResponseHTML("Mosse Humanities Building");

        Assertions.assertNotNull(html3, "html3 should not be null"); // Should still return HTML

        // This may return the same location as start, so frontend will treat it as invalid
        Assertions.assertTrue(
            html3.toLowerCase().contains("no valid") || html3.toLowerCase().contains("error"),
            "html3 should show message when no furthest destination is found"
        );
    }

}
