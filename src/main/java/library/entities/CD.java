package library.entities;

/**
 * Represents a CD in the library system.
 * <p>
 * This class stores the CD's ID, title, artist, and availability status.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class CD {

    /**
     * The unique ID of the CD.
     */
    private String id;

    /**
     * The title of the CD.
     */
    private String title;

    /**
     * The artist of the CD.
     */
    private String artist;

    /**
     * Indicates whether the CD is currently available.
     */
    private boolean available;

    /**
     * Constructs a new CD with the specified ID, title, and artist.
     * The CD is initially available.
     *
     * @param id     the unique ID of the CD
     * @param title  the title of the CD
     * @param artist the artist of the CD
     */
    public CD(String id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.available = true;
    }

    /**
     * Returns the ID of the CD.
     *
     * @return the CD ID
     */
    public String getCdId() {
        return id;
    }

    /**
     * Returns the title of the CD.
     *
     * @return the CD title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the artist of the CD.
     *
     * @return the CD artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Checks whether the CD is available.
     *
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the CD.
     *
     * @param available the availability status to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Returns a string representation of the CD.
     *
     * @return a string in the format "title by artist"
     */
    @Override
    public String toString() {
        return title + " by " + artist;
    }
}
