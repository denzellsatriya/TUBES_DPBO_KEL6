import java.util.ArrayList;
import java.util.List;

public class User extends Akun {
    private Setting setting;
    private List<Song> likedSongs = new ArrayList<>();
    private boolean isPremium;

    public User(String username, String email,String password, boolean isPremium) {
        super(username, email, password);
        this.isPremium = isPremium;
        this.setting = new Setting("Indonesia"); 
    }

    public void likeSong(Song song) {
        likedSongs.add(song);
        System.out.println(">>> Anda menyukai lagu: " + song.getJudul());
    }

    public Setting getSetting() {
        return setting;
    }
    
    public List<Song> getLikedSongs() {
        return likedSongs;
    }

    public boolean isPremium() {
        return isPremium;
    }

    @Override
    public String getRole() {
        return "User";
    }
}