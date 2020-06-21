package Interface;

import BusinessLogic.AdminOpportunities;
import BusinessLogic.Buildier;
import BusinessLogic.DataBaseHelper;
import Data.Book;
import Data.Library;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookFundRecord extends JFrame {

    JLabel libraryLabel;
    JComboBox<Object> libraryList;
    JLabel bookLabel;
    JComboBox<Object> bookList;
    JLabel amountLabel;
    JTextField amountTextField;
    JButton save;

    ArrayList<Book> booksArrayList;
    ArrayList<Library> librariesArrayList;
    long id_bookfund;

    public BookFundRecord() {
        initialize();
        id_bookfund = -1;

    }

    public BookFundRecord(long id_bookfund) {

        initialize();
        this.id_bookfund = id_bookfund;

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        try {
            ResultSet bookfund = dbHelper.findBookFund(id_bookfund);
            bookfund.next();

            String isbn = bookfund.getString(1);
            long id_library = bookfund.getLong(2);

            ResultSet library = dbHelper.findLibrary(id_library);
            library.next();
            libraryList.setEditable(true);
            libraryList.setSelectedItem(library.getString(2));
            libraryList.setEditable(false);

            ResultSet book = dbHelper.findBook(isbn);
            book.next();
            bookList.setEditable(true);
            bookList.setSelectedItem(book.getString(2) + " : " + book.getInt(7) + "г.");
            bookList.setEditable(false);

            amountTextField.setText(bookfund.getString(3));

        } catch (SQLException e) {
            Buildier.showErrorMessage("Error", "Errors when getting record data");
        }


    }

    public void initialize() {

        setTitle("BookFund record");
        setSize(310, 210);
        setVisible(true);
        setBackground(Color.WHITE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2 - getWidth()/2, dimension.height/2 - getHeight()/2);

        setLayout(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(BusinessLogic.Buildier.getImage("Images/bookIcon.png"));
        setIconImage(icon.getImage());

        JPanel panel = Buildier.createPanel(getWidth(), getHeight());

        libraryLabel = Buildier.createLabel("Library",70, 20, 10, 30);
        panel.add(libraryLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        ResultSet libraries = dbHelper.getLibraries("Library");
        librariesArrayList = new ArrayList<>();
        try {
            while(libraries.next()) {
                librariesArrayList.add(new Library(libraries.getLong(1), libraries.getString(2), libraries.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object [] librariesArray = librariesArrayList.toArray();

        libraryList = new JComboBox<>(librariesArray);
        libraryList.setMaximumRowCount(librariesArray.length);
        libraryList.setSize(200, 20);
        libraryList.setLocation(80, 30);
        //libraryList.setEditable(true);

        panel.add(libraryList);

        bookLabel = Buildier.createLabel("Book", 70, 20,10, 60);
        panel.add(bookLabel);

        ResultSet books = dbHelper.getBooks("Title");
        booksArrayList = new ArrayList<>();
        try {
            while(books.next()) {
                booksArrayList.add(new Book(books.getString(1), books.getString(2), books.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object [] booksArray = booksArrayList.toArray();

        bookList = new JComboBox<>(booksArray);
        bookList.setMaximumRowCount(booksArray.length);
        bookList.setSize(200, 20);
        bookList.setLocation(80, 60);
        //bookList.setEditable(true);

        panel.add(bookList);

        amountLabel = Buildier.createLabel("Amount", 70, 20, 10, 90);
        panel.add(amountLabel);

        amountTextField = Buildier.createTextField(200, 20, 80, 90);
        panel.add(amountTextField);

        save = Buildier.createButton("Save", 80, 30, 115, 120);
        save.addActionListener(e -> onButtonClick());
        panel.add(save);

        add(panel);

    }

    public void onButtonClick()  {

        int libraryIndex = libraryList.getSelectedIndex();
        int booksIndex = bookList.getSelectedIndex();

        int amount;
        try{
            amount = Integer.parseInt(amountTextField.getText());
        } catch (NumberFormatException e) {
            Buildier.showErrorMessage("Error", "The number of instances cannot be identified");
            return;
        }

        if(amount <=  0) {
            Buildier.showErrorMessage("Error", "Invalid number of instances");
            return;
        }

        AdminOpportunities opportunities = AdminOpportunities.getInstance();

        if(id_bookfund != -1) {
            if (opportunities.redactBookFund(id_bookfund, libraryIndex, booksIndex,  amount, librariesArrayList, booksArrayList)) {
                dispose();
            } else {
                Buildier.showErrorMessage("Error", "Error when editing an entry in the book Fund");
            }
        } else {
            libraryIndex = libraryList.getSelectedIndex() != -1 ? libraryList.getSelectedIndex() : 0;
            booksIndex = bookList.getSelectedIndex() != -1 ? bookList.getSelectedIndex() : 0;
            long id_library = librariesArrayList.get(libraryIndex).getId();
            String id_book = booksArrayList.get(booksIndex).getISBN();
            if (opportunities.addBookFund(id_library, id_book, amount)) {
                dispose();
            } else {
                Buildier.showErrorMessage("Error", "Error when adding an entry in the book Fund");
            }
        }
    }

}
