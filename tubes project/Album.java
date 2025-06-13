import java.util.ArrayList;
import java.util.List;

public class Album {
    private String name;
    private List<Song> tracks = new ArrayList<>();

    public Album(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addSong(Song song) {
        tracks.add(song);
    }
    
    public List<Song> getTracks() {
        return tracks;
    }
}