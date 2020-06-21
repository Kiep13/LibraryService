package Interface;
import BusinessLogic.UserOpportunities;

import javax.swing.*;
import java.awt.*;

public class CatalogPanel extends JPanel {

    JButton authorization;
    JLabel sortLabel;
    JLabel fieldLabel;
    JButton searchButton;

    JTable table;
    JComboBox<Object> fieldsList;
    JComboBox<Object> libraryList;

    public CatalogPanel(int width, int height) {

        UserOpportunities opportunities = UserOpportunities.getInstance();

        Insets zeroPaddings = new Insets(0,0,0,0);

        this.setSize(width, height);
        this.setLocation(0,0);
        this.setBackground(Color.white);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

        authorization = new JButton("Authorization");

        authorization.setMargin(zeroPaddings);
        authorization.setFocusPainted( false );
        authorization.setContentAreaFilled(false);
        authorization.setForeground(Color.BLUE);
        authorization.addActionListener(e -> onAuthorizationClick());
        authorization.setBorder(null);

        this.add(authorization, gbc);

        sortLabel = new JLabel("Library");

        gbc.gridy = 1;

        this.add(sortLabel, gbc);

        Object [] librariesArray = UserOpportunities.getInstance().getLibrariesList().toArray();

        libraryList = new JComboBox<>(librariesArray);
        libraryList.setMaximumRowCount(librariesArray.length);
        libraryList.addActionListener(e -> opportunities.viewListOfBooks());

        gbc.gridx = 1;

        this.add(libraryList, gbc);

        fieldLabel = new JLabel("Sort by");

        gbc.gridx = 2;

        this.add(fieldLabel,gbc);

        fieldsList = new JComboBox<Object>(UserOpportunities.headers);
        fieldsList.setMaximumRowCount(UserOpportunities.headers.length);
        fieldsList.addActionListener(e -> opportunities.viewListOfBooks());
        gbc.gridx = 3;

        this.add(fieldsList, gbc);

        searchButton = new JButton("Search options");
        searchButton.addActionListener(e -> onSearchButtonClick());

        gbc.gridx = 4;

        this.add(searchButton, gbc);

        Box box = Box.createVerticalBox();

        table = new JTable();
        table.setBorder(BorderFactory.createLineBorder(Color.black));

        opportunities.table = table;
        opportunities.libraryList = libraryList;
        opportunities.fieldsList = fieldsList;

        box.add(table);
        box.add(new JScrollPane(table));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 10, 10, 10);

        this.add(box, gbc);

        UserOpportunities userOpportunities = UserOpportunities.getInstance();
        userOpportunities.viewListOfBooks();

    }

    public void onAuthorizationClick() {
        new Authorization();
    }

    public void onSearchButtonClick() {
        new SearchOptions();
    }
}
