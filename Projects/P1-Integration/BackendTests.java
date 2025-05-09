
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.util.List;

/**
 * A class for testing the methods of the Backend class.
 */
public class BackendTests {
    /**
     * Tests functionality of readData method. 
     * Verifies that exceptions are correctly thrown/not thrown and 
     * verifies that Song instances are added to the tree. 
     */
    @Test
    public void backendTest1() {
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        Backend b = new Backend(tree);

        // Test that an IOException is properly thrown
        try {
            b.readData("definitelyDoesNotExist.txt");
            Assertions.fail("readData failed to correctly throw an IOException!");
        } catch (IOException e) {
            // This part of the test passes
        }

        // Test that the last song in songs.csv is found in tree
        try {
            b.readData("songs.csv");
        } catch (IOException e) {
            // In this case, we aren't expecting an exception
            Assertions.fail("IOException! Check that songs.csv file is present!");
        }
        Song lastInCSV = new Song("Kills You Slowly",
                                  "The Chainsmokers",
                                  "electropop",
                                  2019,
                                  150,
                                  44,
                                  70,
                                  -9,
                                  13,
                                  (s1, s2) -> s1.getDanceability() - s2.getDanceability());
        Assertions.assertEquals(lastInCSV.compareTo(
            ((Tree_Placeholder) tree).lastAddedSong), 
            0, "The Song is not in the tree!");
    }

    /**
     * Tests functionality of getRange and filterSongs methods. 
     * Verifies that getRange and filterSongs correctly include/exclude songs that
     * fall inside/outside the bounds set by the methods. 
     */
    @Test
        public void backendTest2() {
            IterableSortedCollection<Song> tree = new Tree_Placeholder();
            Backend b = new Backend(tree);

            // Load the last song of songs.csv into the tree.
            // We know that the last song has the following relevant attributes
            // Danceability: 70
            // Speed / BPM: 150
            // Other songs in tree have danceability values of 43, 81, 77.
            // Other songs in tree have speed / BPM values of 148, 103, and 119
            try {
                b.readData("songs.csv");
            } catch (IOException e) {
                // Not expecting this exception to be thrown
                Assertions.fail("IOException! Check that songs.csv file is present!");
            }

            // Test the getRange method
            // Case 1: Danceability is between non-null bounds
            Assertions.assertEquals(b.getRange(69, 70).size(), 1, "Wrong number of songs!");
            // // Case 2: Danceability is not between non-null bounds
            Assertions.assertEquals(b.getRange(71, 72).size(), 0, "Wrong number of songs!");
            // // Case 3: Danceability is between bounds; upper bound is null
            Assertions.assertEquals(b.getRange(0, null).size(), 4, "Wrong number of songs!");
            // // Case 4: Danceability isn't between bounds; upper bound is null
            Assertions.assertEquals(b.getRange(81, null).size(), 1, "Wrong number of songs!");
            // // Case 5: Danceability is between bounds; lower bound is null
            Assertions.assertEquals(b.getRange(null, 100).size(), 4, "Wrong number of songs!");
            // // Case 6: Danceability isn't between bounds; lower bound is null
            Assertions.assertEquals(b.getRange(null, 65).size(), 1, "Wrong number of songs!");
            // // Case 7: Danceability isn't bounded
            Assertions.assertEquals(b.getRange(null, null).size(), 4, "Wrong number of songs!");

            // Test the filterSongs method
            b.getRange(0, 100); // Setting bounds 
                                // Case 1: threshold is above the speed of all songs
            Assertions.assertEquals(b.filterSongs(151).size(), 4, "Wrong number of songs!");
            // Case 2: threshold is equal the speed of a song
            Assertions.assertEquals(b.filterSongs(150).size(), 3, "Wrong number of songs!");
            // Case 3: threshold is below the speed of all songs
            Assertions.assertEquals(b.filterSongs(0).size(), 0, "Wrong number of songs!");
        }

    /**
     * Tests functionality of fiveMost method. 
     * Verifies that the correct number of song titles are returned by fiveMost,
     * including cases when no danceability range or speed threshold has been set
     * and when both danceability range and speed threshold have been set. 
     */
    @Test
        public void backendTest3() {
            IterableSortedCollection<Song> tree = new Tree_Placeholder();
            Backend b = new Backend(tree);

            // Test first with just 3 songs in the Tree_Placeholder instance
            Assertions.assertEquals(b.fiveMost().size(), 3, "Wrong number of songs!");

            // Load the last song of songs.csv into the tree.
            // We know that the last song has the following relevant attributes
            // Title: Kill You Slowly
            // Danceability: 70
            // Speed / BPM: 150
            try {
                b.readData("songs.csv");
            } catch (IOException e) {
                // Not expecting this exception to be thrown
                Assertions.fail("IOException! Check that songs.csv file is present!");
            }

            // // Case 1: there are no bounds (getRange and filterSongs don't apply)
            Assertions.assertEquals(b.fiveMost().size(), 4,	"Wrong number of songs!");
            // // Case 2: there are bounds, restricting fiveMost's output
            b.getRange(40,80);
            b.filterSongs(149);
            Assertions.assertEquals(b.fiveMost().size(), 2, "Wrong number of songs!");
        }
}
