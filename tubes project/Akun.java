public abstract class Akun {
    protected String username;
    protected String email;
    protected String password;

    public Akun(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public abstract String getRole();
}