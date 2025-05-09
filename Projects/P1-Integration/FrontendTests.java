import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



/**
 * This is the tester class of Frontend
 */
public class FrontendTests {
  
  
  /**
   * The first frontend tester method
   * Test for unknown commands and quit
   */
  @Test
  public void frontendTest1() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder(); 
    Backend_Placeholder bknd = new Backend_Placeholder(tree); 
    
    //Create tester object
    TextUITester tester = new TextUITester("lkm\nquit\n");
    Scanner scn = new Scanner(System.in);
    Frontend test1 = new Frontend(scn, bknd);
    
    test1.runCommandLoop(); //RunCommandLoop
    String output = tester.checkOutput();
    

    String expectedOutput1 = "Commands You May Enter:\n"
        + "The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n"
        + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
        + "MAX_COUNT\nshow most recent\nhelp\nquit\n";
    
    String expectedOutput2 = "Please Enter Command: \nError: Unknown command. "
        + "Type 'help' for a list of commands.\nPlease Enter Command: \nYou are now leaving.\n";
    
    String expectedOutput = expectedOutput1 + expectedOutput2;
    
    Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  /**
   * The second front end tester method
   * Test for help
   */
  @Test
  public void frontendTest2() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder(); 
    Backend_Placeholder bknd = new Backend_Placeholder(tree); 
    
    //Create tester object
    TextUITester tester = new TextUITester("help\nquit\n");
    Scanner scn = new Scanner(System.in);
    Frontend test2 = new Frontend(scn, bknd);
    
    test2.runCommandLoop(); //RunCommandLoop
    String output = tester.checkOutput();
    
    String expectedOutput1 = "Commands You May Enter:\n"
        + "The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n"
        + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
        + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
    
    
    String expectedOutput2 = "Commands You May Enter:\n"
    + "The lowercase words are keywords that you must match, "
    + "the upper case words are placeholders for arguments that you can specify.\n"
    + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
    + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \nYou are now leaving.\n";
    
    String expectedOutput = expectedOutput1 + expectedOutput2;
    
    
    Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  /**
   * The third front end tester method
   * Test for load
   */
  @Test
  public void frontendTest3() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder(); 
    Backend_Placeholder bknd = new Backend_Placeholder(tree); 
    
    //Create tester object
    TextUITester tester = new TextUITester("load some\nquit\n");
    Scanner scn = new Scanner(System.in);
    Frontend test3 = new Frontend(scn, bknd);
    
    test3.runCommandLoop(); //RunCommandLoop
    String output = tester.checkOutput();
    
    String expectedOutput1 = "Commands You May Enter:\n"
        + "The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n"
        + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
        + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
    
    String expectedOutput2 = "Data loaded successfully from some\nPlease Enter Command: "
        + "\nYou are now leaving.\n";
    
    String expectedOutput = expectedOutput1 + expectedOutput2;
    
    Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  /**
   * The fourth front end tester method
   * Test for danceability
   */
  @Test
  public void frontendTest4() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder(); 
    Backend_Placeholder bknd = new Backend_Placeholder(tree); 
    
    //Create tester object
    TextUITester tester = new TextUITester("danceability null to null\ndanceability 3\ndanceability 3 to 5\n"
        + "danceability dh\n danceability s to k\ndanceability g h y u\nquit\n");
    
    Scanner scn = new Scanner(System.in);
    Frontend test4 = new Frontend(scn, bknd);
    
    test4.runCommandLoop(); //RunCommandLoop
    String output = tester.checkOutput();
    
    String expectedOutput1 = "Commands You May Enter:\n"
        + "The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n"
        + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
        + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
    
    String expectedOutput2 = "Danceability range set to all possible values.\nPlease Enter Command: \n"
        + "Danceability range set to: 0 to 3\nPlease Enter Command: \n"
        + "Danceability range set to: 3 to 5\nPlease Enter Command: \n"
        + "Error: Invalid number format for MAX.\nPlease Enter Command: \n"
        + "Error: Invalid number format for MIN or MAX.\nPlease Enter Command: \n"
        + "Error: Invalid danceability command format. "
        + "Use 'danceability MAX' or 'danceability MIN to MAX'.\nPlease Enter Command: \n"
        + "You are now leaving.\n";
    
    String expectedOutput = expectedOutput1 + expectedOutput2;
    Assertions.assertEquals(output,expectedOutput);
    
  }
  
  
  
  /**
   * The fifth front end tester method
   * Test for speed command
   */
  @Test
  public void frontendTest5() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder(); 
    Backend_Placeholder bknd = new Backend_Placeholder(tree); 
    
    //Create tester object
    TextUITester tester = new TextUITester("speed null\nspeed 20\nspeed km\nspeed sh ju h\nquit\n");
    Scanner scn = new Scanner(System.in);
    Frontend test5 = new Frontend(scn, bknd);
    
    test5.runCommandLoop(); //RunCommandLoop
    String output = tester.checkOutput();
    
    String expectedOutput1 = "Commands You May Enter:\n"
        + "The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n"
        + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
        + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
    
    
    String expectedOutput2 = "No speed threshold set\nPlease Enter Command: \n"
        + "Speed filter set to below 20\nPlease Enter Command: \n"
        + "Error: Invalid number format for MAX.\nPlease Enter Command: \n"
        + "Error: Invalid speed command format. "
        + "Use 'speed MAX'.\nPlease Enter Command: \nYou are now leaving.\n";
    
    String expectedOutput = expectedOutput1 + expectedOutput2;
    Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  /**
   * The sixth front end tester method
   * Test for show command
   */
  @Test
  public void frontendTest6() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder(); 
    Backend_Placeholder bknd = new Backend_Placeholder(tree); 
    
    //Create tester object
    TextUITester tester = new TextUITester("show most recent\nshow 2\nshow k\nshow k k k\nquit\n");
    Scanner scn = new Scanner(System.in);
    Frontend test6 = new Frontend(scn, bknd);
    
    test6.runCommandLoop(); //RunCommandLoop
    String output = tester.checkOutput();
    
    
    String expectedOutput1 = "Commands You May Enter:\n"
        + "The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n"
        + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
        + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
    
    String expectedOutput2 = "Most recent songs: [A L I E N S, BO$$, Cake By The Ocean]\n"
        + "Please Enter Command: \nDisplaying up to 2 songs: []\nPlease Enter Command: \n"
        + "Error: Invalid number format for MAX_COUNT.\n"
        + "Please Enter Command: \n"
        + "Error: Invalid show command format. "
        + "Use 'show MAX_COUNT' or 'show most recent'.\nPlease Enter Command: \n"
        + "You are now leaving.\n";
    
    String expectedOutput = expectedOutput1 + expectedOutput2;
    
    Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  /////////////////////////////////////////////////////////////////////////
  
  
  /**
   * Tests the integration between the frontend and backend by verifying that
   * the frontend can load data from the backend and display the correct message.
   */
  @Test
  public void testFrontendBackendIntegrationLoadData() {
      // Step 1: Initialize the backend and frontend
      IterableSortedCollection<Song> tree = new IterableRedBlackTree<>(); // Actual implementation
      Backend backend = new Backend(tree); // Use actual backend, not placeholder
      TextUITester tester = new TextUITester("load songs.csv\nquit\n");
      Scanner scn = new Scanner(System.in);
      Frontend frontend = new Frontend(scn, backend);

      // Step 2: Run the command loop
      frontend.runCommandLoop();
      String output = tester.checkOutput();

      // Step 3: Verify the output contains the success message
      String expectedOutput1 = "Commands You May Enter:\n"
          + "The lowercase words are keywords that you must match, "
          + "the upper case words are placeholders for arguments that you can specify.\n"
          + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
          + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
      
      String expectedOutput2 = "Data loaded successfully from songs.csv\nPlease Enter Command: "
          + "\nYou are now leaving.\n";
      
      String expectedOutput = expectedOutput1 + expectedOutput2;
      
      //If it passes, it indicates that both frontend diplays the correct message
      //and the backend has successfully dealt with the import
      Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  
  /**
   * Tests the integration between the frontend and backend by verifying that
   * the frontend can set a danceability range and retrieve filtered songs.
   */
  @Test
  public void testFrontendBackendIntegrationDanceabilityFilter() {
      // Step 1: Initialize the backend and frontend
      IterableSortedCollection<Song> tree = new IterableRedBlackTree<>(); // Actual implementation
      Backend backend = new Backend(tree); // Use actual backend, not placeholder
      TextUITester tester = new TextUITester("load songs.csv\ndanceability 70 to 71\nshow 3\nquit\n");
      Scanner scn = new Scanner(System.in);
      Frontend frontend = new Frontend(scn, backend);

      // Step 2: Run the command loop
      frontend.runCommandLoop();
      String output = tester.checkOutput();

      // Step 3: Verify the output contains the danceability range message
      String expectedOutput1 = "Commands You May Enter:\n"
          + "The lowercase words are keywords that you must match, "
          + "the upper case words are placeholders for arguments that you can specify.\n"
          + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
          + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
      
      String expectedOutput2 = "Data loaded successfully from songs.csv\nPlease Enter Command: \n" +
      "Danceability range set to: 70 to 71\nPlease Enter Command: \n"
          + "Displaying up to 3 songs: [Kills You Slowly, No Guidance (feat. Drake), My My My!]\n" + 
      "Please Enter Command: \nYou are now leaving.\n";
      
      //Check frontend/backend integration functionality
      String expectedOutput = expectedOutput1 + expectedOutput2;
      Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  
  /**
   * Tests the integration between the frontend and backend by verifying that
   * the frontend can set a speed threshold and retrieve filtered songs.
   */
  @Test
  public void testFrontendBackendIntegrationSpeedFilter() {
      // Step 1: Initialize the backend and frontend
      IterableSortedCollection<Song> tree = new IterableRedBlackTree<>(); // Actual implementation
      Backend backend = new Backend(tree); // Use actual backend, not placeholder
      TextUITester tester = new TextUITester("load songs.csv\nspeed 70\nshow 50\nquit\n");
      Scanner scn = new Scanner(System.in);
      Frontend frontend = new Frontend(scn, backend);

      // Step 2: Run the command loop
      frontend.runCommandLoop();
      String output = tester.checkOutput();

      // Step 3: Verify the output contains the danceability range message
      String expectedOutput1 = "Commands You May Enter:\n"
          + "The lowercase words are keywords that you must match, "
          + "the upper case words are placeholders for arguments that you can specify.\n"
          + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
          + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
      
      String expectedOutput2 = "Data loaded successfully from songs.csv\nPlease Enter Command: \n" +
      "Speed filter set to below 70\nPlease Enter Command: \n"
          + "Displaying up to 50 songs: [Million Years Ago, You Lost Me, 1+1, Baby]\n" + 
      "Please Enter Command: \nYou are now leaving.\n";
      
      //Check frontend/backend integration functionality
      String expectedOutput = expectedOutput1 + expectedOutput2;
      Assertions.assertEquals(output,expectedOutput);
  }
  
  
  
  
  /**
   * Tests the integration between the frontend and backend by verifying that
   * the frontend can set a speed threshold + danceability range and retrieve filtered songs.
   */
  @Test
  public void testFrontendBackendIntegrationComprehensiveFilter() {
      // Step 1: Initialize the backend and frontend
      IterableSortedCollection<Song> tree = new IterableRedBlackTree<>(); // Actual implementation
      Backend backend = new Backend(tree); // Use actual backend, not placeholder
      TextUITester tester = new TextUITester("load songs.csv\nspeed 70\ndanceability 50 to 100\nshow most recent\nquit\n");
      Scanner scn = new Scanner(System.in);
      Frontend frontend = new Frontend(scn, backend);

      // Step 2: Run the command loop
      frontend.runCommandLoop();
      String output = tester.checkOutput();

      // Step 3: Verify the output contains the danceability range message
      String expectedOutput1 = "Commands You May Enter:\n"
          + "The lowercase words are keywords that you must match, "
          + "the upper case words are placeholders for arguments that you can specify.\n"
          + "load FILEPATH\ndanceability MAX\ndanceability MIN to MAX\nspeed MAX\nshow "
          + "MAX_COUNT\nshow most recent\nhelp\nquit\nPlease Enter Command: \n";
      
      String expectedOutput2 = "Data loaded successfully from songs.csv\nPlease Enter Command: \n" +
      "Speed filter set to below 70\nPlease Enter Command: \n"
          + "Danceability range set to: 50 to 100\nPlease Enter Command: \n" + "Most recent songs: [Baby]\n" + 
      "Please Enter Command: \nYou are now leaving.\n";
      
      //Check frontend/backend integration functionality
      String expectedOutput = expectedOutput1 + expectedOutput2;
      Assertions.assertEquals(output,expectedOutput);
  }
}
