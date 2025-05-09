import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Frontend - CS400 Project 1: iSongly
 * Implements the FrontendInterface.
 */
public class Frontend implements FrontendInterface {
  
  private Integer danceMin = null;
  private Integer danceMax = null;
  
  private Scanner in;
  private BackendInterface backend;

  
  
  /**
   * Constructor for Frontend.
   * @param in Scanner for reading user input.
   * @param backend BackendInterface instance.
   */
  public Frontend(Scanner in, BackendInterface backend) {
    this.in = in;
    this.backend = backend;
  }
  
  
  
  
  /**
   * Displays instructions for the syntax of user commands.  And then 
   * repeatedly gives the user an opportunity to issue new commands until
   * they enter "quit".  Uses the evaluateSingleCommand method below to
   * parse and run each command entered by the user.  If the backend ever
   * throws any exceptions, they should be caught here and reported to the
   * user.  The user should then continue to be able to issue subsequent
   * commands until they enter "quit".  This method must use the scanner
   * passed into the constructor to read commands input by the user.
   */
  public void runCommandLoop() {
    displayCommandInstructions();
    //This is for the following loop for continuous command inputs from users:
    while (true) {
      System.out.print("Please Enter Command: \n");
      String command = in.nextLine().trim();
      
      //If the user types the "quit", the program stops:
      if (command.equals("quit")) {
        System.out.print("You are now leaving.\n");
        break;
      }
      try {
        executeSingleCommand(command);
      } 
      catch (Exception e) {
        System.out.print(e.getMessage() + "\n");
      }
    }
  }
  
  
  
  
  /**
   * Displays instructions for the user to understand the syntax of commands
   * that they are able to enter.  This should be displayed once from the
   * command loop, before the first user command is read in, and then later
   * in response to the user entering the command: help.
   * 
   * The lowercase words in the following examples are keywords that the 
   * user must match exactly in their commands, while the upper case words
   * are placeholders for arguments that the user can specify.  The following
   * are examples of valid command syntax that your frontend should be able
   * to handle correctly.
   * 
   * load FILEPATH
   * danceability MAX
   * danceability MIN to MAX
   * speed MAX 
   * show MAX_COUNT
   * show most recent
   * help
   * quit
   */
  public void displayCommandInstructions() {
    System.out.print("Commands You May Enter:\n");
    System.out.print("The lowercase words are keywords that you must match, "
        + "the upper case words are placeholders for arguments that you can specify.\n");
    
    System.out.print("load FILEPATH\n");
    System.out.print("danceability MAX\n");
    System.out.print("danceability MIN to MAX\n");
    System.out.print("speed MAX\n");
    System.out.print("show MAX_COUNT\n");
    System.out.print("show most recent\n");
    System.out.print("help\n");
    System.out.print("quit\n");
  }
  
  
  
  
  
  
  /**
   * This method takes a command entered by the user as input. It parses
   * that command to determine what kind of command it is, and then makes
   * use of the backend (which was passed to the constructor) to update the
   * state of that backend.  When a show or help command are issued, this
   * method prints the appropriate results to standard out.  When a command 
   * does not follow the syntax rules described above, this method should 
   * print out an error message that describes at least one defect in the 
   * syntax of the provided command argument.
   * 
   * Some notes on the expected behavior of the different commands:
   *     load: results in backend loading data from specified path
   *     danceability: updates backend's range of songs to return
   *                 should not result in any songs being displayed
   *     speed: updates backend's filter threshold
   *                   should not result in any songs being displayed
   *     show: displays list of songs with currently set thresholds
   *           MAX_COUNT: argument limits the number of song titles displayed
   *           to the first MAX_COUNT in the list returned from backend
   *           most recent: argument displays results returned from the
   *           backend's fiveMost method
   *     help: displays command instructions
   *     quit: ends this program (handled by runCommandLoop method above)
   *           (do NOT use System.exit(), as this will interfere with tests)
   */
  public void executeSingleCommand(String command) {
    String[] commandArray = command.split(" ");

    //Let's check what is the first word in the command sentence: 
    switch (commandArray[0]) {
      //This is for load data:
      case "load":
        try {
          backend.readData(commandArray[1]);
          System.out.print("Data loaded successfully from " + commandArray[1] + "\n");
        } 
        //It is possible that the file is not successfully read, exception handling here is a must:
        catch (IOException e) {
          System.out.print("Error: Unable to load file, please check it\n");
        }
        break;
      
      //This is for danceability  
      case "danceability":
        //Upperbounds:
        if (commandArray.length == 2) {
          try {
            int max = Integer.parseInt(commandArray[1]);
            backend.getRange(null, max);
            danceMin = null;
            danceMax = max;
            System.out.print("Danceability range set to: 0 to " + max + "\n");
          } 
          //Max here can be invalid input from the user, exception handling is a must:
          catch (NumberFormatException e) {
            System.out.print("Error: Invalid number format for MAX.\n");
          }
          
        } 
        
        //Both lower and upper bounds:
        else if (commandArray.length == 4 && commandArray[2].equals("to")) {
          
          //Check if there are no bounds
          if (commandArray[1].equals("null") && commandArray[3].equals("null")) {
            backend.getRange(null, null);
            danceMin = null;
            danceMax = null;
            System.out.print("Danceability range set to all possible values.\n");
          }
          
          //This is the case for only min bound is set:
          else if (commandArray[3].equals("null")) {
            try {
              int min = Integer.parseInt(commandArray[1]);
              backend.getRange(min, null);
              danceMin = min;
              danceMax = null;
              System.out.print("Danceability range set to: " + min + " to the end of max\n");
            }
            //Min here can be invalid inout from user, needs exception handling logic:
            catch (NumberFormatException e) {
              System.out.print("Error: Invalid number format for MIN \n");
            }
          }
          
          //This is the case for both bounds set:
          else {
            try {
              int min = Integer.parseInt(commandArray[1]);
              int max = Integer.parseInt(commandArray[3]);
              backend.getRange(min, max);
              danceMin = min;
              danceMax = max;
              System.out.print("Danceability range set to: " + min + " to " + max + "\n");
            }
            //Either min or max can be invalid input from the user, exception handling here is a must: 
            catch (NumberFormatException e) {
              System.out.print("Error: Invalid number format for MIN or MAX.\n");
            }
          }
          
        } else {
          System.out.print("Error: Invalid danceability command format. "
              + "Use 'danceability MAX' or 'danceability MIN to MAX'.\n");
        }
        break;
        
      //Speed command: 
      case "speed":

        //The speed filter here is null:
        if (commandArray.length == 2 && commandArray[1].equals("null")) {
          backend.filterSongs(null);
          System.out.print("No speed threshold set\n");
        }

        //The speed filter here is not null:
        else if (commandArray.length == 2) {
          try {
            int threshold = Integer.parseInt(commandArray[1]);
            backend.filterSongs(threshold);
            System.out.print("Speed filter set to below " + threshold + "\n");
          }
          //threshold input from user can be invalid, so exception handling is necessary:
          catch (NumberFormatException e) {
            System.out.print("Error: Invalid number format for MAX.\n");
          }
        }
        else {
          System.out.print("Error: Invalid speed command format. "
              + "Use 'speed MAX'.\n");
        }
        break;
        
      //Show command:
      case "show":
        //This is for dealing with show most recent:
        if (commandArray.length == 3 && commandArray[1].equals("most") &&
        commandArray[2].equals("recent")) {
          
          List<String> recentSongs = backend.fiveMost();
          System.out.print("Most recent songs: " + recentSongs + "\n");
        } 

        //This is for dealing with show maxcount:
        else if (commandArray.length == 2) {
          try {
            int maxCount = Integer.parseInt(commandArray[1]);
            List<String> songs = backend.getRange(danceMin, danceMax);
            System.out.print("Displaying up to " + maxCount + " songs: " + 
            songs.subList(0, Math.min(maxCount, songs.size())) + "\n");
          } 

          //Maxcount here can be invalid input from user, needs for exception handling:
          catch (NumberFormatException e) {
            System.out.print("Error: Invalid number format for MAX_COUNT.\n");
          }
        } 
        else {
          System.out.print("Error: Invalid show command format. "
              + "Use 'show MAX_COUNT' or 'show most recent'.\n");
        }
        break;
      
      case "help":
        displayCommandInstructions();
        break;
      
      default:
        System.out.print("Error: Unknown command. Type 'help' for a list of commands.\n");
        break;
    }
  
  }
  
  
  
}
