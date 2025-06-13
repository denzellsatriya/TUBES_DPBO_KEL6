public class Artist extends Akun {
    private Genre genre;
    private Album defaultAlbum;

    public Artist(String username, String email,String password, Genre genre) {
        super(username, email, password);
        this.genre = genre;
        this.defaultAlbum = new Album("Karya Terbaik " + username); 
    }

    public Song rilisLaguBaru(String id, String judul) {
        Song newSong = new Song(id, judul, this, this.defaultAlbum);
        this.defaultAlbum.addSong(newSong);
        System.out.println(">>> Artis " + this.username + " merilis lagu baru '" + judul + "'!");
        return newSong;
    }

    public Album getDefaultAlbum() {
        return defaultAlbum;
    }
    
    public Genre getGenre() {
        return genre;
    }

    @Override
    public String getRole() {
        return "Artist";
    }
}