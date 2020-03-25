package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;
import Data.Library;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LibraryPanel extends JPanel {

    public JTable table;
    JLabel fieldLabel;
    public JComboBox<Object> fieldsList;

    JButton add;
    JButton redact;
    JButton delete;

    public String[] columnsHeader = new String [] {"Идентификатор", "Библиотека", "Адрес", "Телефон"};
    public ArrayList<Library> libraryArrayList;

    public LibraryPanel() {

        this.setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        fieldLabel = new JLabel("Sort by");

        add(fieldLabel, gbc);

        AdminOpportunities opportunities = AdminOpportunities.getInstance();
        fieldsList = new JComboBox<Object>(columnsHeader);
        fieldsList.setMaximumRowCount(columnsHeader.length);
        fieldsList.addActionListener(e -> opportunities.updateLibraryTable());

        gbc.gridx = 1;

        add(fieldsList, gbc);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setVisible(true);

        add = Buildier.createIconButton(0, 0, new ImageIcon("add.png"));
        add.addActionListener(e -> onAddButtonClick());
        panel.add(add);

        redact = Buildier.createIconButton(40, 0, new ImageIcon("redact.png"));
        redact.addActionListener(e -> onRedactButtonClick());

        panel.add(redact);

        delete = Buildier.createIconButton(80, 0, new ImageIcon("delete.png"));
        delete.addActionListener(e -> onDeleteButtonClick());

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;

        panel.add(delete);

        add(panel, gbc);

        Box box = Box.createVerticalBox();
        table = new JTable();
        table.setBorder(BorderFactory.createLineBorder(Color.black));

        box.add(table);
        box.add(new JScrollPane(table));
        box.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 10, 10, 10);

        add(box, gbc);

    }

    public void onAddButtonClick() {
        new LibraryRecord();
    }

    public void onRedactButtonClick() {

        int index = table.getSelectedRow();
        long id_library = libraryArrayList.get(index).getId();
        new LibraryRecord(id_library);

    }

    public void onDeleteButtonClick() {

        int index = table.getSelectedRow();
        long id_library = libraryArrayList.get(index).getId();

        AdminOpportunities.getInstance().deleteLibrary(id_library);

    }

}