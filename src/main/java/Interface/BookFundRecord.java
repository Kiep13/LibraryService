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

    public ArrayList<Book> booksArrayList;
    public ArrayList<Library> librariesArrayList;
    long id_bookfund;

    public BookFundRecord() {
        execute();
        id_bookfund = -1;

    }

    public BookFundRecord(long id_bookfund) {

        execute();
        this.id_bookfund = id_bookfund;

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        try {
            ResultSet bookfund = dbHelper.findBookFund(id_bookfund);
            bookfund.next();

            String isbn = bookfund.getString(1);
            long id_library = bookfund.getLong(2);

            ResultSet library = dbHelper.findLibraryById(id_library);
            library.next();
            libraryList.setSelectedItem(library.getString(1));

            ResultSet book = dbHelper.findBookById(isbn);
            book.next();
            bookList.setSelectedItem(book.getString(1));

            amountTextField.setText(bookfund.getString(3));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void execute() {

        setTitle("BookFund record");
        setSize(310, 210);
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

        libraryLabel = new JLabel("Library");
        libraryLabel.setSize(70, 20);
        libraryLabel.setLocation(10, 30);

        panel.add(libraryLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        ResultSet libraries = dbHelper.getShortInfoLibraries();
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
        libraryList.setEditable(true);

        panel.add(libraryList);

        bookLabel = new JLabel("Book");
        bookLabel.setSize(70, 20);
        bookLabel.setLocation(10, 60);

        panel.add(bookLabel);

        ResultSet books = dbHelper.getShortInfoBooks();
        booksArrayList = new ArrayList<>();
        try {
            while(books.next()) {
                booksArrayList.add(new Book(books.getString(1), books.getString(2), books.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object [] booksArray = booksArrayList.toArray();

        bookList = new JComboBox<>(booksArray);
        bookList.setSize(200, 20);
        bookList.setLocation(80, 60);
        bookList.setEditable(true);

        panel.add(bookList);

        amountLabel = new JLabel("Amount");
        amountLabel.setSize(70, 20);
        amountLabel.setLocation(10, 90);

        panel.add(amountLabel);

        amountTextField = new JTextField();
        amountTextField.setSize(200, 20);
        amountTextField.setLocation(80, 90);

        panel.add(amountTextField);

        save = new JButton("Save");
        save.addActionListener(e -> onButtonClick());
        save.setSize(80, 30);
        save.setLocation(115,120);

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
            e.printStackTrace();
            Buildier.ShowErrorMessage("Ошибка", "Невозможно иденцифитровать количество экземпляров");
            return;
        }

        if(amount <=  0) {
            Buildier.ShowErrorMessage("Ошибка", "Некорректное количество экземпляров");
            return;
        }

        AdminOpportunities opportunities = AdminOpportunities.getInstance();

        if(id_bookfund != -1) {
            if (opportunities.redactBookFund(id_bookfund, libraryIndex, booksIndex,  amount, this)) {
                dispose();
            } else {
                Buildier.ShowErrorMessage("Ошибка", "Ошибка при редактировании записи в книжном фонде");
            }
        } else {
            if (opportunities.addBookFund(booksIndex, libraryIndex, amount, this)) {
                dispose();
            } else {
                Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении записи в книжном фонде");
            }
        }
    }

}
