
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * A class to manage, arrange, and filter Song data. 
 */
public class Backend implements BackendInterface {
    private IterableSortedCollection<Song> tree;
    private Integer bounds[];
    private Integer threshold;

    /**
     * Backend constructor initializes the tree with the given argument and 
     * sets the inital values for bounds and threshold. 
     * @param tree is the tree the Songs are to be stored in
     */
    public Backend(IterableSortedCollection<Song> tree) {
        this.tree = tree;
        bounds = new Integer[2];
        bounds[0] = null; bounds[1] = null;
        threshold = null;
    }

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
    @Override
    public void readData(String filename) throws IOException {
        Scanner myScanner;
        try {
            // Create Scanner object with contents at filename as its input
            myScanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new IOException("The file doesn't exist!");
        } 

        // Check if the file at filename is empty
        String header = myScanner.nextLine();
        if (header.trim().equals("")) throw new IOException("The file is empty!");

        // Get the header and create an array of the headers in order
        String headers[] = lineDecomp(header);

        while (myScanner.hasNextLine()) {
            // Extract the parameters from one line of the CSV file
            String words[] = lineDecomp(myScanner.nextLine());
            String title = words[argPosition(headers, "title")];
            String artist = words[argPosition(headers, "artist")];
            String genres = words[argPosition(headers, "top genre")];
            int year = 
                Integer.parseInt(words[argPosition(headers, "year")]);
            int bpm = 
                Integer.parseInt(words[argPosition(headers, "bpm")]);
            int energy = 
                Integer.parseInt(words[argPosition(headers, "nrgy")]);
            int danceability = 
                Integer.parseInt(words[argPosition(headers, "dnce")]);
            int loudness = 
                Integer.parseInt(words[argPosition(headers, "dB")]);
            int liveness = 
                Integer.parseInt(words[argPosition(headers, "live")]);

            Song s = new Song(title, 
                              artist, 
                              genres, 
                              year, 
                              bpm, 
                              energy, 
                              danceability,
                              loudness, 
                              liveness,
                              (s1, s2) -> s1.getDanceability() - s2.getDanceability());
            tree.insert(s);
        }
    }

    /**
     * Private helper method to decompose a line of a CSV file into its 
     * constituent comma-separated values. 
     * Returns the values as an array of Strings.  
     * @param line is the full line of the CSV file
     * @return the values extracted from the line
     */
    private String[] lineDecomp(String line) {
        ArrayList<String> words = new ArrayList<String>();
        int numDQs = 0; // Refers to the number of double-quotations
        // Refers to the accumulated String. This corresponds to a single value in the CSV
        String acc = "";


        for (int i = 0; i < line.length(); i++) {
            String c = line.substring(i, i + 1); // A single character in the line of text 

            // If the character c is the last one in the line
            if (i == line.length() - 1) { 
                // The last character is a double quotation (") 
                // and the number of double quotations is odd
                if (c.equals("\"") && numDQs % 2 == 1) {
                    words.add(acc); // Exclude the "
                } else {
                    acc += c;
                    words.add(acc);
                }
                acc = ""; // Reset the accumulated value 
            } else if (c.equals(",")) { // Character is a comma
                if (numDQs % 2 == 0) {
                    words.add(acc);
                    acc = "";
                    numDQs = 0;
                } else {
                    // If we have an odd number of DQs, the comma is part of the value
                    acc += ",";
                }
            } else if (c.equals("\"")) { // Character is a double quotation mark
                // Check if the character is followed by another double quotation mark
                if (line.substring(i + 1, i + 2).equals("\"")) { 
                    acc += "\"";
                    numDQs += 2;
                    i++;
                } else {
                    numDQs++;
                }
            } else { // Character is normal (not comma or double quotation)
                acc += c;
            }
        }
        // Add acc to words if acc isn't empty
        if (!acc.equals("")) {
            words.add(acc);
            acc = "";
        }

        // Put the values in words into an array of Strings
        String output[] = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            output[i] = words.get(i);
        }
        return output;
    }

    /**
     * Private helper method to return the index of the specified 
     * header/argument name in the sequence of header values. 
     * For example, if the header of the CSV file is title,age,bpm...
     * then argPosition(headers, "title") should return 0,
     * and argPosition(headers, "age") should return 1, and so on.
     * @param headers is an array of the header fields of the CSV file
     * @param argName is the name of the field concerned
     * @return
     */
    private int argPosition(String[] headers, String argName) {
        for (int i = 0; i < headers.length; i++) {
            if (argName.equals(headers[i])) {
                return i;
            }
        }
        return -1;
    }

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
    @Override 
    public List<String> getRange(Integer low, Integer high) {
        bounds[0] = low;
        bounds[1] = high;

        // Configure the iterators
        this.tree.setIteratorMin(boundingSong(bounds[0]));
        this.tree.setIteratorMax(boundingSong(bounds[1]));

        return getTitles();
    }

    /**
     * Helper method creating a Song object used by the setIterator methods.
     */
    private Comparable<Song> boundingSong(Integer danceabilityBound) {
        if (danceabilityBound == null) return null;
        return new Song(null, null, null, 0, 0, 0, danceabilityBound, 0, 0, 
            (s1, s2) -> s1.getDanceability() - s2.getDanceability());
    }

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
    @Override 
    public List<String> filterSongs(Integer threshold) {
        this.threshold = threshold;
        return getTitles();
    }

    /** 
     * Private helper method to collect and return all the Songs that fall 
     * within the danceability bounds and the speed threshold. 
     * @return a List of all the songs that satisfy these conditions
     */
    private List<Song> getSongs() {
        List<Song> songs = new ArrayList<Song>();
        for (Song s : tree) {
            if (threshold == null) {
                songs.add(s);
            } else if (s.getBPM() < threshold) {
                songs.add(s);
            }
        }
        return songs;
    }

    /** 
     * Private helper method to collect and return all the titles of the Songs
     * that fall within the danceability bounds and the speed threshold. Uses
     * the getSongs method, and extracts the title from each song. 
     * @return a List of all the songs' titles that satisfy these conditions
     */
    private List<String> getTitles() {
        List<Song> songs = getSongs();
        List<String> titles = new ArrayList<String>(songs.size());
        for (Song s : songs) {
            titles.add(s.getTitle());
        }
        return titles;
    }

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
    @Override 
    public List<String> fiveMost() {
        List<String> top5 = new ArrayList<String>(5);
        List<Song> songs = getSongs();

        // Find the most recent, 2nd most recent, 3rd most recent, etc., 
        // removing the chosen element from titles with each pass. 
        // The code in the outer loop is run 5 times because we need to find and extract the top 
        // song in the tree 5 times. 
        for (int i = 0; i < 5; i++) {
            int value = 0;
            int index = -1;
            for (int j = 0; j < songs.size(); j++) {
                if (index == -1 || songs.get(j).getYear() > value) {
                    value = songs.get(j).getYear();
                    index = j;
                }
            }
            if (index != -1) {
                top5.add(songs.remove(index).getTitle());
            }
        }

        return top5;
    }
}
