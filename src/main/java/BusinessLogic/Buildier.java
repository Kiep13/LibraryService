package BusinessLogic;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Buildier {

    public static JPanel createPanel(int width, int height) {
        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setBackground(Color.WHITE);
        panel.setLocation(0, 0);
        panel.setSize(width, height);
        panel.setLayout(null);
        return panel;
    }

    public static JButton createButton(String title, int width, int height, int x, int y) {
        JButton button = new JButton(title);
        button.setSize(width, height);
        button.setLocation(x, y);
        return button;
    }

    public static JButton createIconButton(int x, int y, ImageIcon icon) {
        JButton button = new JButton();
        button.setContentAreaFilled(false);
        button.setIcon(icon);
        button.setMargin(new Insets(10,10,10,10));
        button.setBorder(null);
        button.setSize(30 , 30);
        button.setLocation(x, y);
        return button;
    }

    public static JButton createBlueLinkButton(String title) {
        JButton linkButton = new JButton(title);
        linkButton.setMargin(new Insets(10,0,0,0));
        linkButton.setFocusPainted( false );
        linkButton.setContentAreaFilled(false);
        linkButton.setForeground(Color.BLUE);
        linkButton.setBorder(null);
        return linkButton;
    }

    public static JLabel createLabel(String title, int width, int height, int x, int y) {
        JLabel label = new JLabel(title);
        label.setSize(width, height);
        label.setLocation(x,y);
        return label;
    }

    public static JTextField createTextField(int width, int height, int x , int y) {
        JTextField textField = new JTextField();
        textField.setSize(width, height);
        textField.setLocation(x, y);
        return textField;
    }

    public static JPasswordField createPasswordField(int width, int height, int x , int y) {
        JPasswordField textField = new JPasswordField();
        textField.setSize(width, height);
        textField.setLocation(x, y);
        return textField;
    }

    public static Image getImage(final String path) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static GridBagConstraints crateGbc() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        return gbc;
    }

    public static void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
