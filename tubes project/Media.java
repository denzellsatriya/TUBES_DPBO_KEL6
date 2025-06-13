public abstract class Media {
    protected String id;
    protected String judul;

    public Media(String id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }
}