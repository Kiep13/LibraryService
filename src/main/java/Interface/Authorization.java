package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;
import BusinessLogic.DataBaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Authorization extends JDialog {


    public JLabel loginLabel;
    public JTextField loginTextField;
    public JLabel passwordLabel;
    public JPasswordField passwordTextField;
    public JButton logIn;

    public Authorization() {

        setTitle("Authorization");
        setSize(250, 220);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("bookIcon.png");
        setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setBackground(Color.WHITE);
        panel.setLocation(0, 0);
        panel.setSize(getWidth(), getHeight());
        panel.setLayout(null);

        loginLabel = new JLabel("Login");
        loginLabel.setSize(40, 20);
        loginLabel.setLocation(105,20);

        panel.add(loginLabel);

        loginTextField = new JTextField();
        loginTextField.setSize(200, 20);
        loginTextField.setLocation(20, 45);

        panel.add(loginTextField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setSize(70, 20);
        passwordLabel.setLocation(90, 75);

        getContentPane().add(passwordLabel);

        passwordTextField = new JPasswordField();
        passwordTextField.setSize(200, 20);
        passwordTextField.setLocation(20, 100);

        panel.add(passwordTextField);

        logIn = new JButton("Entry");
        logIn.setSize(80, 30);
        logIn.setLocation(85, 130);

        Action logInAction = new AbstractAction("Log in") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onButtonClick();
            }
        };
        logIn.setAction(logInAction);
        logIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "logIn");
        logIn.getActionMap().put("logIn", logInAction);

        panel.add(logIn);

        getContentPane().add(panel);

    }

    public void onButtonClick() {

        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        if(login.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Пустое поле логина");
            return;
        }

        if(password.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Пустое поле пароля");
            return;
        }

        AdminOpportunities adminOpp = AdminOpportunities.getInstance();
        if(adminOpp.login(login, password)) {
            dispose();
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Неправильно указан логин и/или пароль");
        }

    }
}
