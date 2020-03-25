package Interface;

import BusinessLogic.AdminOpportunities;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    JTabbedPane tabbedPane;
    BookPanel booksPanel;
    LibraryPanel libraryPanel;
    BookFundPanel bookFundPanel;

    public AdminPanel(int width, int height) {

        setSize(width, height);
        setLocation(0,0);
        setLayout(new GridBagLayout());
        setVisible(true);
        setBackground(Color.WHITE);

        AdminOpportunities opportunities = AdminOpportunities.getInstance();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setSize(getWidth(),getHeight());
        tabbedPane.setBackground(Color.WHITE);

        booksPanel = new BookPanel();
        opportunities.setBookPanel(booksPanel);
        opportunities.updateBookTable();

        libraryPanel = new LibraryPanel();
        opportunities.setLibraryPanel(libraryPanel);
        opportunities.updateLibraryTable();

        bookFundPanel = new BookFundPanel();
        opportunities.setBfPanel(bookFundPanel);
        opportunities.updateBookFundTable();

        tabbedPane.add("Book fund", bookFundPanel);
        tabbedPane.add("Books", booksPanel);
        tabbedPane.add("Libraries", libraryPanel);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        add(tabbedPane, gbc);

        revalidate();
        repaint();

    }

}
