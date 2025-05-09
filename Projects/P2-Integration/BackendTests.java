import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;


/**
 * A class for testing the methods of the Backend class.
 */
public class BackendTests {
  
  
  
  /**
   * Test the functionality of the loadGraphData method.
   */
  @Test
  public void BackendTest1() {
    
    //Initializa the graph and the backend
    GraphADT<String, Double> g1 = new Graph_Placeholder();
    Backend bkn1 = new Backend(g1);
    
    //Check the non-existing file, should correctly throwing an IOException
    try {
      bkn1.loadGraphData("somefile");
    }
    catch (IOException e) {
      Assertions.assertTrue(true);
    }
    
    //Since we are loading the wrong file, the graph should 
    //have 3 nodes and 2 edges from the Graph_Placeholder
    Assertions.assertTrue(g1.getNodeCount() == 3 && g1.getEdgeCount() == 2);
    
    //Check the existing file, should not throw any exception
    try {
      bkn1.loadGraphData("campus.dot");
      Assertions.assertTrue(true);
    }
    catch (IOException e) {
      Assertions.assertTrue(false);
    }
    
    //Since we are loading the correct file, the graph should not be empty
    //and also clear the previous round of nodes/edges
    Assertions.assertTrue(g1.getNodeCount() == 4 && g1.getEdgeCount() == 3);
  }
  
  
  
  /**
   * Test the functionality of the getListOfAllLocations method
   */
  @Test
  public void BackendTest2() {
    //Initializa the graph and the backend
    GraphADT<String, Double> g2 = new Graph_Placeholder();
    Backend bkn2 = new Backend(g2);
    
    //Get all the locations, should be only the three nodes
    //inside the Graph_Placeholder
    List<String> resultingList = bkn2.getListOfAllLocations();
    Assertions.assertEquals(resultingList.size(), 3);
    Assertions.assertEquals(resultingList.get(0), "Union South");
    Assertions.assertEquals(resultingList.get(1), "Computer Sciences and Statistics");
    Assertions.assertEquals(resultingList.get(2), "Weeks Hall for Geological Sciences");
  }
  
  
  
  
  /**
   * Test the functionality of the findLocationsOnShortestPath method
   * findTimesOnShortestPath method and the getFurthestDestinationFrom method
   */
  @Test
  public void BackendTest3() {
    //Initializa the graph and the backend
    GraphADT<String, Double> g3 = new Graph_Placeholder();
    Backend bkn3 = new Backend(g3);
    
    //Resulting list should be the only two places (start and the end)
    List<String> resultingList = 
        bkn3.findLocationsOnShortestPath("Union South", "Computer Sciences and Statistics");
    Assertions.assertEquals(resultingList.size(), 2);
    Assertions.assertEquals(resultingList.get(0), "Union South");
    Assertions.assertEquals(resultingList.get(1), "Computer Sciences and Statistics");

    
    //Now check for the weights between two locations: In this case should be 1
    List<Double> resultingList2 = 
        bkn3.findTimesOnShortestPath("Union South", "Computer Sciences and Statistics");
    Assertions.assertEquals(resultingList2.size(), 1);
    Assertions.assertEquals(resultingList2.get(0), 1.0);
    
    //Check for the farthest distance possible starting from the Union South
    String result3 = 
        bkn3.getFurthestDestinationFrom("Union South");
    Assertions.assertEquals(result3, "Weeks Hall for Geological Sciences");

  }
  
  
  
  
  /**
   * Integration test to verify frontend generates correct prompt HTML.
   */
  @Test
  public void IntegrationTest1() throws IOException {
    //Initialize the frontend with real Backend/Graph now:
    Frontend frontend = new Frontend(new Backend(new DijkstraGraph<>()));
    
    //ShortestPathPromptHTML output:
    String html = frontend.generateShortestPathPromptHTML();
    Assertions.assertTrue(html.contains("id=\"start\""));
    Assertions.assertTrue(html.contains("id=\"end\""));
    Assertions.assertTrue(html.contains("Find Shortest Path"));
  }
  
  
  
  
  
  /**
   * Integration test for generating shortest path response HTML.
   */
  @Test
  public void IntegrationTest2() throws IOException {
    //Initialize the backend instance:
    Backend backend = new Backend(new DijkstraGraph<>());

    //Create the frontend that depends on the backend:
    Frontend frontend = new Frontend(backend);
    
    //Get the result of ShortestPathResponseHTML
    String html = frontend.generateShortestPathResponseHTML("Union South", "Computer Sciences and Statistics");
    Assertions.assertTrue(html.contains("Shortest path from \"Union South\" to \"Computer Sciences and Statistics\""));
    Assertions.assertTrue(html.contains("Total travel time:"));
  }

  
  
  
  /**
   * Integration test for furthest destination path HTML.
   */
  @Test
  public void IntegrationTest3() throws IOException {
    //Initialize the backend instance:
    Backend backend = new Backend(new DijkstraGraph<>());

    //Create the frontend that depends on the backend:
    Frontend frontend = new Frontend(backend);
    
    //Get the result of FurthestPathResponseHTML
    String html = frontend.generateFurthestDestinationFromResponseHTML("Union South");
    Assertions.assertTrue(html.contains("Furthest destination is \"Smith Residence Hall\""));
    Assertions.assertTrue(html.contains("Total travel time:"));
  }

  
  
  
  
  /**
   * Integration test for invalid input handling in shortest path.
   */
  @Test
  public void IntegrationTest4() throws IOException {
    //Initialize the backend instance:
    Backend backend = new Backend(new DijkstraGraph<>());

    //Create the frontend that depends on the backend:
    Frontend frontend = new Frontend(backend);
    
    //Get the result of shortestPathResponseHTML (for checking of invalid inputs)
    String html = frontend.generateShortestPathResponseHTML("InvalidStart", "InvalidEnd");
    Assertions.assertTrue(html.contains("No path found"));
  }
  

}