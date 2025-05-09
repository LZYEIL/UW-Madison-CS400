import java.util.List;
import java.io.IOException;

/**
 * BackendInterface - CS400 Project 1: iSongly
 */
public interface BackendInterface {

    //public Backend(IterableSortedCollection<Song> tree)
    // Your constructor must have the signature above. All methods below must
    // use the provided tree to store, sort, and iterate through songs. This
    // will enable you to create some tests that use the placeholder tree, and
    // others that make use of a working tree, depending on what is passed
    // into this constructor.

    /**
     * Loads data from the .csv file referenced by filename.  You can rely
     * on the exact headers found in the provided songs.csv, but you should
     * not rely on them always being presented in this order or on there
     * not being additional columns describing other song qualities.
     * After reading songs from the file, the songs are inserted into
     * the tree passed to this backend' constructor.  Don't forget to 
     * create a Comparator to pass to the constructor for each Song object that
     * you create.  This will be used to store these songs in order within your
     * tree, and to retrieve them by danceability range in the getRange method.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    public void readData(String filename) throws IOException;

    /**
     * Retrieves a list of song titles from the tree passed to the contructor.
     * The songs should be ordered by the songs' danceability, and fall within
     * the specified range of danceability values.  This danceability range will
     * also be used by future calls to filterSongs and getFiveMost.
     *
     * If a speed filter has been set using the filterSongs method
     * below, then only songs that pass that filter should be included in the
     * list of titles returned by this method.
     *
     * When null is passed as either the low or high argument to this method,
     * that end of the range is understood to be unbounded.  For example, a 
     * argument for the hight parameter means that there is no maximum 
     * danceability to include in the returned list.
     *
     * @param low is the minimum danceability of songs in the returned list
     * @param high is the maximum danceability of songs in the returned list
     * @return List of titles for all songs from low to high that pass any
     *     set filter, or an empty list when no such songs can be found
     */
    public List<String> getRange(Integer low, Integer high);

    /**
     * Retrieves a list of song titles that have a speed that is
     * smaller than the specified threshold.  Similar to the getRange
     * method: this list of song titles should be ordered by the songs'
     * danceability, and should only include songs that fall within the specified
     * range of danceability values that was established by the most recent call
     * to getRange.  If getRange has not previously been called, then no low
     * or high danceability bound should be used.  The filter set by this method
     * will be used by future calls to the getRange and fiveMost methods.
     *
     * When null is passed as the threshold to this method, then no 
     * speed threshold should be used.  This clears the filter.
     *
     * @param threshold filters returned song titles to only include songs that
     *     have a speed that is smaller than this threshold.
     * @return List of titles for songs that meet this filter requirement and
     *     are within any previously set danceability range, or an empty list 
     *     when no such songs can be found
     */
    public List<String> filterSongs(Integer threshold);

    /**
     * This method returns a list of song titles representing the five
     * most recent songs that both fall within any attribute range specified
     * by the most recent call to getRange, and conform to any filter set by
     * the most recent call to filteredSongs.  The order of the song titles 
     * in this returned list is up to you.
     *
     * If fewer than five such songs exist, return all of them.  And return an
     * empty list when there are no such songs.
     *
     * @return List of five most recent song titles
     */
    public List<String> fiveMost();
}
