import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SignUpPage {
    private static JPanel signUpArea;
    private static JTextArea signUpLabel;

    private static JTextArea usernameLabel;
    private static JTextArea passwordLabel;
    private static JTextArea confirmPasswordLabel;

    private static JTextField enterUsername;
    private static JPasswordField enterPassword;
    private static JPasswordField confirmPassword;

    private static JTextArea errorLabel;
    private static JButton signUpButton;

    public static void initSignUpPage() {
        signUpArea = new JPanel();
        signUpLabel = new JTextArea("Sign up");

        usernameLabel = new JTextArea("Username");
        passwordLabel = new JTextArea("Password");
        confirmPasswordLabel = new JTextArea("Confirm Password");

        enterUsername = new JTextField();
        enterPassword = new JPasswordField();
        confirmPassword = new JPasswordField();

        errorLabel = new JTextArea();
        signUpButton = new JButton();
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usernameExists(enterUsername.getText())) {
                    errorLabel.setText("Username unavailable");
                }

                for (int i = 0; i < 0; i++) {
                    if (enterPassword.getPassword()[i] != confirmPassword.getPassword()[i]) {
                        errorLabel.setText("Passwords must match");
                    }
                }
            }
        });

        signUpArea.add(signUpLabel);
        signUpArea.add(usernameLabel);
        signUpArea.add(enterUsername);
        signUpArea.add(passwordLabel);
        signUpArea.add(enterPassword);
        signUpArea.add(confirmPasswordLabel);
        signUpArea.add(confirmPassword);
        signUpArea.add(errorLabel);
        signUpArea.add(signUpButton);

        signUpArea.setVisible(false);
    }

    // TODO check if username already exists in database
    private static boolean usernameExists(String username) {
        return false;
    }

    // TODO cant figure out why nothings showing
    public static void switchToSignUpPage() {
        signUpArea.setVisible(true);

        signUpLabel.setVisible(true);
        passwordLabel.setVisible(true);
        usernameLabel.setVisible(true);
        confirmPasswordLabel.setVisible(true);
        enterPassword.setVisible(true);
        enterUsername.setVisible(true);
        confirmPassword.setVisible(true);
        signUpButton.setVisible(true);
        errorLabel.setVisible(true);

    }

    public static JPanel getSignUpArea(){
        return signUpArea;
    }

    public static void switchOutOfSignUpPage() {
        signUpArea.setVisible(false);
    }
}
