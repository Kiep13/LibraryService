package BusinessLogic;

import Data.Admin;
import Data.Book;
import Data.BookFund;
import Data.Library;
import Interface.*;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminOpportunities {

    private static AdminOpportunities opportunities;

    Admin admin;
    public DataBaseHelper dbHelper;
    LibraryService window;

    LibraryPanel libraryPanel;
    BookPanel bookPanel;
    BookFundPanel bfPanel;

    private AdminOpportunities() {

        dbHelper = DataBaseHelper.getInstance();

    }

    public static AdminOpportunities getInstance() {
        if(opportunities == null) {
            opportunities = new AdminOpportunities();
        }
        return opportunities;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLibraryPanel(LibraryPanel libraryPanel) {
        this.libraryPanel = libraryPanel;
    }

    public void setBookPanel(BookPanel bookPanel) {
        this.bookPanel = bookPanel;
    }

    public void setBfPanel(BookFundPanel bfPanel) {
        this.bfPanel = bfPanel;
    }

    public void setAdmin(String login) {
        this.admin = new Admin(login);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setFrame(LibraryService window) {
        this.window = window;
    }

    public boolean login(String login, String password) {
        ResultSet result = dbHelper.findUser(login, password);
        boolean isFind = false;
        try{
          isFind =  result.next();
          setAdmin(result.getString(1));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        if(isFind) {


            int width = window.getWidth();
            int height = window.getHeight();

            window.remove(window.panel);
            window.panel = new AdminPanel(width, height);

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            window.getContentPane().add(window.panel, gbc);
            window.revalidate();
            window.repaint();
            window.setTitle("Admin Service");

        } else {
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateBookTable() {

        String sortLabel = String.valueOf(bookPanel.fieldsList.getSelectedItem());
        sortLabel = findBookColumnName(sortLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        bookPanel.bookArrayList = new ArrayList<>();
        try {
            ResultSet books = dbHelper.getBooks(sortLabel);
            model.setDataSource(books, bookPanel.columnsHeader);
            bookPanel.table.setModel(model);

            books = dbHelper.getBooks(sortLabel);
            bookPanel.bookArrayList.clear();
            while (books.next()) {
                bookPanel.bookArrayList.add(new Book(books.getString(1), books.getString(2), books.getInt(7)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String findBookColumnName(String columnName) {
        switch (columnName) {
            case "ISBN": return "ISBN";
            case "Название": return "b.title";
            case "Автор": return "author";
            case "Жанр": return "g.genre";
            case "Издатель": return "p.publisher";
            case "Количество страниц": return "b.amount_pages";
            case "Год": return "b.year";
        }
        return "";
    }

    public boolean addBook(String isbn, String title, String author,String genre, String publisher, String pages, String year) {

        if(isbn.length() != 10) {
            Buildier.ShowErrorMessage("Ошибка", "Ошибки при вводе кода книги");
            return false;
        }

        if(dbHelper.existsBook(isbn)) {
            Buildier.ShowErrorMessage("Ошибка", "Книга с подобным кодом уже существует");
            return false;
        }

        if(title.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Название книги отсутствует");
            return false;
        } else {
            title = title.substring(0,1).toUpperCase() + title.substring(1);
        }

        if(author.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Поле автора пустое");
            return false;
        }

        if(genre.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Поле жанра пустое");
            return false;
        } else {
            genre = genre.substring(0, 1).toUpperCase() + genre.substring(1).toLowerCase();
        }

        if(publisher.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Поле издателя пустое");
            return false;
        } else {
            publisher = publisher.substring(0, 1).toUpperCase() + publisher.substring(1);
        }

        if(pages.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Не указано количество страниц");
            return false;
        }

        int amount_pages, year_value;

        try{
            amount_pages = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.ShowErrorMessage("Ошибка", "Невозможно иденцифитровать количество страниц");
            return false;
        }

        if (amount_pages <= 0 ) {
            Buildier.ShowErrorMessage("Ошибка", "Неверное заданное количество страниц");
            return false;
        }

        try{
            year_value = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.ShowErrorMessage("Ошибка", "Невозможно иденцифитровать год");
            return false;
        }

        if (year_value <= 1500 || year_value > getCurrentYear()) {
            Buildier.ShowErrorMessage("Ошибка", "Неверное заданный год");
            return false;
        }

        long id_author = getAuthorId(author);
        long id_publisher = getPublisherId(publisher);
        long id_genre = getGenreId(genre);

        if(dbHelper.addBook(isbn, title, id_author, id_genre, id_publisher, amount_pages, year_value)) {
            updateBookTable();
            return true;
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении книги");
            return false;
        }
    }

    public boolean redactBook(String isbn, String title, String author,String genre, String publisher, String pages, String year) {

        if(isbn.length() != 10) {
            Buildier.ShowErrorMessage("Ошибка", "Ошибки при вводе кода книги");
            return false;
        }

        if(title.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Название книги отсутствует");
            return false;
        } else {
            title = title.substring(0,1).toUpperCase() + title.substring(1);
        }

        if(author.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Поле автора пустое");
            return false;
        }

        if(genre.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Поле жанра пустое");
            return false;
        } else {
            genre = genre.substring(0, 1).toUpperCase() + genre.substring(1).toLowerCase();
        }

        if(publisher.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Поле издателя пустое");
            return false;
        } else {
            publisher = publisher.substring(0, 1).toUpperCase() + publisher.substring(1);
        }

        if(pages.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Не указано количество страниц");
            return false;
        }

        int amount_pages, year_value;

        try{
            amount_pages = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.ShowErrorMessage("Ошибка", "Невозможно иденцифитровать количество страниц");
            return false;
        }

        if (amount_pages <= 0 ) {
            Buildier.ShowErrorMessage("Ошибка", "Неверное заданное количество страниц");
            return false;
        }

        try{
            year_value = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Buildier.ShowErrorMessage("Ошибка", "Невозможно иденцифитровать год");
            return false;
        }

        if (year_value <= 1500 || year_value > getCurrentYear()) {
            Buildier.ShowErrorMessage("Ошибка", "Неверное заданный год");
            return false;
        }

        long id_author = getAuthorId(author);
        long id_publisher = getPublisherId(publisher);
        long id_genre = getGenreId(genre);

        if(dbHelper.redactBook(isbn, title, id_author, id_genre, id_publisher, amount_pages, year_value)) {
            updateBookTable();
            return true;
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при редактировании книги");
            return false;
        }
    }

    public void deleteBook(String id_book) {
        if(dbHelper.deleteBook(id_book)) {
            updateBookTable();
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при удалении книги");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public long getAuthorId(String author){

        long id_author = -1;

        String [] arrayAuthor = author.split(" ");
        if(arrayAuthor.length == 3) {
            arrayAuthor[0] = arrayAuthor[0].substring(0,1).toUpperCase() + arrayAuthor[0].substring(1).toLowerCase();
            arrayAuthor[1] = arrayAuthor[1].substring(0,1).toUpperCase() + arrayAuthor[1].substring(1).toLowerCase();
            arrayAuthor[2] = arrayAuthor[2].substring(0,1).toUpperCase() + arrayAuthor[2].substring(1).toLowerCase();
        } else if( arrayAuthor.length == 2) {
            String surname = arrayAuthor[0].substring(0,1).toUpperCase() + arrayAuthor[0].substring(1).toLowerCase();
            String name = arrayAuthor[1].substring(0,1).toUpperCase() + arrayAuthor[1].substring(1).toLowerCase();
            arrayAuthor = new String[] {surname, name, ""};
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Слишком мало данных об авторе");
            return id_author;
        }

        ResultSet resultSet = dbHelper.getAuthorId(arrayAuthor[0], arrayAuthor[1], arrayAuthor[2]);
        try {
            if(!resultSet.next()) {
                if(dbHelper.addAuthor(arrayAuthor[0], arrayAuthor[1], arrayAuthor[2])) {
                    resultSet = dbHelper.getAuthorId(arrayAuthor[0], arrayAuthor[1], arrayAuthor[2]);
                    resultSet.next();
                } else {
                    Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении автора");
                }
            }
            id_author = resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_author;
    }

    public long getPublisherId(String publisher) {

        ResultSet resultSet = dbHelper.getPublisherId(publisher);
        long id_publisher = -1;
        try {
            if(!resultSet.next()) {
                if(dbHelper.addPublisher(publisher)) {
                    resultSet = dbHelper.getPublisherId(publisher);
                    resultSet.next();
                } else {
                    Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении издателя");
                }
            }
            id_publisher = resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_publisher;
    }

    public long getGenreId(String genre) {

        ResultSet resultSet = dbHelper.getGenreId(genre);
        long id_genre = 0;
        try {
            if(!resultSet.next()) {
                if(dbHelper.addGenre(genre)) {
                    resultSet = dbHelper.getGenreId(genre);
                    resultSet.next();
                } else {
                    Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении жанра");
                }
            }
            id_genre = resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id_genre;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateLibraryTable() {

        String sortLabel = String.valueOf(libraryPanel.fieldsList.getSelectedItem());
        sortLabel =findLibraryColumnName(sortLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        libraryPanel.libraryArrayList = new ArrayList<>();
        try {
            ResultSet libraries = dbHelper.getLibraries(sortLabel);
            model.setDataSource(libraries, libraryPanel.columnsHeader);
            libraryPanel.table.setModel(model);
            libraryPanel.table.removeColumn(libraryPanel.table.getColumnModel().getColumn(0));

            libraries = dbHelper.getLibraries(sortLabel);
            libraryPanel.libraryArrayList.clear();
            while (libraries.next()) {
                libraryPanel.libraryArrayList.add(new Library(libraries.getLong(1), libraries.getString(2), libraries.getString(3), libraries.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String findLibraryColumnName(String columnName) {
        switch (columnName) {
            case "Идентификатор":
                return "id_library";
            case "Библиотека":
                return "name";
            case "Адрес":
                return "address";
            case "Телефон":
                return "telephone";
        }
        return "";
    }

    public boolean addLibrary(String name, String address, String phone){

        if(name.length() == 0 || address.length() == 0 || phone.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Пустые поля");
            return false;
        }

        if(dbHelper.existsLibrary(name, address, phone)) {
            Buildier.ShowErrorMessage("Ошибка", "Библиотека с такими данными уже существует");
            return false;
        }

        if (dbHelper.addLibrary(name, address, phone)) {
            updateLibraryTable();
            return true;
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении библиотеки");
            return false;
        }
    }

    public boolean redactLibrary(long id_library, String name, String address, String phone) {

        if(name.length() == 0 || address.length() == 0 || phone.length() == 0) {
            Buildier.ShowErrorMessage("Ошибка", "Пустые поля");
            return false;
        }

        if(dbHelper.existsLibrary(name, address, phone)) {
            Buildier.ShowErrorMessage("Ошибка", "Библиотека с такими данными уже существует");
            return false;
        }

        if (dbHelper.redactLibrary(id_library, name, address, phone)) {
            updateLibraryTable();
            return true;
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при редактировании библиотеки");
            return false;
        }
    }

    public void deleteLibrary(long id_library) {
        if(dbHelper.deleteLibrary(id_library)) {
            updateLibraryTable();
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при удалении библиотеки");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateBookFundTable() {

        String sortLabel = String.valueOf(bfPanel.fieldsList.getSelectedItem());
        sortLabel = findBfColumnName(sortLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        bfPanel.bfArrayList = new ArrayList<>();
        try {
            ResultSet bookfund = dbHelper.getBookFund(sortLabel);
            model.setDataSource(bookfund, bfPanel.columnsHeader);
            bfPanel.table.setModel(model);

            bookfund = dbHelper.getShortInfoBookFund(sortLabel);
            while (bookfund.next()) {
                bfPanel.bfArrayList.add(new BookFund(bookfund.getLong(1), bookfund.getString(2), bookfund.getLong(3)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String findBfColumnName(String columnName) {
        switch (columnName) {
            case "Автор": return "author";
            case "Название": return "b.title";
            case "Издательство": return "p.publisher";
            case "Год издания": return "b.year";
            case "Количество страниц": return "b.amount_pages";
            case "Жанр": return "g.genre";
            case "ISBN": return "b.ISBN";
            case "Библиотека": return "l.name";
            case "Адрес": return "l.address";
            case "Телефон": return "l.telephone";
        }
        return "";
    }

    public boolean addBookFund(int booksIndex, int libraryIndex, int amount, BookFundRecord record) {
        long id_library = record.librariesArrayList.get(libraryIndex).getId();
        String id_book = record.booksArrayList.get(booksIndex).getISBN();

        if(dbHelper.existsBookFund(id_book, id_library)) {
            Buildier.ShowErrorMessage("Ошибка", "Запись об этой книге в данной библиотеке уже сущесвует");
            return false;
        }

        if (dbHelper.addBookFund(id_book, id_library, amount, admin.getLogin())) {
            updateBookFundTable();
            return true;
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при добавлении записи в книжном фонде");
            return false;
        }
    }

    public boolean redactBookFund(long id_bookfund, int libraryIndex, int booksIndex, int amount, BookFundRecord record) {
        ResultSet bookfund = dbHelper.findBookFund(id_bookfund);
        long id_library;
        String id_book;
        try {

            bookfund.next();

            if (libraryIndex != -1) {
                id_library = record.librariesArrayList.get(libraryIndex).getId();
            } else {
                id_library = bookfund.getLong(2);
            }

            if (booksIndex != -1) {
                id_book = record.booksArrayList.get(booksIndex).getISBN();
            } else {
                id_book = bookfund.getString(1);
            }

            if (dbHelper.redactBookFund(id_bookfund, id_book, id_library, amount, admin.getLogin())) {
                updateBookFundTable();
            } else {
                Buildier.ShowErrorMessage("Ошибка", "Ошибка при редактировании записи в книжном фонде");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteBookFund(long id_bookfund) {
        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        if(dbHelper.deleteBookFund(id_bookfund)) {
            updateBookFundTable();
        } else {
            Buildier.ShowErrorMessage("Ошибка", "Ошибка при удалении записи книжного фонда");
        }
    }

    public int getCurrentYear()
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

}
