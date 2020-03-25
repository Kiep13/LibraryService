package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;
import BusinessLogic.DataBaseHelper;
import BusinessLogic.DatabaseTableModel;
import Data.Book;
import Data.Library;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookPanel extends JPanel {

    public JTable table;
    JLabel fieldLabel;
    public JComboBox<Object> fieldsList;

    JButton add;
    JButton redact;
    JButton delete;

    public String[] columnsHeader = new String [] {"ISBN", "Название", "Автор", "Жанр", "Издатель", "Количество страниц", "Год"};
    public ArrayList<Book> bookArrayList;

    public BookPanel() {

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

        fieldLabel = new JLabel("Sort by");

        add(fieldLabel, gbc);

        fieldsList = new JComboBox<Object>(columnsHeader);
        fieldsList.setMaximumRowCount(columnsHeader.length);

        AdminOpportunities opportunities = AdminOpportunities.getInstance();
        fieldsList.setSize(150, 20);
        fieldsList.setLocation(70, 10);
        fieldsList.addActionListener(e -> opportunities.updateBookTable());

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
        new BookRecord();
    }

    public void onRedactButtonClick()  {
        int index = table.getSelectedRow();
        String isbn = bookArrayList.get(index).getISBN();
        new BookRecord(isbn);
    }

    public void onDeleteButtonClick() {

        int index = table.getSelectedRow();
        String id_book = bookArrayList.get(index).getISBN();

        AdminOpportunities opportunities = AdminOpportunities.getInstance();
        opportunities.deleteBook(id_book);

    }
}
