public class Setting {
    private String country;
    
    public Setting(String country) {
        this.country = country;
    }

    public String getProfileInfo() {
        return "Negara: " + country;
    }
}