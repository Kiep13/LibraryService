package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;
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
            Buildier.showErrorMessage("Error", "Errors when getting record data");
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

        JPanel panel = Buildier.createPanel(getWidth(), getHeight());

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());

        nameLabel = Buildier.createLabel("Name", 70, 20, 10, 30);
        panel.add(nameLabel);

        addressLabel = Buildier.createLabel("Address", 70, 20, 10, 60);
        panel.add(addressLabel);

        telephoneLabel = Buildier.createLabel("Phone", 70, 20,10, 90);
        panel.add(telephoneLabel);

        nameTextField = Buildier.createTextField(200, 20, 80, 30);
        panel.add(nameTextField);

        addressTextField = Buildier.createTextField(200, 20, 80, 60);
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

        save = Buildier.createButton("Save", 80, 30, 115,120);
        save.addActionListener(e -> onButtonClick());

        panel.add(save);

        add(panel);

    }

    public void onButtonClick() {

        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String phone = telephoneTextField.getText();

        if (name.length() == 0 || address.length() == 0 || phone.length() == 0) {
            Buildier.showErrorMessage("Error", "Empty fields");
            return;
        }


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
