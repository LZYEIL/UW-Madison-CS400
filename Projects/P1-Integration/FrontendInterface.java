import java.util.Scanner;

/**
 * FrontendInterface - CS400 Project 1: iSongly
 */
public interface FrontendInterface {

    //public Frontend(Scanner in, BackendInterface backend)
    // Your constructor must have the signature above. This class must rely
    // rely only on the provided Scanner to read input from the user, and must
    // use the provided BackendInterface reference to compute the results
    // of an command requested by the user.

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
    public void runCommandLoop();
    
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
    public void displayCommandInstructions();

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
    public void executeSingleCommand(String command);
    
}
