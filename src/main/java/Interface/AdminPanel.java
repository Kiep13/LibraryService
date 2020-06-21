package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    JButton logOut;
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

        logOut = Buildier.createBlueLinkButton("Log out");
        logOut.addActionListener(e -> onLogOutClick());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0 ,0 ,0, 15);

        add(logOut, gbc);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setSize(getWidth(),getHeight());
        tabbedPane.setBackground(Color.WHITE);

        libraryPanel = new LibraryPanel();
        opportunities.updateLibraries();

        booksPanel = new BookPanel();
        opportunities.updateBooks();

        bookFundPanel = new BookFundPanel();
        opportunities.updateBookFund();

        tabbedPane.add("Book fund", bookFundPanel);
        tabbedPane.add("Books", booksPanel);
        tabbedPane.add("Libraries", libraryPanel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0 ,0 ,0, 0);

        add(tabbedPane, gbc);

        revalidate();
        repaint();

    }

    public void onLogOutClick() {
        AdminOpportunities adminOpp = AdminOpportunities.getInstance();
        int width = adminOpp.window.getWidth();
        int height = adminOpp.window.getHeight();

        adminOpp.window.getContentPane().removeAll();
        JPanel panel = new CatalogPanel(width, height);

        GridBagConstraints gbc = Buildier.crateGbc();

        adminOpp.window.getContentPane().add(panel, gbc);
        adminOpp.window.revalidate();
        adminOpp.window.repaint();
        adminOpp.window.setTitle("Library Service");
    }

}
