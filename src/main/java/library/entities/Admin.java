package library.entities;

public class Admin {

    private String username;
    private String name;
    private String password;
    private boolean loggedIn;

    // constructor الأساسي
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = username;   // أو أي اسم، المهم ما يظل null
        this.loggedIn = false;
    }

    // constructor الكامل إذا احتجتيه
    public Admin(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
