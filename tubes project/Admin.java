public class Admin extends Akun {
    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    public void viewSystemReport(java.util.List<Akun> allUsers, java.util.List<Payment> allPayments) {
        System.out.println("\n--- LAPORAN SISTEM ---");
        System.out.println("\n[Daftar Pengguna]");
        for (Akun user : allUsers) {
            String premiumStatus = "";
            if (user instanceof User) {
                premiumStatus = ((User) user).isPremium() ? "[PREMIUM]" : "[REGULER]";
            }
            System.out.println("- " + user.getUsername() + " (" + user.getRole() + ") " + premiumStatus);
        }

        System.out.println("\n[Daftar Transaksi Pembayaran]");
        if (allPayments.isEmpty()) {
            System.out.println("(Tidak ada transaksi)");
        } else {
            for (Payment payment : allPayments) {
                System.out.println("- " + payment.getInfo());
            }
        }
        System.out.println("\n--- AKHIR LAPORAN ---");
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}