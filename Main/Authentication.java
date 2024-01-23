import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Authenticator {

    private static Map<String, String> users = new HashMap<>();

    public static void main(String[] args) {
    
        // Prompt for username and password
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        scanner.close();

        // Authenticate user
        if (authenticateUser(username, password)) {
            System.out.println("Authentication successful!");
        } else {
            System.out.println("Authentication failed!");
        }
    }

    private static void registerUser(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        users.put(username, hashedPassword);
    }

    private static boolean authenticateUser(String username, String password) {
        if (users.containsKey(username)) {
            String storedHashedPassword = users.get(username);
            return BCrypt.checkpw(password, storedHashedPassword);
        }
        return false;
    }
}
