package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.DataBaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LibraryService extends JFrame {

    public static void main(String []args) {
        new LibraryService();
    }

    public LibraryService() {

        setSize(775, 500);
        setVisible(true);
        setTitle("Library Service");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - 350, dimension.height/2 - 250);

        JPanel panel = new CatalogPanel(this.getWidth(), this.getHeight());

        AdminOpportunities.getInstance().setFrame(this);

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());


        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        getContentPane().add(panel, gbc);

        revalidate();
        repaint();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        class WindowListener extends WindowAdapter {
            public void windowClosing(WindowEvent e) {
                DataBaseHelper dbHelper = DataBaseHelper.getInstance();
                dbHelper.close();
            }
        }

        addWindowListener(new WindowListener());

    }

}
