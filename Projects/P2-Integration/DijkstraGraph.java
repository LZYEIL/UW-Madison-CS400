import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new HashtableMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
      
      //Check if start or end is valid data in the graph
      if (!this.nodes.containsKey(start) || !this.nodes.containsKey(end)) {
        throw new NoSuchElementException("There is no start or end data in this graph");
      }
      
      //Following the pseudocode given in lecture slides:
      PriorityQueue<SearchNode> pq = new PriorityQueue<>();
      Node startNode = this.nodes.get(start);
      pq.add(new SearchNode(startNode, 0.0, null));
      
      //For keeping track of the visted nodes:
      MapADT<NodeType, NodeType> visited = new HashtableMap<>();
      
      while (!pq.isEmpty()) {
        SearchNode curr = pq.poll();
        
        //If we find the end, we can terminate the method early:
        if (curr.node.data.equals(end)) {
          return curr;
        }
        
        //Follows the lecture slide, mark current as visited and check for 
        //its neighbours for next iteration:
        if (!visited.containsKey(curr.node.data)) {
          visited.put(curr.node.data, curr.node.data);
          
          for (Edge edge: curr.node.edgesLeaving) {
            if (!visited.containsKey(edge.successor.data)) {
              pq.add(new SearchNode(edge.successor, curr.cost + edge.data.doubleValue(), curr));
            }
          }
        }
        
      }
      
      //There is no path from the start to the end
      throw new NoSuchElementException("No path from the start to end");
    }

    
    
    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
      
      //Initialize an ArrayList for return:
      List<NodeType> pathData = new ArrayList<>();
      
      try {
        SearchNode endNode = this.computeShortestPath(start, end);
        
        //Trace back through the chain of SearchNode from (end to start)
        while (endNode != null) {
          pathData.add(0, endNode.node.data);
          endNode = endNode.predecessor;
        }
        return pathData;
      }
      //Deal with the exception propagation from the computeShortestPath()
      catch (NoSuchElementException e) {
        throw new NoSuchElementException("Please check the validity of the path or the two end nodes");
      }
	}
    
    

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
      //The endNode already carries the information of the total cost:
      try {
        SearchNode endNode = this.computeShortestPath(start, end);
        return endNode.cost;
      }
      
      //Deal with the exception propagation from the computeShortestPath()
      catch (NoSuchElementException e) {
        throw new NoSuchElementException("Please check the validity of the path or the two end nodes");
      }
    }
    
    
    
    
    /**
     * Makes use of an example in lecture, Florian's slides page 4
     */
    @Test
    public void test1() {
      //create graph, nodes, and edges
      DijkstraGraph<Character, Integer> test = new DijkstraGraph<>();
      
      test.insertNode('A');
      test.insertNode('B');
      test.insertNode('C');
      test.insertNode('D');
      test.insertNode('E');
      test.insertNode('F');
      test.insertNode('G');
      test.insertNode('H');
      
      test.insertEdge('A', 'B', 4);
      test.insertEdge('A', 'C', 2);
      test.insertEdge('A', 'E', 15);
      test.insertEdge('B', 'D', 1);
      test.insertEdge('B', 'E', 10);
      test.insertEdge('C', 'D', 5);
      test.insertEdge('D', 'E', 3);
      test.insertEdge('D', 'F', 0);
      test.insertEdge('F', 'D', 2);
      test.insertEdge('F', 'H', 4);
      test.insertEdge('G', 'H', 4);
      
      DijkstraGraph<Character, Integer>.SearchNode n1 = test.computeShortestPath('A', 'B');
      
      // Verify the end node is B
      Assertions.assertEquals('B', n1.node.data);
      // Verify the total cost is 4
      Assertions.assertEquals(4.0, n1.cost, 0.001);
      // Verify the predecessor is A
      Assertions.assertEquals('A', n1.predecessor.node.data);
      // Verify A's cost is 0 and has no predecessor
      Assertions.assertEquals(0.0, n1.predecessor.cost, 0.001);
      Assertions.assertNull(n1.predecessor.predecessor);
    }
    
    
    
    
    /**
     * Makes use of an example in lecture, Florian's slides page 4
     * Check the cost and sequence of data along the shortest path 
     * between a different start and end node
     */
    @Test
    public void test2() {
      //create graph, nodes, and edges
      DijkstraGraph<Character, Integer> test = new DijkstraGraph<>();
      
      test.insertNode('A');
      test.insertNode('B');
      test.insertNode('C');
      test.insertNode('D');
      test.insertNode('E');
      test.insertNode('F');
      test.insertNode('G');
      test.insertNode('H');
      
      test.insertEdge('A', 'B', 4);
      test.insertEdge('A', 'C', 2);
      test.insertEdge('A', 'E', 15);
      test.insertEdge('B', 'D', 1);
      test.insertEdge('B', 'E', 10);
      test.insertEdge('C', 'D', 5);
      test.insertEdge('D', 'E', 3);
      test.insertEdge('D', 'F', 0);
      test.insertEdge('F', 'D', 2);
      test.insertEdge('F', 'H', 4);
      test.insertEdge('G', 'H', 4);
      
      //Check for the data:
      //AB
      List<Character> arr1 = test.shortestPathData('A', 'B');
      Assertions.assertEquals(arr1.size(), 2);
      Assertions.assertEquals(arr1.get(0), 'A');
      Assertions.assertEquals(arr1.get(1), 'B');
      
      //AC
      List<Character> arr2 = test.shortestPathData('A', 'C');
      Assertions.assertEquals(arr2.size(), 2);
      Assertions.assertEquals(arr2.get(0), 'A');
      Assertions.assertEquals(arr2.get(1), 'C');
      
      //AH
      List<Character> arr3 = test.shortestPathData('A', 'H');
      Assertions.assertEquals(arr3.size(), 5);
      Assertions.assertEquals(arr3.get(0), 'A');
      Assertions.assertEquals(arr3.get(1), 'B');
      Assertions.assertEquals(arr3.get(2), 'D');
      Assertions.assertEquals(arr3.get(3), 'F');
      Assertions.assertEquals(arr3.get(4), 'H');
 
      
      //Check for the cost:
      //AB
      double abR = test.shortestPathCost('A', 'B');
      Assertions.assertEquals(abR, 4.0);
      
      //AC
      double acR = test.shortestPathCost('A', 'C');
      Assertions.assertEquals(acR, 2.0);
      
      //AH
      double ahR = test.shortestPathCost('A', 'H');
      Assertions.assertEquals(ahR, 9.0);
    }
    
    
    
    /**
     * Makes use of an example in lecture, Florian's slides page 4
     * Check the validity of the exception cases
     */
    @Test
    public void test3() {
      //create graph, nodes, and edges
      DijkstraGraph<Character, Integer> test = new DijkstraGraph<>();
      
      test.insertNode('A');
      test.insertNode('B');
      test.insertNode('C');
      test.insertNode('D');
      test.insertNode('E');
      test.insertNode('F');
      test.insertNode('G');
      test.insertNode('H');
      
      test.insertEdge('A', 'B', 4);
      test.insertEdge('A', 'C', 2);
      test.insertEdge('A', 'E', 15);
      test.insertEdge('B', 'D', 1);
      test.insertEdge('B', 'E', 10);
      test.insertEdge('C', 'D', 5);
      test.insertEdge('D', 'E', 3);
      test.insertEdge('D', 'F', 0);
      test.insertEdge('F', 'D', 2);
      test.insertEdge('F', 'H', 4);
      test.insertEdge('G', 'H', 4);
      
      //No start (on pathData):
      try {
        test.shortestPathData('M', 'B');
      }
      catch (NoSuchElementException e) {
        Assertions.assertTrue(true);
      }
      
      
      //No start (on pathCost):
      try {
        test.shortestPathCost('M', 'B');
      }
      catch (NoSuchElementException e) {
        Assertions.assertTrue(true);
      }
      
      
      //No end (on pathData):
      try {
        test.shortestPathData('A', 'K');
      }
      catch (NoSuchElementException e) {
        Assertions.assertTrue(true);
      }
      
      
      //No end (on pathCost):
      try {
        test.shortestPathCost('A', 'K');
      }
      catch (NoSuchElementException e) {
        Assertions.assertTrue(true);
      }
      
      
      //No path from start to end:
      try {
        test.shortestPathCost('A', 'G');
      }
      catch (NoSuchElementException e) {
        Assertions.assertTrue(true);
      }
      
    }
    
}
