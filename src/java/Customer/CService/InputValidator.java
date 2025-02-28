package Customer.CService;

import java.util.regex.Pattern;

public class InputValidator {

    // Regex pattern for email validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    
    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6; // Minimum 6 characters
    }
}
