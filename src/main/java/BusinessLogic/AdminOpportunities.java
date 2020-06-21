package BusinessLogic;

import Data.Admin;
import Data.Book;
import Data.BookFund;
import Data.Library;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminOpportunities {

    private static AdminOpportunities opportunities;

    Admin admin;
    public DataBaseHelper dbHelper;
    public JFrame window;

    public JTable libraryTable;
    public JComboBox<Object> libraryFieldList;
    public ArrayList<Library> libraryArrayList;

    public JTable bookTable;
    public JComboBox<Object> bookFieldList;
    public ArrayList<Book> bookArrayList;

    public JTable bfTable;
    public JComboBox<Object> bfFieldList;
    public ArrayList<BookFund> bfArrayList;

    public String[] libraryHeaders = new String [] {"ID", "Library", "Address", "Telephone"};
    public String[] bookHeaders = new String [] {"ISBN", "Title", "Author", "Genre", "Publisher", "Pages", "Year"};
    public String[] bookFundHeaders = new String [] {"ID", "Author", "Title", "Publisher", "Year", "Pages", "Genre", "ISBN", "Library", "Address", "Telephone", "Amount"};

    private AdminOpportunities() {

        dbHelper = DataBaseHelper.getInstance();

    }

    public static AdminOpportunities getInstance() {
        if (opportunities == null) {
            opportunities = new AdminOpportunities();
        }
        return opportunities;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setAdmin(String login) {
        this.admin = new Admin(login);
    }

    public void setFrame(JFrame window) {
        this.window = window;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean register(String login, String password, String repeatPassword) {

        if(login.length() == 0) {
            Buildier.showErrorMessage("Error", "Login field can not be empty!");
            return false;
        }

        if(password.length() == 0) {
            Buildier.showErrorMessage("Error", "Password field can not be empty!");
            return false;
        }

        if(password.length() < 3) {
            Buildier.showErrorMessage("Error", "Login value is to short!");
            return false;
        }

        if(password.length() < 4) {
            Buildier.showErrorMessage("Error", "Password value is too short!");
            return false;
        }

        if(password.compareTo(repeatPassword) != 0) {
            Buildier.showErrorMessage("Error", "Passwords don't match!");
            return false;
        }

        try {
            ResultSet resultOfExists = dbHelper.existsUser(login);
            if(!resultOfExists.next()) {
                dbHelper.addAdmin(login, password);
            }
        } catch (SQLException e) {
            Buildier.showErrorMessage("Error", "A user with this name already exists!");
            return false;
        }
        return true;
    }

    public boolean authorize(String login, String password) {

        if(login.length() == 0) {
            Buildier.showErrorMessage("Error", "Login field is empty");
            return false;
        }

        if(password.length() == 0) {
            Buildier.showErrorMessage("Error", "Password field is empty");
            return false;
        }

        ResultSet result = dbHelper.findUser(login, password);
        try {
            result.next();
            setAdmin(result.getString(1));
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateBooks() {

        String sortLabel = String.valueOf(bookFieldList.getSelectedItem());

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        bookArrayList = new ArrayList<>();
        try {
            ResultSet books = dbHelper.getBooks(sortLabel);
            model.setDataSource(books, bookHeaders);
            bookTable.setModel(model);

            books = dbHelper.getBooks(sortLabel);
            bookArrayList.clear();
            while (books.next()) {
                bookArrayList.add(new Book(books.getString(1), books.getString(2), books.getInt(7)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            Buildier.showErrorMessage("Error", "Errors when updating the table");
        }

    }

    public boolean addBook(String isbn, String title, String author, String genre, String publisher, int pages, int year) {

        if (dbHelper.existsBook(isbn)) {
            Buildier.showErrorMessage("Error", "A book with this ISBN already exists");
            return false;
        }

        long id_author = getAuthorId(author);
        long id_publisher = getPublisherId(publisher);
        long id_genre = getGenreId(genre);

        if (dbHelper.addBook(isbn, title, id_author, id_genre, id_publisher, pages, year)) {
            updateBooks();
            return true;
        } else {
            Buildier.showErrorMessage("Error", "Error when adding a book");
            return false;
        }
    }

    public boolean redactBook(String isbn, String title, String author, String genre, String publisher, int pages, int year) {

        long id_author = getAuthorId(author);
        long id_publisher = getPublisherId(publisher);
        long id_genre = getGenreId(genre);

        if (dbHelper.redactBook(isbn, title, id_author, id_genre, id_publisher, pages, year)) {
            updateBooks();
            return true;
        } else {
            Buildier.showErrorMessage("Error", "Error when editing a book");
            return false;
        }
    }

    public void deleteBook(String id_book) {
        if (dbHelper.deleteBook(id_book)) {
            updateBooks();
        } else {
            Buildier.showErrorMessage("Error", "Error deleting a book");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public long getAuthorId(String author) {

        long id_author = -1;

        String[] arrayAuthor = author.split(" ");
        if (arrayAuthor.length == 3) {
            arrayAuthor[0] = arrayAuthor[0].substring(0, 1).toUpperCase() + arrayAuthor[0].substring(1).toLowerCase();
            arrayAuthor[1] = arrayAuthor[1].substring(0, 1).toUpperCase() + arrayAuthor[1].substring(1).toLowerCase();
            arrayAuthor[2] = arrayAuthor[2].substring(0, 1).toUpperCase() + arrayAuthor[2].substring(1).toLowerCase();
        } else if (arrayAuthor.length == 2) {
            String surname = arrayAuthor[0].substring(0, 1).toUpperCase() + arrayAuthor[0].substring(1).toLowerCase();
            String name = arrayAuthor[1].substring(0, 1).toUpperCase() + arrayAuthor[1].substring(1).toLowerCase();
            arrayAuthor = new String[]{surname, name, ""};
        } else {
            Buildier.showErrorMessage("Error", "Too little information about the author");
            return id_author;
        }

        ResultSet resultSet = dbHelper.getAuthorId(arrayAuthor[0], arrayAuthor[1], arrayAuthor[2]);
        try {
            if (!resultSet.next()) {
                if (dbHelper.addAuthor(arrayAuthor[0], arrayAuthor[1], arrayAuthor[2])) {
                    resultSet = dbHelper.getAuthorId(arrayAuthor[0], arrayAuthor[1], arrayAuthor[2]);
                    resultSet.next();
                } else {
                    Buildier.showErrorMessage("Error", "Error when adding an author");
                }
            }
            id_author = resultSet.getLong(1);
        } catch (SQLException e) {
            Buildier.showErrorMessage("Error", "Errors when getting the list of authors");
        }
        return id_author;
    }

    public long getPublisherId(String publisher) {

        ResultSet resultSet = dbHelper.getPublisherId(publisher);
        long id_publisher = -1;
        try {
            if (!resultSet.next()) {
                if (dbHelper.addPublisher(publisher)) {
                    resultSet = dbHelper.getPublisherId(publisher);
                    resultSet.next();
                } else {
                    Buildier.showErrorMessage("Error", "Error when adding a publisher");
                }
            }
            id_publisher = resultSet.getLong(1);
        } catch (SQLException e) {
            Buildier.showErrorMessage("Error", "Errors when getting the publisher");
        }
        return id_publisher;
    }

    public long getGenreId(String genre) {

        ResultSet resultSet = dbHelper.getGenreId(genre);
        long id_genre = 0;
        try {
            if (!resultSet.next()) {
                if (dbHelper.addGenre(genre)) {
                    resultSet = dbHelper.getGenreId(genre);
                    resultSet.next();
                } else {
                    Buildier.showErrorMessage("Error", "Error when adding a genre");
                }
            }
            id_genre = resultSet.getLong(1);
        } catch (SQLException e) {
            Buildier.showErrorMessage("Error", "Errors when getting the genre");
        }

        return id_genre;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateLibraries() {

        String sortLabel = String.valueOf(libraryFieldList.getSelectedItem());

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        libraryArrayList = new ArrayList<>();
        try {
            ResultSet libraries = dbHelper.getLibraries(sortLabel);
            model.setDataSource(libraries, libraryHeaders);
            libraryTable.setModel(model);
            libraryTable.removeColumn(libraryTable.getColumnModel().getColumn(0));

            libraries = dbHelper.getLibraries(sortLabel);
            while (libraries.next()) {
                libraryArrayList.add(new Library(libraries.getLong(1),
                        libraries.getString(2), libraries.getString(3),
                        libraries.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            Buildier.showErrorMessage("Error", "Errors when updating the library table");
        }
    }

    public boolean addLibrary(String name, String address, String phone) {

        if (dbHelper.existsLibrary(name, address, phone, -1)) {
            Buildier.showErrorMessage("Error", "A library with this data already exists");
            return false;
        }

        if (dbHelper.addLibrary(name, address, phone)) {
            updateLibraries();
            return true;
        } else {
            Buildier.showErrorMessage("Error", "Error adding the library");
            return false;
        }
    }

    public boolean redactLibrary(long id_library, String name, String address, String phone) {

        if (dbHelper.existsLibrary(name, address, phone, id_library)) {
            Buildier.showErrorMessage("Error", "A library with this data already exists");
            return false;
        }

        if (dbHelper.redactLibrary(id_library, name, address, phone)) {
            updateLibraries();
            return true;
        } else {
            Buildier.showErrorMessage("Error", "Error when editing the library");
            return false;
        }
    }

    public void deleteLibrary(long id_library) {
        if (dbHelper.deleteLibrary(id_library)) {
            updateLibraries();
        } else {
            Buildier.showErrorMessage("Error", "Error deleting the library");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateBookFund() {

        String sortLabel = String.valueOf(bfFieldList.getSelectedItem());

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        bfArrayList = new ArrayList<>();
        try {
            ResultSet bookfund = dbHelper.getBookFund(sortLabel);
            model.setDataSource(bookfund, bookFundHeaders);
            bfTable.setModel(model);
            bfTable.removeColumn(bfTable.getColumnModel().getColumn(0));

            bookfund = dbHelper.getBookFund(sortLabel);
            while (bookfund.next()) {
                bfArrayList.add(new BookFund(bookfund.getLong(1)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            Buildier.showErrorMessage("Error", "Errors when updating the book Fund table");
        }

    }

    public boolean addBookFund(long id_library, String id_book, int amount) {

        if (dbHelper.existsBookFund(id_book, id_library, -1)) {
            Buildier.showErrorMessage("Error", "There is already a record of this book in this library");
            return false;
        }

        if (dbHelper.addBookFund(id_book, id_library, amount, admin.getLogin())) {
            updateBookFund();
            return true;
        } else {
            return false;
        }
    }

    public boolean redactBookFund(long id_bookfund, int libraryIndex, int booksIndex, int amount, ArrayList<Library> libraryRecordList, ArrayList<Book> bookRecordList) {
        ResultSet bookfund = dbHelper.findBookFund(id_bookfund);
        long id_library;
        String id_book;
        try {

            bookfund.next();

            if (libraryIndex != -1) {
                id_library = libraryRecordList.get(libraryIndex).getId();
            } else {
                id_library = bookfund.getLong(2);
            }

            if (booksIndex != -1) {
                id_book = bookRecordList.get(booksIndex).getISBN();
            } else {
                id_book = bookfund.getString(1);
            }

            if (dbHelper.existsBookFund(id_book, id_library, id_bookfund)) {
                Buildier.showErrorMessage("Error", "There is already a record of this book in this library");
                return false;
            }

            if (dbHelper.redactBookFund(id_bookfund, id_book, id_library, amount, admin.getLogin())) {
                updateBookFund();
            } else {
                Buildier.showErrorMessage("Error", "Error when editing an entry in the book Fund");
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void deleteBookFund(long id_bookfund) {
        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        if (dbHelper.deleteBookFund(id_bookfund)) {
            updateBookFund();
        } else {
            Buildier.showErrorMessage("Error", "Error when deleting a record book fund");
        }
    }

}
