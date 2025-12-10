package library.services;

public class EmailConfig {

    // Switch for GUI real email sending
    public static final boolean ENABLE_REAL_EMAIL = false;  
    // true = GUI sends REAL emails (ONLY after you set username + app password)
    // false = tests run in MOCK mode

    // Your Gmail address
    public static final String USERNAME = "your_email@gmail.com";

    // Your 16-character App Password (NOT your Gmail password!)
    public static final String APP_PASSWORD = "xxxxxxxxxxxxxxxx";
}
