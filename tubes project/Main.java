import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {

	static List<Akun> allAccounts = new ArrayList<>();
	static List<Song> allSongs = new ArrayList<>();
	static List<Podcast> allPodcasts = new ArrayList<>();
	static List<Payment> allPayments = new ArrayList<>();
	static Akun loggedInAkun;
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		setupInitialData();
		System.out.println("Selamat Datang di Website / Aplikasi Streaming Audio");
		System.out.println("====================================================");

		LogReg();
		// loginProcess();

		if (loggedInAkun != null) {
			mainMenuLoop();
		}

		System.out.println("\nProgram Selesai.");
	}

	private static void mainMenuLoop() {
		boolean exit = false;
		while (!exit) {
			try {
				System.out.println("\n--- MENU UTAMA (" + loggedInAkun.getUsername() + ") ---");
				int choice = 0;
				if (loggedInAkun instanceof User && !(loggedInAkun instanceof Artist)) {
					choice = showUserMenu();
				} else if (loggedInAkun instanceof Artist) {
					choice = showArtistMenu();
				} else if (loggedInAkun instanceof Admin) {
					choice = showAdminMenu();
				}

				if (choice == 0)
					exit = true;

			} catch (InputMismatchException e) {
				System.out.println("(X) Input tidak valid! Harap masukkan angka.");
				scanner.nextLine();
			}
		}
	}

	private static int showUserMenu() {
		System.out.println("1. Lihat & Putar Semua Media");
		System.out.println("2. Sukai Lagu");
		System.out.println("3. Lihat Profil & Lagu Disukai");
		System.out.println("0. Logout");
		System.out.print("Pilihan: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		User user = (User) loggedInAkun;
		switch (choice) {
			case 1:
				displayAndPlayMedia();
				break;
			case 2:
				likeASong(user);
				break;
			case 3:
				viewUserProfile(user);
				break;
		}
		return choice;
	}

	private static int showArtistMenu() {
		System.out.println("1. Rilis Lagu Baru");
		System.out.println("2. Lihat Album Saya");
		System.out.println("0. Logout");
		System.out.print("Pilihan: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		Artist artist = (Artist) loggedInAkun;
		switch (choice) {
			case 1:
				System.out.print("Masukkan judul lagu baru: ");
				String judul = scanner.nextLine();
				Song newSong = artist.rilisLaguBaru("S" + (allSongs.size() + 1), judul);
				allSongs.add(newSong);
				break;
			case 2:
				System.out.println("\nAlbum: " + artist.getDefaultAlbum().getName());
				for (Song track : artist.getDefaultAlbum().getTracks()) {
					System.out.println("- " + track);
				}
				break;
		}
		return choice;
	}

	private static int showAdminMenu() {
		System.out.println("1. Lihat Laporan Sistem");
		System.out.println("0. Logout");
		System.out.print("Pilihan: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		if (choice == 1) {
			((Admin) loggedInAkun).viewSystemReport(allAccounts, allPayments);
		}
		return choice;
	}

	private static void displayAndPlayMedia() {
		System.out.println("\n--- Daftar Media Tersedia ---");
		List<Playable> allMedia = new ArrayList<>();
		allMedia.addAll(allSongs);
		allMedia.addAll(allPodcasts);

		for (int i = 0; i < allMedia.size(); i++) {
			System.out.println((i + 1) + ". " + allMedia.get(i));
		}
		System.out.print("Pilih media untuk diputar (0 untuk kembali): ");
		int choice = scanner.nextInt() - 1;
		scanner.nextLine();

		if (choice >= 0 && choice < allMedia.size()) {
			allMedia.get(choice).play();
		}
	}

	private static void likeASong(User user) {
		System.out.println("\n--- Pilih Lagu untuk Disukai ---");
		for (int i = 0; i < allSongs.size(); i++) {
			System.out.println((i + 1) + ". " + allSongs.get(i));
		}
		System.out.print("Pilihan Anda (0 untuk kembali): ");
		int choice = scanner.nextInt() - 1;
		scanner.nextLine();

		if (choice >= 0 && choice < allSongs.size()) {
			user.likeSong(allSongs.get(choice));
		}
	}

	private static void viewUserProfile(User user) {
		System.out.println("\n--- Profil " + user.getUsername() + " ---");
		System.out.println("Email: " + user.email);
		System.out.println("Status: " + (user.isPremium() ? "Premium" : "Reguler"));
		System.out.println("Info Setting: " + user.getSetting().getProfileInfo());
		System.out.println("\nLagu yang disukai:");
		if (user.getLikedSongs().isEmpty()) {
			System.out.println("(Belum ada lagu yang disukai)");
		} else {
			for (Song song : user.getLikedSongs()) {
				System.out.println("- " + song.getJudul());
			}
		}
	}

	private static void setupInitialData() {
		Genre pop = new Genre("Pop");
		Genre rock = new Genre("Rock");
		Premium premiumPlan = new Premium("Student", 27500);

		User user1 = new User("Denzell", "denzell@gmail.com", "123456", false);
		User user2 = new User("Nopal", "nopal@gmail.com", "123456", true);
		Artist artis1 = new Artist("Braser Roll", "broll@gmail.com", "123456", rock);
		Admin admin = new Admin("admin", "admin@gmail.com", "123456");

		allAccounts.add(user1);
		allAccounts.add(user2);
		allAccounts.add(artis1);
		allAccounts.add(admin);

		allSongs.add(artis1.rilisLaguBaru("L1", "Climbing the Stage"));
		allSongs.add(artis1.rilisLaguBaru("L2", "Bising"));

		allPayments.add(new Payment(user2, premiumPlan, "2025-06-11"));

		allPodcasts.add(new Podcast("P1", "Band apa ini?", "Raditya Dika"));
	}

	private static void LogReg() {
		int key = 0;
		String input = "";
		int f = 0;
		while (f == 0) {
			input = JOptionPane.showInputDialog("1. Register Akun\n2. Login Akun");

			if (input == null) {
				break;
			}

			try {
				key = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, e);
			}

			switch (key) {
				case 1:
					register();
					f = 1;
					break;
				case 2:
					login();
					f = 1;
					break;
				default:
					JOptionPane.showMessageDialog(null, "Pilih opsi 1 atau 2");
					break;
			}
		}

	}

	public static void register() {
		// ArrayList<Akun> akun = new ArrayList<Akun>();
		String name = "";
		String email = "";
		String pass = "";
		boolean create = false;

		int index = 0;
		int x = 0;
		int y = 0;
		int z = 0;

		// Register jika tidak Akun tidak ada isi
		if (allAccounts.size() <= 0) {
			// Nama
			while (x == 0) {
				name = JOptionPane.showInputDialog("Masukkan nama");
				if (name == null) {
					x += 1;
					y += 1;
					z += 1;
					break;
				}
				try {
					if (name.equals("")) {
						throw new Exception("Nama tidak boleh kosong");
					} else {
						x += 1;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			// Email
			while (y == 0) {
				email = JOptionPane.showInputDialog("Masukkan email");
				if (email == null) {
					y += 1;
					z += 1;
					break;
				}
				try {
					if (email.equals("")) {
						throw new Exception("Email tidak boleh kosong");
					}
					if (!email.contains("@")) {
						throw new Exception("Email tidak sesuai");
					} else {
						y += 1;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			// Password
			while (z == 0) {
				pass = JOptionPane.showInputDialog("Masukkan password");
				if (pass == null) {
					z += 1;
					break;
				}
				try {
					if (pass.equals("")) {
						throw new Exception("(!) Password tidak boleh kosong");
					}
					if (pass.length() <= 5) {
						throw new Exception("(!) Password harus lebih dari 5 karakter");
					} else {
						z += 1;
						create = true;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);

				}
			}
			// Kalo akun ada isi
		} else {
			// Nama
			while (x == 0) {
				name = JOptionPane.showInputDialog("Masukkan nama");
				if (name == null) {
					x += 1;
					y += 1;
					z += 1;
					break;
				}

				try {
					if (name.equals("")) {
						throw new Exception("(!) Nama tidak boleh kosong");
					} else {
						for (int i = 0; i < allAccounts.size(); i++) {
							if (name.equals(allAccounts.get(i).getUsername())) {
								throw new Exception("(!) Nama sudah terdaftar, coba lagi");
							}
							if ((i + 1 == allAccounts.size())) {
								index = i + 1;
								x += 1;
							}
						}

					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			// Email
			while (y == 0) {
				email = JOptionPane.showInputDialog("Masukkan email");
				if (email == null) {
					y += 1;
					z += 1;
					break;
				}

				try {
					if (email.equals("")) {
						throw new Exception("(!) Email tidak boleh kosong");
					}
					if (!email.contains("@")) {
						throw new Exception("(!) Email tidak sesuai");
					} else {
						for (int i = 0; i < allAccounts.size(); i++) {
							if (email.equals(allAccounts.get(i).getEmail())) {
								throw new Exception("(!) Email sudah terdaftar, coba lagi");
							}
							if ((i + 1 == allAccounts.size())) {
								y += 1;
							}
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			// Password
			while (z == 0) {
				pass = JOptionPane.showInputDialog("Masukkan password");
				if (pass == null) {
					z += 1;
					break;
				}
				try {
					if (pass.equals("")) {
						throw new Exception("(!) Password tidak boleh kosong");
					}
					if (pass.length() <= 5) {
						throw new Exception("(!) Password harus lebih dari 5 karakter");
					} else {
						z += 1;
						create = true;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);

				}
			}
		}
		// Bikin akun
		if (create == true) {
			User userRegis = new User(name, email, pass, false);
			allAccounts.add(userRegis);
			loggedInAkun = allAccounts.get(index);
			JOptionPane.showMessageDialog(null, "Username : " + name
					+ "\nEmail : " + email);
		}

	}

	public static void login() {
		String input = "";
		String pass = "";
		boolean check = false;

		int index = -1;
		int x = 0;
		int y = 0;

		while (x == 0) {
			input = JOptionPane.showInputDialog("Masukkan nama atau email");
			if (input == null) {
				x += 1;
				y += 1;
				break;
			}
			try {
				if (input.equals("")) {
					throw new Exception("(!) Input tidak boleh kosong");
				} else {
					for (int i = 0; i < allAccounts.size(); i++) {
						if (input.equals(allAccounts.get(i).getUsername())) {
							index = i;
							x += 1;
							break;
						} else if (input.equals(allAccounts.get(i).getEmail())) {
							index = i;
							x += 1;
							break;
						}
						if ((i + 1 == allAccounts.size())) {
							throw new Exception("(!) Nama atau Email tidak terdaftar, coba lagi");
						}
					}

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}

		while (y == 0) {

			pass = JOptionPane.showInputDialog("Masukkan password");
			if (pass == null) {
				y += 1;
				break;
			}
			try {
				if (pass.equals("")) {
					throw new Exception("(!) Password tidak boleh kosong");
				}
				if (pass.equals(allAccounts.get(index).getPassword())) {

					y += 1;
					check = true;
				} else {
					throw new Exception("(!) Password tidak sesuai, Coba lagi");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);

			}
		}

		if (check == true) {
			loggedInAkun = allAccounts.get(index);
		}
	}

}
