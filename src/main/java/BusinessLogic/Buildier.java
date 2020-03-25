package BusinessLogic;

import javax.swing.*;
import java.awt.*;

public class Buildier {

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

    public JTextField createTextField() {
        return new JTextField();
    }

    public JLabel createLabel() {
        return new JLabel();
    }

    public JTable createTabel() {
        return new JTable();
    }

    public JComboBox createCombobox() {
        return new JComboBox();
    }


    public static void ShowErrorMessage(String title, String message) {

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void ShowInformationMessage(String title, String message) {

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

}
