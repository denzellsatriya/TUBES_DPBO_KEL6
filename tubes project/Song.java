public class Song extends Media implements Playable {
    private Artist artist;
    private Album album;

    public Song(String id, String judul, Artist artist, Album album) {
        super(id, judul);
        this.artist = artist;
        this.album = album;
    }

    @Override
    public void play() {
        System.out.println(">>>  Memutar lagu: '" + this.judul + "' oleh " + artist.getUsername());
    }

    @Override
    public String toString() {
        return "Lagu: " + this.judul + " | Artis: " + this.artist.getUsername() + " | Album: " + this.album.getName();
    }
}