package library.entities;

public class CD {
    private String title;
    private String artist;
    private String cdId;
    private boolean available;

    public CD(String title, String artist, String cdId) {
        this.title = title;
        this.artist = artist;
        this.cdId = cdId;
        this.available = true;
    }

    public String getTitle() { return title; }
    public String getArtist() { return cdId; }
    public String getCdId() { return cdId; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return title + " — " + cdId + " (ID: " + cdId + ")";
    }
}

