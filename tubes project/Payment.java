public class Payment {
    private User user;
    private Premium premiumPackage;
    private String paymentDate;

    public Payment(User user, Premium premiumPackage, String paymentDate) {
        this.user = user;
        this.premiumPackage = premiumPackage;
        this.paymentDate = paymentDate;
    }

    public String getInfo() {
        return "User: " + user.getUsername() + " | Paket: " + premiumPackage.getPackageName() + " | Tanggal: " + paymentDate;
    }
}