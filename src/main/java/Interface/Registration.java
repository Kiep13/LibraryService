package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Registration extends JDialog {

    JLabel loginLabel;
    JTextField loginTextField;
    JLabel passwordLabel;
    JPasswordField passwordTextField;
    JLabel repeatPasswordLabel;
    JPasswordField repeatPasswordTextField;
    JButton registration;

    public Registration(int x, int y) {

        setTitle("Registration");
        setSize(250, 260);
        setVisible(true);
        setBackground(Color.WHITE);

        setLocation(x, y);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());

        JPanel panel = Buildier.createPanel(getWidth(), getHeight());

        loginLabel = Buildier.createLabel("Login", 40, 20, 105, 20);
        panel.add(loginLabel);

        loginTextField = Buildier.createTextField(200, 20, 20, 45);
        panel.add(loginTextField);

        passwordLabel = Buildier.createLabel("Password", 70, 20, 90, 75);
        panel.add(passwordLabel);

        passwordTextField = Buildier.createPasswordField(200, 20, 20, 100);
        panel.add(passwordTextField);

        repeatPasswordLabel = Buildier.createLabel("Repeat password", 110, 20, 70, 130);
        panel.add(repeatPasswordLabel);

        repeatPasswordTextField = Buildier.createPasswordField(200, 20, 20, 155);
        panel.add(repeatPasswordTextField);

        registration = Buildier.createButton("Registration", 120, 30, 65, 185);

        Action logInAction = new AbstractAction("Registration") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onRegistrationButtonClick();
            }
        };
        registration.setAction(logInAction);
        registration.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "registration");
        registration.getActionMap().put("registration", logInAction);

        panel.add(registration);

        getContentPane().add(panel);

    }

    public void onRegistrationButtonClick() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String repeatPassword = repeatPasswordTextField.getText();

        AdminOpportunities adminOpportunities = AdminOpportunities.getInstance();
        if (adminOpportunities.register(login, password, repeatPassword)) {
            dispose();
        }
    }

}
