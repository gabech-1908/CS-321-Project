import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginPage {
    private static String username;
    private static char[] password;
    private static JTextField enterUsername;
    private static JPasswordField enterPassword;
    private static JButton loginButton;
    private static JPanel loginArea;
    private static JTextArea loginText;

    public static void initLoginPage() {
        loginArea = new JPanel();

        enterUsername = new JTextField("Username");
        enterPassword = new JPasswordField("Password");
        loginButton = new JButton("Login");
        loginText = new JTextArea("Enter username and password to login");

        // add necessary action to login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = enterUsername.getText();
                password = enterPassword.getPassword();
                // if username and password are correct, switch to the homepage
                if (username.equals("admin") && isPasswordValid()) {
                    // switch to homepage
                    switchToHome();
                    HomePage.switchToHome();
                } else {
                    // otherwise, leth them try again
                    loginText.setText("Incorrect username and/or password");
                }
            }
        });

        enterUsername.setVisible(true);
        enterPassword.setVisible(true);
        loginText.setVisible(true);
        loginText.setEditable(false);
        // layout for loginArea
        // TODO
        // need to figure out how Layouts work
        // and lay out everything right but this sort of works for now
        GroupLayout layout = new GroupLayout(loginArea);
        loginArea.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(loginText)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(enterUsername)
                                .addComponent(enterPassword))
                        .addComponent(loginButton));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(loginText)
                                .addComponent(loginButton)
                                .addComponent(enterUsername))
                        .addComponent(enterPassword));
    }

    public static JPanel getLoginArea() {
        return loginArea;
    }

    private static boolean isPasswordValid() {
        if (password.length != 5) {
            System.out.println(password.length);
            return false;
        }
        char[] pass = { 'a', 'd', 'm', 'i', 'n' };
        for (int i = 0; i > pass.length; i++) {
            if (pass[i] != password[i]) {
                return false;
            }
            System.out.println(password[i]);
        }
        return true;
    }

    public static void switchToHome() {
        loginArea.setVisible(false);
        enterUsername.setVisible(false);
        enterPassword.setVisible(false);
        loginText.setVisible(false);

    }
}
