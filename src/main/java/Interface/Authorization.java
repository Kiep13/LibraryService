package Interface;

import BusinessLogic.Buildier;
import BusinessLogic.AdminOpportunities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Authorization extends JDialog {


    JLabel loginLabel;
    JTextField loginTextField;
    JLabel passwordLabel;
    JPasswordField passwordTextField;
    JButton logIn;
    JButton registration;

    public Authorization() {

        setTitle("Authorization");
        setSize(250, 260);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());

        JPanel panel = Buildier.createPanel(getWidth(), getHeight());

        loginLabel = Buildier.createLabel("Login", 40, 20, 105, 20);
        panel.add(loginLabel);

        loginTextField = Buildier.createTextField(200, 20, 20, 45);
        panel.add(loginTextField);

        passwordLabel = Buildier.createLabel("Password" ,70, 20, 90, 75);
        panel.add(passwordLabel);

        passwordTextField = Buildier.createPasswordField(200, 20, 20, 100);
        panel.add(passwordTextField);

        logIn = Buildier.createButton("Entry", 80, 30, 85, 130);

        Action logInAction = new AbstractAction("Log in") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onLogInButtonClick();
            }
        };
        logIn.setAction(logInAction);
        logIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "logIn");
        logIn.getActionMap().put("logIn", logInAction);

        panel.add(logIn);

        registration = Buildier.createBlueLinkButton("Registration");
        registration.setSize(120, 30);
        registration.setLocation(65, 170);
        registration.addActionListener(e -> onRegistrationButtonClick());

        panel.add(registration);

        getContentPane().add(panel);

    }

    public void onLogInButtonClick() {

        String login = loginTextField.getText();
        String password = passwordTextField.getText();


        AdminOpportunities adminOpp = AdminOpportunities.getInstance();
        if(adminOpp.authorize(login, password)) {
            dispose();
            int width = adminOpp.window.getWidth();
            int height = adminOpp.window.getHeight();

            adminOpp.window.getContentPane().removeAll();
            JPanel panel = new AdminPanel(width, height);

            GridBagConstraints gbc = Buildier.crateGbc();

            adminOpp.window.getContentPane().add(panel, gbc);
            adminOpp.window.revalidate();
            adminOpp.window.repaint();
            adminOpp.window.setTitle("Admin Service");
        } else {
            Buildier.showErrorMessage("Error", "Invalid username and / or password");
        }

    }

    public void onRegistrationButtonClick() {
        new Registration(getX(), getY());
        this.dispose();
    }
}
