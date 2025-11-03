package library.entities;

public class CD {
    private String id;
    private String title;
    private String artist;
    private boolean available;

    public CD(String id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.available = true;
    }

    // ✅ Getter للدالة المطلوبة
    public String getCdId() {
        return id;
    }

    // باقي الـ getters/setters
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return title + " by " + artist;
    }
    
}


