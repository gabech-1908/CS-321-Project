import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
    private static String username;
    private static char[] password;
    private static JTextField enterUsername;
    private static JPasswordField enterPassword;
    private static JButton loginButton;
    private static JButton signUpButton;
    private static JPanel loginArea;
    private static JLabel loginText;

    public static void initLoginPage() {
        loginArea = new JPanel();

        enterUsername = new JTextField("Username");
        enterPassword = new JPasswordField("Password");
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        loginText = new JLabel("Enter username and password to login");

        Font bigFont = new Font("SansSerif", Font.PLAIN, 18);
        enterUsername.setFont(bigFont);
        enterPassword.setFont(bigFont);
        loginButton.setFont(bigFont);
        signUpButton.setFont(bigFont);
        loginText.setFont(bigFont);

        Dimension inputSize = new Dimension(250, 32);
        enterUsername.setPreferredSize(inputSize);
        enterUsername.setMaximumSize(inputSize);
        enterPassword.setPreferredSize(inputSize);
        enterPassword.setMaximumSize(inputSize);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = enterUsername.getText();
                password = enterPassword.getPassword();

                if (Database.checkLogin(username.trim(), new String(password))) {
                    enterUsername.setText("Username");
                    enterPassword.setText("Password");

                    LinkedList<SavingGoal> sg = new LinkedList<>();
                    LinkedList<Subscription> s = new LinkedList<>();
                    LinkedList<SpendingEntry> se = new LinkedList<>();
                    double b = 0;

                    User.initUser(sg, b, s, se);
                    User.setUsername(username.trim());

                    switchToHome();
                    HomePage.switchToHome();
                } else {
                    loginText.setText("Incorrect username and/or password");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchToSignUp();
            }
        });

        loginArea.setLayout(new BoxLayout(loginArea, BoxLayout.Y_AXIS));

        loginArea.add(Box.createVerticalStrut(40));

        JLabel logoLabel = new JLabel();
        Dimension logoSize = new Dimension(300, 120);
        setLogoIconIfFound(logoLabel, logoSize);
        logoLabel.setPreferredSize(logoSize);
        logoLabel.setMaximumSize(logoSize);
        logoLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        loginArea.add(logoLabel);
        loginArea.add(Box.createVerticalStrut(20));

        loginText.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        enterUsername.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        enterPassword.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        loginArea.add(loginText);
        loginArea.add(Box.createVerticalStrut(40));
        loginArea.add(enterUsername);
        loginArea.add(Box.createVerticalStrut(10));
        loginArea.add(enterPassword);
        loginArea.add(Box.createVerticalStrut(20));
        loginArea.add(loginButton);
        loginArea.add(Box.createVerticalStrut(10));
        loginArea.add(signUpButton);
    }

    public static JPanel getLoginArea() {
        return loginArea;
    }

    public static void setLogoIconIfFound(JLabel logoLabel, Dimension logoSize) {
        ImageIcon icon = null;

        URL logoUrl = LoginPage.class.getResource("/logo.png");
        if (logoUrl != null) {
            icon = new ImageIcon(logoUrl);
        }

        if (icon == null || icon.getIconWidth() <= 0) {
            String[] candidatePaths = {
                "logo.png",
                "Financial App/logo.png",
                "CS-321-Project-main/Financial App/logo.png"
            };

            for (String path : candidatePaths) {
                File logoFile = new File(path);
                if (logoFile.exists() && logoFile.isFile()) {
                    icon = new ImageIcon(logoFile.getAbsolutePath());
                    break;
                }
            }
        }

        if (icon != null && icon.getIconWidth() > 0) {
            Image scaled = icon.getImage().getScaledInstance(
                    logoSize.width,
                    logoSize.height,
                    Image.SCALE_SMOOTH
            );
            logoLabel.setText("");
            logoLabel.setIcon(new ImageIcon(scaled));
        } else {
            logoLabel.setText("Logo not found");
        }
    }

    public static void switchToSignUp() {
        loginArea.setVisible(false);
        SignUpPage.switchToSignUpPage();
    }

    public static void switchToHome() {
        loginArea.setVisible(false);
    }

    public static void switchToLogin() {
        if (loginArea != null) {
            loginArea.setVisible(true);
        }

        if (loginText != null) {
            loginText.setVisible(true);
        }

        if (enterUsername != null) {
            enterUsername.setVisible(true);
        }

        if (enterPassword != null) {
            enterPassword.setVisible(true);
        }
    }
}