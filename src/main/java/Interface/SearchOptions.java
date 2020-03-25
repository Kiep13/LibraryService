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

        ImageIcon icon = new ImageIcon("bookIcon.png");
        setIconImage(icon.getImage());

        titleLabel = new JLabel("Title");
        titleLabel.setSize(50, 20);
        titleLabel.setLocation(10, 10);

        add(titleLabel);

        titleTextField = new JTextField();
        titleTextField.setSize(120, 20);
        titleTextField.setLocation(60, 10);

        add(titleTextField);

        genresLabel = new JLabel("Genre");
        genresLabel.setSize(50, 20);
        genresLabel.setLocation(10, 40);

        add(genresLabel);

        genresTextField = new JTextField();
        genresTextField.setSize(120, 20);
        genresTextField.setLocation(60, 40);

        add(genresTextField);

        authorLabel = new JLabel("Author");
        authorLabel.setSize(50, 20);
        authorLabel.setLocation(200, 10);

        add(authorLabel);

        publisherLabel = new JLabel("Publisher");
        publisherLabel.setSize(70, 20);
        publisherLabel.setLocation(200, 40);

        add(publisherLabel);

        authorTextField = new JTextField();
        authorTextField.setSize(120, 20);
        authorTextField.setLocation(270, 10);

        add(authorTextField);

        publisherTextField = new JTextField();
        publisherTextField.setSize(120, 20);
        publisherTextField.setLocation(270, 40);

        add(publisherTextField);

        isbnLabel = new JLabel("ISBN");
        isbnLabel.setSize(40, 20);
        isbnLabel.setLocation(155, 70);

        add(isbnLabel);

        isbnTextField = new JTextField();
        isbnTextField.setSize(120, 20);
        isbnTextField.setLocation(200, 70);

        add(isbnTextField);

        pagesFromLabel = new JLabel("Pages from");
        pagesFromLabel.setSize(70, 20);
        pagesFromLabel.setLocation(105, 100);

        add(pagesFromLabel);

        pagesFromTextField = new JTextField();
        pagesFromTextField.setSize(50, 20);
        pagesFromTextField.setLocation(180, 100);

        add(pagesFromTextField);

        pagesToLabel = new JLabel("to");
        pagesToLabel.setSize(20, 20);
        pagesToLabel.setLocation(245, 100);

        add(pagesToLabel);

        pagesToTextField = new JTextField();
        pagesToTextField.setSize(50,20);
        pagesToTextField.setLocation(270, 100);

        add(pagesToTextField);

        yearFromLabel = new JLabel("Year from");
        yearFromLabel.setSize(70 , 20);
        yearFromLabel.setLocation(105, 130);

        add(yearFromLabel);

        yearFromTextField = new JTextField();
        yearFromTextField.setSize(50, 20);
        yearFromTextField.setLocation(180, 130);

        add(yearFromTextField);

        yearToLabel = new JLabel("to");
        yearToLabel.setSize(20, 20);
        yearToLabel.setLocation(245, 130);

        add(yearToLabel);

        yearToTextField = new JTextField();
        yearToTextField.setSize(50,20);
        yearToTextField.setLocation(270, 130);

        add(yearToTextField);

        confirm = new JButton("Confirm");
        confirm.addActionListener(e -> onButtonClick());
        confirm.setSize(80, 30);
        confirm.setLocation(170, 160);

        add(confirm);

    }

    public void onButtonClick() {

        try {
            if(pagesFromTextField.getText().length() != 0 )Integer.parseInt(pagesFromTextField.getText());
            if(pagesToTextField.getText().length() != 0)Integer.parseInt(pagesToTextField.getText());
            if(yearFromTextField.getText().length() != 0)Integer.parseInt(yearFromTextField.getText());
            if(yearToTextField.getText().length() != 0 )Integer.parseInt(yearToTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.ShowErrorMessage("Ошибка", "Невозможно иденцифитровать числовые параметры");
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

        opportunities.updateCatalogTable();
        dispose();
    }

}
