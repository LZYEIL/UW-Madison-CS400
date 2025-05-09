import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.*;



/**
 * The Backend class serves as the core logic layer that interacts with the graph data structure.
 * It handles loading graph data from DOT files, querying for all locations, computing the shortest
 * paths between locations, calculating the time it takes to traverse those paths, and identifying
 * the most distant location from a given starting point.
 */
public class Backend implements BackendInterface {
  
  
  private GraphADT<String, Double> graph;
  
  
  /**
   * This is the constructor of the Backend class
   * @param graph  Object to store the backend's graph data
   */
  public Backend(GraphADT<String, Double> graph) {
    this.graph = graph;
  }
  
  
  
  
  /**
   * Loads graph data from a dot file.  If a graph was previously loaded, this
   * method should first delete the contents (nodes and edges) of the existing 
   * graph before loading a new one.
   * @param filename the path to a dot file to read graph data from
   * @throws IOException if there was any problem reading from this file
   */
  @Override
  public void loadGraphData(String filename) throws IOException {   
    
    // Clear the existing graph data
    for (String node : new ArrayList<>(this.graph.getAllNodes())) {
        this.graph.removeNode(node);
    }
    
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      
      // Regex expression pattern
      String regex = "\"([^\"]+)\"\\s*->\\s*\"([^\"]+)\"\\s*\\[seconds=([\\d.]+)\\];";
      Pattern pattern = Pattern.compile(regex);
      
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.matches()) {
          String node1 = matcher.group(1);  // First node
          String node2 = matcher.group(2);  // Second node
          double weight = Double.parseDouble(matcher.group(3));  // Edge weight
            
          this.graph.insertNode(node1);
          this.graph.insertNode(node2);
          this.graph.insertEdge(node1, node2, weight);
        }
      }
      reader.close();  
    } 
    catch (IOException e) {
      throw new IOException("Error reading the file: " + filename, e);
    }
  }
  
  
  
  
  
  /**
   * Returns a list of all locations (node data) available in the graph.
   * @return list of all location names
   */
  @Override
  public List<String> getListOfAllLocations() {
    return this.graph.getAllNodes();
  }
  
  
  
  
  /**
   * Return the sequence of locations along the shortest path from 
   * startLocation to endLocation, or an empty list if no such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the nodes along the shortest path from startLocation 
   *         to endLocation, or an empty list if no such path exists
   */
  @Override
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
    try {
      return this.graph.shortestPathData(startLocation, endLocation);
    } 
    catch (NoSuchElementException e) {
      return new ArrayList<String>();
    }
  }
  
  
  
  
  /**
   * Return the walking times in seconds between each two nodes on the 
   * shortest path from startLocation to endLocation, or an empty list of no 
   * such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the walking times in seconds between two nodes along 
   *         the shortest path from startLocation to endLocation, or an empty 
   *         list if no such path exists
   */
  @Override
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
    // Get our location list for the shortest path:
    List<String> locationList = this.findLocationsOnShortestPath(startLocation, endLocation);
    List<Double> timeList = new ArrayList<Double>();
    
    // Iterate through each consecutive pair of locations along the shortest path
    // and collect the time (edge weight) between them
    for (int i = 0; i < locationList.size() - 1; i++) {
      timeList.add(this.graph.getEdge(locationList.get(i), locationList.get(i + 1)));
    }
    return timeList;
  }
  
  
  
  
  
  /**
   * Returns the most distant location (the one that takes the longest time to 
   * reach) when comparing all shortest paths that begin from the provided 
   * startLocation.
   * @param startLocation the location to find the most distant location from
   * @return the most distant location (the one that takes the longest time to 
   *         reach which following the shortest path)
   * @throws NoSuchElementException if startLocation does not exist, or if
   *         there are no other locations that can be reached from there
   */
  @Override
  public String getFurthestDestinationFrom(String startLocation) throws NoSuchElementException {
    
    //No start location present
    if (!this.graph.containsNode(startLocation)) {
      throw new NoSuchElementException("No starting node");
    }
    
    String destination = null;
    double totalWeights = -1;
    boolean foundReachable = false;
    List<String> allLocations = new ArrayList<>(this.getListOfAllLocations());
    allLocations.remove(startLocation);
    
    
    for (String location: allLocations) {
      try {
        double currentWeight = this.graph.shortestPathCost(startLocation, location);
        foundReachable = true;
        
        if (currentWeight > totalWeights) {
          totalWeights = currentWeight;
          destination = location;
        }
      } catch (NoSuchElementException e) {
        // Skip unreachable locations
        continue;
      }
    }
    
    //Not a single reachable path from the starting node
    if (!foundReachable) {
      throw new NoSuchElementException("No reachable destinations from " + startLocation);
    }
    return destination;
  }

  
}
