public class Premium {
    private String packageName;
    private int price;

    public Premium(String packageName, int price) {
        this.packageName = packageName;
        this.price = price;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getPrice() {
        return price;
    }
}