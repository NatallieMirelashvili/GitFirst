package logic;

public class PasswordAuthenticator {
    private String password;

    public PasswordAuthenticator() {
        this("test");
    }

    public PasswordAuthenticator(String password) {
        this.password = password;
    }

    public boolean authenticate(String password) {
        return password != null && password.equals(this.password);
    }
}
