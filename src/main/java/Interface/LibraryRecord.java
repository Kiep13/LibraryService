package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.DataBaseHelper;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class LibraryRecord extends JFrame {

    JLabel nameLabel;
    JTextField nameTextField;
    JLabel addressLabel;
    JTextField addressTextField;
    JLabel telephoneLabel;
    JFormattedTextField telephoneTextField;
    JButton save;

    long id_library;

    public LibraryRecord() {

        initialize();
        id_library = -1;

    }

    public LibraryRecord( long id_library) {

        initialize();
        this.id_library = id_library;

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        try {
            ResultSet library = dbHelper.findLibrary(id_library);
            library.next();
            nameTextField.setText(library.getString(2));
            addressTextField.setText(library.getString(3));
            telephoneTextField.setText(library.getString(4));
        } catch(SQLException e) {
             e.printStackTrace();
        }

    }

    public void initialize() {

        setTitle("Library record");
        setSize(310, 210);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setBackground(Color.WHITE);
        panel.setLocation(0, 0);
        panel.setSize(getWidth(), getHeight());
        panel.setLayout(null);

        ImageIcon icon = new ImageIcon("bookIcon.png");
        setIconImage(icon.getImage());

        nameLabel = new JLabel("Name");
        nameLabel.setSize(70, 20);
        nameLabel.setLocation(10, 30);
        panel.add(nameLabel);

        addressLabel = new JLabel("Address");
        addressLabel.setSize(70, 20);
        addressLabel.setLocation(10, 60);
        panel.add(addressLabel);

        telephoneLabel = new JLabel("Phone");
        telephoneLabel.setSize(70, 20);
        telephoneLabel.setLocation(10, 90);
        panel.add(telephoneLabel);

        nameTextField = new JTextField();
        nameTextField.setSize(200, 20);
        nameTextField.setLocation(80, 30);
        panel.add(nameTextField);

        addressTextField = new JTextField();
        addressTextField.setSize(200, 20);
        addressTextField.setLocation(80, 60);
        panel.add(addressTextField);

        MaskFormatter phoneFormatter;
        try {
            phoneFormatter = new MaskFormatter("##-##-##");
            telephoneTextField = new JFormattedTextField(phoneFormatter);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        telephoneTextField.setSize(200, 20);
        telephoneTextField.setLocation(80, 90);
        panel.add(telephoneTextField);

        save = new JButton("Save");
        save.addActionListener(e -> onButtonClick());
        save.setSize(80, 30);
        save.setLocation(115,120);

        panel.add(save);

        add(panel);

    }

    public void onButtonClick() {

        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String phone = telephoneTextField.getText();

        AdminOpportunities opportunities = AdminOpportunities.getInstance();
        if(id_library == -1) {
            if(opportunities.addLibrary(name, address, phone)) {
                dispose();
            }
        } else {
            if (opportunities.redactLibrary(id_library, name, address, phone)) {
                dispose();
            }
        }
    }

}
