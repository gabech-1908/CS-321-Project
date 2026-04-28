import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
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
    private static JButton signUpButton;
    private static JPanel loginArea;
    private static JTextArea loginText;

    public static void initLoginPage() {
        loginArea = new JPanel();

        enterUsername = new JTextField("Username");
        enterPassword = new JPasswordField("Password");
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        loginText = new JTextArea("Enter username and password to login");

        // larger font for readability
        Font bigFont = new Font("SansSerif", Font.PLAIN, 18);
        enterUsername.setFont(bigFont);
        enterPassword.setFont(bigFont);
        loginButton.setFont(bigFont);
        signUpButton.setFont(bigFont);
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
                //TODO: see if username and password match anything from database
                //potentially need loop around if statement
                
                // if username and password are correct, switch to the homepage
                if (username.equals("admin") && isPasswordValid()) {
                    //reset text in textfields
                    enterUsername.setText("Username");
                    enterPassword.setText("Password");

                    //TODO: grab related data from database
                    LinkedList<SavingGoal> sg = new LinkedList<>();
                    LinkedList<Subscription> s = new LinkedList<>();
                    double b = 0;
                    // update current user
                    User.initUser(sg, b, s);

                    // switch to homepage
                    switchToHome();
                    HomePage.switchToHome();
                } else {
                    // otherwise, leth them try again
                    loginText.setText("Incorrect username and/or password");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchToSignUp();
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
        
        // Logo with fallback paths and visible fallback text
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

        loginArea.add(loginText);
        loginArea.add(Box.createVerticalStrut(40));
        loginArea.add(enterUsername);
        loginArea.add(Box.createVerticalStrut(10));
        loginArea.add(enterPassword);
        loginArea.add(Box.createVerticalStrut(20));
        loginArea.add(loginButton);
        loginArea.add(signUpButton);
    }

    public static JPanel getLoginArea() {
        return loginArea;
    }

    private static void setLogoIconIfFound(JLabel logoLabel, Dimension logoSize) {
    String[] candidatePaths = {
        "logo.png",
        "Financial App/logo.png"
    };

    for (String path : candidatePaths) {
        File logoFile = new File(path);
        if (logoFile.exists() && logoFile.isFile()) {
            ImageIcon icon = new ImageIcon(logoFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(
                logoSize.width,
                logoSize.height,
                Image.SCALE_SMOOTH
            );
            logoLabel.setText("");
            logoLabel.setIcon(new ImageIcon(scaled));
            return;
        }
    }

    logoLabel.setText("Logo not found");
}

    private static boolean isPasswordValid() {
        //TODO after getting username, check user's password
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
    
    public static void switchToSignUp(){
        loginArea.setVisible(false);
        SignUpPage.switchToSignUpPage();
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
