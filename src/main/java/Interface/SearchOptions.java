package Interface;

import BusinessLogic.Buildier;
import BusinessLogic.UserOpportunities;

import javax.swing.*;
import java.awt.*;

public class SearchOptions extends JDialog {

    JLabel titleLabel;
    JTextField titleTextField;
    JLabel genresLabel;
    JTextField genresTextField;
    JLabel authorLabel;
    JTextField authorTextField;
    JLabel publisherLabel;
    JTextField publisherTextField;
    JLabel isbnLabel;
    JTextField isbnTextField;
    JLabel pagesFromLabel;
    JTextField pagesFromTextField;
    JLabel pagesToLabel;
    JTextField pagesToTextField;
    JLabel yearFromLabel;
    JTextField yearFromTextField;
    JLabel yearToLabel;
    JTextField yearToTextField;
    JButton confirm;

    public SearchOptions() {

        setTitle("Search Options");
        setSize(410, 230);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());

        JPanel panel = Buildier.createPanel(getWidth(), getHeight());

        titleLabel = Buildier.createLabel("Title", 50, 20, 10, 10);
        panel.add(titleLabel);

        titleTextField = Buildier.createTextField(120, 20, 60, 10);
        panel.add(titleTextField);

        genresLabel = Buildier.createLabel("Genre", 50, 20, 10, 40);
        panel.add(genresLabel);

        genresTextField = Buildier.createTextField(120, 20, 60, 40);
        panel.add(genresTextField);

        authorLabel = Buildier.createLabel("Author", 50, 20, 200, 10);
        panel.add(authorLabel);

        publisherLabel = Buildier.createLabel("Publisher", 70, 20, 200, 40);
        panel.add(publisherLabel);

        authorTextField = Buildier.createTextField(120, 20, 270, 10);
        panel.add(authorTextField);

        publisherTextField = Buildier.createTextField(120, 20, 270, 40);
        panel.add(publisherTextField);

        isbnLabel = Buildier.createLabel("ISBN", 40, 20, 155, 70);
        panel.add(isbnLabel);

        isbnTextField = Buildier.createTextField(120, 20, 200, 70);
        panel.add(isbnTextField);

        pagesFromLabel = Buildier.createLabel("Pages from", 70, 20, 105, 100);
        panel.add(pagesFromLabel);

        pagesFromTextField = Buildier.createTextField(50, 20, 180, 100);
        panel.add(pagesFromTextField);

        pagesToLabel = Buildier.createLabel("to", 20, 20, 245, 100);
        panel.add(pagesToLabel);

        pagesToTextField = Buildier.createTextField(50,20, 270, 100);
        panel.add(pagesToTextField);

        yearFromLabel = Buildier.createLabel("Year from", 70, 20,105, 130 );
        panel.add(yearFromLabel);

        yearFromTextField = Buildier.createTextField(50, 20,180, 130 );
        panel.add(yearFromTextField);

        yearToLabel = Buildier.createLabel("to",20, 20, 245, 130);
        panel.add(yearToLabel);

        yearToTextField = Buildier.createTextField(50,20, 270, 130);
        panel.add(yearToTextField);

        confirm = Buildier.createButton("Confirm",80, 30, 170, 160);
        confirm.addActionListener(e -> onButtonClick());
        panel.add(confirm);

        add(panel);

    }

    public void onButtonClick() {

        try {
            if(pagesFromTextField.getText().length() != 0 )Integer.parseInt(pagesFromTextField.getText());
            if(pagesToTextField.getText().length() != 0)Integer.parseInt(pagesToTextField.getText());
            if(yearFromTextField.getText().length() != 0)Integer.parseInt(yearFromTextField.getText());
            if(yearToTextField.getText().length() != 0 )Integer.parseInt(yearToTextField.getText());
        } catch (NumberFormatException e) {
            Buildier.showErrorMessage("Error", "Numeric parameters cannot be identified");
            return;
        }

        UserOpportunities opportunities = UserOpportunities.getInstance();

        if(titleTextField.getText().length() == 0) opportunities.titleMack = "%";
        else opportunities.titleMack = titleTextField.getText() + "%";

        if(genresTextField.getText().length() == 0) opportunities.genreMack = "%";
        else opportunities.genreMack = genresTextField.getText() + "%";

        if(authorTextField.getText().length() == 0) opportunities.authorMack = "%";
        else opportunities.authorMack = authorTextField.getText() + "%";

        if(publisherTextField.getText().length() == 0) opportunities.publisherMask = "%";
        else opportunities.publisherMask = publisherTextField.getText() + "%";

        if(isbnTextField.getText().length() == 0) opportunities.isbnMack = "%";
        else opportunities.isbnMack = isbnTextField.getText() + "%";

        if(pagesFromTextField.getText().length() == 0) opportunities.pageMin = 0;
        else opportunities.pageMin = Integer.parseInt(pagesFromTextField.getText());

        if(pagesToTextField.getText().length() == 0) opportunities.pageMax = opportunities.dbHelper.getMaxPage();
        else opportunities.pageMax = Integer.parseInt(pagesToTextField.getText());

        if(yearFromTextField.getText().length() == 0) opportunities.yearMin = 0;
        else opportunities.yearMin = Integer.parseInt(yearFromTextField.getText());

        if(yearToTextField.getText().length() == 0) opportunities.yearMax = opportunities.dbHelper.getMaxYear();
        else opportunities.yearMax = Integer.parseInt(yearToTextField.getText());

        opportunities.viewListOfBooks();
        dispose();
    }

}
