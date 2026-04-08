import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;


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

        // larger font for readability
        Font bigFont = new Font("SansSerif", Font.PLAIN, 18);
        enterUsername.setFont(bigFont);
        enterPassword.setFont(bigFont);
        loginButton.setFont(bigFont);
        loginText.setFont(bigFont);

        // limit width of username/password fields
        Dimension inputSize = new Dimension(250, 32);
        enterUsername.setPreferredSize(inputSize);
        enterUsername.setMaximumSize(inputSize);
        enterPassword.setPreferredSize(inputSize);
        enterPassword.setMaximumSize(inputSize);

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

        // layout: stack a top spacer, optional logo, then login text and inputs vertically
        loginArea.setLayout(new BoxLayout(loginArea, BoxLayout.Y_AXIS));

        // top spacer to shift everything down
        loginArea.add(Box.createVerticalStrut(40));

        // logo image 
        JLabel logoLabel = new JLabel(); // set an ImageIcon here
        Dimension logoSize = new Dimension(300, 120);
        ImageIcon icon = new ImageIcon("logo.png");
        Image scaled = icon.getImage().getScaledInstance(logoSize.width, logoSize.height,Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));
        logoLabel.setPreferredSize(logoSize);
        logoLabel.setMaximumSize(logoSize);
        logoLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        loginArea.add(logoLabel);
        loginArea.add(Box.createVerticalStrut(20));

        loginText.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        enterUsername.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        enterPassword.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        loginArea.add(loginText);
        // push username/password further down
        loginArea.add(Box.createVerticalStrut(40));
        loginArea.add(enterUsername);
        loginArea.add(Box.createVerticalStrut(10));
        loginArea.add(enterPassword);
        loginArea.add(Box.createVerticalStrut(20));
        loginArea.add(loginButton);
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
        for (int i = 0; i < pass.length; i++) {
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

    public static void switchToLogin() {
        if (loginArea != null) {
            loginArea.setVisible(true);
        }
        if (enterUsername != null) {
            enterUsername.setVisible(true);
        }
        if (enterPassword != null) {
            enterPassword.setVisible(true);
        }
        if (loginText != null) {
            loginText.setVisible(true);
        }
    }
}
