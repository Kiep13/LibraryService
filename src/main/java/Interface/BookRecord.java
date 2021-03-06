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
import java.util.ArrayList;

public class BookRecord extends JFrame {

    JLabel titleLabel;
    JTextField titleTextField;
    JLabel authorLabel;
    JComboBox<Object> authorList;
    JLabel publisherLabel;
    JComboBox<Object> publisherList;
    JLabel genresLabel;
    JComboBox<Object>  genresList;
    JLabel isbnLabel;
    JTextField isbnTextField;
    JLabel pagesLabel;
    JTextField pagesTextField;
    JLabel yearLabel;
    JTextField yearTextField;
    JButton save;

    String id_book;

    public BookRecord()  {

        initialize();
        id_book = "";

    }

    public BookRecord(String isbn)  {

        initialize();
        this.id_book = isbn;

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        try {

            ResultSet book = dbHelper.findBook(isbn);
            book.next();
            titleTextField.setText(book.getString(2));
            isbnTextField.setText(book.getString(1));
            pagesTextField.setText(book.getString(6));
            yearTextField.setText(book.getString(7));

            long id_author = book.getLong(3);
            long id_genre = book.getLong(4);
            long id_publisher  = book.getLong(5);

            ResultSet resultAuthor = dbHelper.findAuthor(id_author);
            resultAuthor.next();
            authorList.setSelectedItem(resultAuthor.getString(1));

            ResultSet resultGenre = dbHelper.findGenre(id_genre);
            resultGenre.next();
            genresList.setSelectedItem(resultGenre.getString(1));

            ResultSet resultPublisher = dbHelper.findPublisher(id_publisher);
            resultPublisher.next();
            publisherList.setSelectedItem(resultPublisher.getString(1));

            isbnTextField.setEditable(false);

        } catch(SQLException e) {
            Buildier.showErrorMessage("Error", "Errors when getting record data");
        }

    }

    public void initialize() {

        setTitle("Book record");
        setSize(310, 300);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());

        JPanel panel = Buildier.createPanel(getWidth(), getHeight());

        titleLabel = Buildier.createLabel("Title", 70, 20, 10, 10);
        panel.add(titleLabel);

        titleTextField = Buildier.createTextField(200, 20, 80, 10);
        panel.add(titleTextField);

        authorLabel = Buildier.createLabel("Author", 70, 20, 10 ,40);
        panel.add(authorLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        ArrayList<String> authors = dbHelper.getAuthors();
        Object [] arrayAuthors = authors.toArray();

        authorList = new JComboBox<>(arrayAuthors);
        authorList.setSize(200, 20);
        authorList.setLocation(80, 40);
        authorList.setEditable(true);

        panel.add(authorList);

        publisherLabel = Buildier.createLabel("Publisher", 70, 20, 10, 70);
        panel.add(publisherLabel);

        ArrayList<String> publishers = dbHelper.getPublishers();
        Object [] arrayPublishers = publishers.toArray();

        publisherList = new JComboBox<>(arrayPublishers);
        publisherList.setMaximumRowCount(arrayPublishers.length);
        publisherList.setSize(200, 20);
        publisherList.setLocation(80, 70);
        publisherList.setEditable(true);

        panel.add(publisherList);

        genresLabel = Buildier.createLabel("Genre", 70, 20, 10, 100);
        panel.add(genresLabel);

        ArrayList<String> genres = dbHelper.getGenres();
        Object [] arrayGenres = genres.toArray();

        genresList = new JComboBox<Object>(arrayGenres);
        genresList.setMaximumRowCount(arrayGenres.length);
        genresList.setSize(200, 20);
        genresList.setLocation(80, 100);
        genresList.setEditable(true);
        panel.add(genresList);


        isbnLabel = Buildier.createLabel("ISBN", 70, 20, 10, 130);
        panel.add(isbnLabel);

        MaskFormatter isbnFormatter;
        try {
            isbnFormatter = new MaskFormatter("UUU##UUU##");
            isbnTextField = new JFormattedTextField(isbnFormatter);
        } catch(ParseException e) {
            Buildier.showErrorMessage("Error", "Errors when creating a code mask");
        }
        isbnTextField.setToolTipText("(Три заглавных символа + две цифры) * 2");
        isbnTextField.setSize(200, 20);
        isbnTextField.setLocation(80, 130);

        panel.add(isbnTextField);

        pagesLabel = Buildier.createLabel("Pages", 70, 20, 10, 160);
        panel.add(pagesLabel);

        pagesTextField = Buildier.createTextField(200, 20, 80, 160);
        panel.add(pagesTextField);

        yearLabel = Buildier.createLabel("Year", 70, 20, 10, 190);
        panel.add(yearLabel);

        yearTextField = Buildier.createTextField(200, 20, 80, 190);
        panel.add(yearTextField);

        save = Buildier.createButton("Save", 80, 30, 115,220);
        save.addActionListener(e -> onButtonClick());
        panel.add(save);

        add(panel);

    }

    public void onButtonClick() {

        String author = String.valueOf(authorList.getSelectedItem());
        String publisher = String.valueOf(publisherList.getSelectedItem());
        String genre = String.valueOf(genresList.getSelectedItem());

        String isbn = isbnTextField.getText();
        String title = titleTextField.getText();
        String pages = pagesTextField.getText();
        String year = yearTextField.getText();

        if (isbn.length() != 10) {
            Buildier.showErrorMessage("Error", "Errors when entering the book code");
            return;
        }

        if (title.length() == 0) {
            Buildier.showErrorMessage("Error", "The book title is missing");
            return;
        } else {
            title = title.substring(0, 1).toUpperCase() + title.substring(1);
        }

        if (author.length() == 0) {
            Buildier.showErrorMessage("Error", "The author field is empty");
            return;
        }

        if (genre.length() == 0) {
            Buildier.showErrorMessage("Error", "The genre field is empty");
            return;
        } else {
            genre = genre.substring(0, 1).toUpperCase() + genre.substring(1).toLowerCase();
        }

        if (publisher.length() == 0) {
            Buildier.showErrorMessage("Error", "The publisher field is empty");
            return;
        } else {
            publisher = publisher.substring(0, 1).toUpperCase() + publisher.substring(1);
        }

        if (pages.length() == 0) {
            Buildier.showErrorMessage("Error", "The number of pages is not specified");
            return;
        }

        int amount_pages, year_value;

        try {
            amount_pages = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.showErrorMessage("Error", "It is not possible to identify the number of pages");
            return;
        }

        if (amount_pages <= 0) {
            Buildier.showErrorMessage("Error", "Invalid set number of pages");
            return;
        }

        try {
            year_value = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.showErrorMessage("Error", "It is not possible to identify the year");
            return;
        }

        if (year_value <= 1500 || year_value > getCurrentYear()) {
            Buildier.showErrorMessage("Error", "Invalid specified year");
            return;
        }

        AdminOpportunities opportunities = AdminOpportunities.getInstance();

        if(id_book.compareTo("") == 0) {
            if(opportunities.addBook(isbn, title, author, genre, publisher, amount_pages, year_value)) {
                dispose();
            }
        } else {
            if(opportunities.redactBook(isbn, title, author, genre, publisher, amount_pages, year_value)) {
                dispose();
            }
        }

    }

    public int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

}
