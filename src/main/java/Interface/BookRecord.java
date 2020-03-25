package Interface;

import BusinessLogic.AdminOpportunities;
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

        execute();
        id_book = "";

    }

    public BookRecord(String isbn)  {

        execute();
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
            e.printStackTrace();
        }

    }

    public void execute() {

        setTitle("Book record");
        setSize(310, 300);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("bookIcon.png");
        setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setBackground(Color.WHITE);
        panel.setLocation(0, 0);
        panel.setSize(getWidth(), getHeight());
        panel.setLayout(null);

        titleLabel = new JLabel("Title");
        titleLabel.setSize(70, 20);
        titleLabel.setLocation(10, 10);

        panel.add(titleLabel);

        titleTextField = new JTextField();
        titleTextField.setSize(200, 20);
        titleTextField.setLocation(80, 10);

        panel.add(titleTextField);

        authorLabel = new JLabel("Author");
        authorLabel.setSize(70, 20);
        authorLabel.setLocation(10, 40);

        panel.add(authorLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        ArrayList<String> authors = dbHelper.getAuthors();
        Object [] arrayAuthors = authors.toArray();

        authorList = new JComboBox<>(arrayAuthors);
        authorList.setSize(200, 20);
        authorList.setLocation(80, 40);
        authorList.setEditable(true);

        panel.add(authorList);

        publisherLabel = new JLabel("Publisher");
        publisherLabel.setSize(70, 20);
        publisherLabel.setLocation(10, 70);

        panel.add(publisherLabel);

        ArrayList<String> publishers = dbHelper.getPublishers();
        Object [] arrayPublishers = publishers.toArray();

        publisherList = new JComboBox<>(arrayPublishers);
        publisherList.setMaximumRowCount(arrayPublishers.length);
        publisherList.setSize(200, 20);
        publisherList.setLocation(80, 70);
        publisherList.setEditable(true);

        panel.add(publisherList);

        genresLabel = new JLabel("Genre");
        genresLabel.setSize(70, 20);
        genresLabel.setLocation(10, 100);

        panel.add(genresLabel);

        ArrayList<String> genres = dbHelper.getGenres();
        Object [] arrayGenres = genres.toArray();

        genresList = new JComboBox<Object>(arrayGenres);
        genresList.setMaximumRowCount(arrayGenres.length);

        genresList.setSize(200, 20);
        genresList.setLocation(80, 100);

        genresList.setEditable(true);

        panel.add(genresList);


        isbnLabel = new JLabel("ISBN");
        isbnLabel.setSize(70, 20);
        isbnLabel.setLocation(10, 130);

        panel.add(isbnLabel);

        MaskFormatter isbnFormatter;
        try {
            isbnFormatter = new MaskFormatter("UUU##UUU##");
            isbnTextField = new JFormattedTextField(isbnFormatter);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        isbnTextField.setToolTipText("(Три заглавных символа + две цифры) * 2");
        isbnTextField.setSize(200, 20);
        isbnTextField.setLocation(80, 130);

        panel.add(isbnTextField);

        pagesLabel = new JLabel("Pages");
        pagesLabel.setSize(70, 20);
        pagesLabel.setLocation(10, 160);

        panel.add(pagesLabel);

        pagesTextField = new JTextField();
        pagesTextField.setSize(200, 20);
        pagesTextField.setLocation(80, 160);

        panel.add(pagesTextField);

        yearLabel = new JLabel("Year");
        yearLabel.setSize(70, 20);
        yearLabel.setLocation(10, 190);

        panel.add(yearLabel);

        yearTextField = new JTextField();
        yearTextField.setSize(200, 20);
        yearTextField.setLocation(80, 190);

        panel.add(yearTextField);

        save = new JButton("Save");
        save.addActionListener(e -> onButtonClick());
        save.setSize(80, 30);
        save.setLocation(115,220);

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

        AdminOpportunities opportunities = AdminOpportunities.getInstance();

        if(id_book.compareTo("") == 0) {
            if(opportunities.addBook(isbn, title, author, genre, publisher, pages, year)) {
                dispose();
            }
        } else {
            if(opportunities.redactBook(isbn, title, author, genre, publisher, pages, year)) {
                dispose();
            }
        }

    }
}
