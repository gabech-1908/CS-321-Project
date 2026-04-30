import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpPage {
    private static JPanel signUpArea;

    private static JLabel signUpLabel;
    private static JLabel usernameLabel;
    private static JLabel passwordLabel;
    private static JLabel confirmPasswordLabel;
    private static JLabel errorLabel;

    private static JTextField enterUsername;
    private static JPasswordField enterPassword;
    private static JPasswordField confirmPassword;

    private static JButton signUpButton;
    private static JButton backButton;

    public static void initSignUpPage() {
        signUpArea = new JPanel();
        signUpArea.setLayout(new BorderLayout());

        // Logo at top, separate from form so it does not push the form down
        JLabel logoLabel = new JLabel();
        Dimension logoSize = new Dimension(300, 120);
        LoginPage.setLogoIconIfFound(logoLabel, logoSize);

        logoLabel.setPreferredSize(logoSize);
        logoLabel.setMaximumSize(logoSize);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        signUpArea.add(logoLabel, BorderLayout.NORTH);

        // Main form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        Font font = new Font("SansSerif", Font.PLAIN, 18);
        Dimension fieldSize = new Dimension(250, 30);

        signUpLabel = new JLabel("Sign Up");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        confirmPasswordLabel = new JLabel("Confirm Password");
        errorLabel = new JLabel(" ");

        enterUsername = new JTextField();
        enterPassword = new JPasswordField();
        confirmPassword = new JPasswordField();

        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back to Login");

        signUpLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        usernameLabel.setFont(font);
        passwordLabel.setFont(font);
        confirmPasswordLabel.setFont(font);
        errorLabel.setFont(font);
        signUpButton.setFont(font);
        backButton.setFont(font);

        enterUsername.setMaximumSize(fieldSize);
        enterPassword.setMaximumSize(fieldSize);
        confirmPassword.setMaximumSize(fieldSize);

        alignCenter(
            signUpLabel,
            usernameLabel,
            passwordLabel,
            confirmPasswordLabel,
            errorLabel,
            enterUsername,
            enterPassword,
            confirmPassword,
            signUpButton,
            backButton
        );

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfSignUpPage();
                LoginPage.switchToLogin();
            }
        });

        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(signUpLabel);
        formPanel.add(Box.createVerticalStrut(30));

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(enterUsername);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(enterPassword);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(confirmPasswordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(confirmPassword);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(errorLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(signUpButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backButton);

        signUpArea.add(formPanel, BorderLayout.CENTER);

        signUpArea.setVisible(false);
    }

    private static void handleSignUp() {
        String username = enterUsername.getText().trim();
        char[] password = enterPassword.getPassword();
        char[] confirm = confirmPassword.getPassword();

        if (username.isEmpty()) {
            errorLabel.setText("Username cannot be empty");
        } else if (password.length == 0) {
            errorLabel.setText("Password cannot be empty");
        } else if (!Arrays.equals(password, confirm)) {
            errorLabel.setText("Passwords must match");
        } else if (usernameExists(username)) {
            errorLabel.setText("Username unavailable");
        } else {
            boolean created = Database.addUser(username, new String(password));
            if (created) {
                enterUsername.setText("");
                enterPassword.setText("");
                confirmPassword.setText("");
                switchOutOfSignUpPage();
                LoginPage.switchToLogin();
            } else {
                errorLabel.setText("Could not create account. Try again.");
            }
        }

        Arrays.fill(password, '\0');
        Arrays.fill(confirm, '\0');
    }

    private static void alignCenter(JComponent... components) {
        for (JComponent component : components) {
            component.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        }
    }

    private static boolean usernameExists(String username) {
        return Database.userExists(username);
    }

    public static void switchToSignUpPage() {
        signUpArea.setVisible(true);
    }

    public static void switchOutOfSignUpPage() {
        signUpArea.setVisible(false);
    }

    public static JPanel getSignUpArea() {
        return signUpArea;
    }
}