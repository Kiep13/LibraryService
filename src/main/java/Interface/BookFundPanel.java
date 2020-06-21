package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;

import javax.swing.*;
import java.awt.*;

public class BookFundPanel extends JPanel {

    JTable table;
    JLabel fieldLabel;
    JComboBox<Object> fieldsList;

    JButton add;
    JButton redact;
    JButton delete;

    public BookFundPanel() {

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        fieldLabel = Buildier.createLabel("Sort by", 50, 20, 10, 10);
        add(fieldLabel, gbc);

        AdminOpportunities opportunities = AdminOpportunities.getInstance();
        fieldsList = new JComboBox(opportunities.bookFundHeaders);
        fieldsList.setMaximumRowCount(opportunities.bookFundHeaders.length);
        fieldsList.removeItemAt(0);
        fieldsList.setSelectedItem(fieldsList.getItemAt(0));
        fieldsList.addActionListener(e -> opportunities.updateBookFund());

        fieldsList.setSize(150, 20);
        fieldsList.setLocation(70, 10);

        gbc.gridx = 1;

        add(fieldsList, gbc);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setVisible(true);

        add = Buildier.createIconButton(0, 0, new ImageIcon(BusinessLogic.Buildier.getImage("Images/add.png")));
        add.addActionListener(e -> onAddButtonClick());
        panel.add(add);

        redact = Buildier.createIconButton(40, 0, new ImageIcon(BusinessLogic.Buildier.getImage("Images/redact.png")));
        redact.addActionListener(e -> onRedactButtonClick());

        panel.add(redact);

        delete = Buildier.createIconButton(80, 0, new ImageIcon(BusinessLogic.Buildier.getImage("Images/delete.png")));
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

        opportunities.bfTable = table;
        opportunities.bfFieldList = fieldsList;

    }

    public void onAddButtonClick() {
        new BookFundRecord();
    }

    public void onRedactButtonClick() {

        int index = table.getSelectedRow();
        if(index == -1) {
            Buildier.showErrorMessage("Error", "The entry is not selected for editing!");
            return;
        }
        long id_bookfund = AdminOpportunities.getInstance().bfArrayList.get(index).getId();
        new BookFundRecord(id_bookfund);

    }

    public void onDeleteButtonClick() {

        int index = table.getSelectedRow();
        if(index == -1) {
            Buildier.showErrorMessage("Error", "The entry is not selected for deleting!");
            return;
        }
        long id_bookfund = AdminOpportunities.getInstance().bfArrayList.get(index).getId();

        AdminOpportunities.getInstance().deleteBookFund(id_bookfund);

    }

}
