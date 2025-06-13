public class Podcast extends Media implements Playable {
    private String host;

    public Podcast(String id, String judul, String host) {
        super(id, judul);
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    @Override
    public void play() {
        System.out.println(">>>  Memutar podcast: '" + this.judul + "' oleh " + this.host);
    }

    @Override
    public String toString() {
        return "Podcast: " + this.judul + " | Host: " + this.host;
    }
}