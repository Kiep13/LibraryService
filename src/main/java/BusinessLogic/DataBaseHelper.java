package BusinessLogic;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseHelper {

    private static DataBaseHelper dbHelper;
    private Connection connection;
    private PreparedStatement statement;

    private DataBaseHelper() {

        String url = "jdbc:mysql://localhost/libraryservice?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "1234";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("The connection was successfully established");

            connection = DriverManager.getConnection(url, username, password);
        }
        catch(Exception ex){
            System.out.println("Error establishing connection");
            System.out.println(ex);
        }

    }

    public static DataBaseHelper getInstance() {
        if(dbHelper == null) {
            dbHelper = new DataBaseHelper();
        }
        return dbHelper;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean existsLibrary(String name, String address, String phone) {
        String request = "SELECT id_library FROM libraries WHERE LOWER(name) = LOWER(?) OR LOWER(address) = LOWER(?) OR LOWER(telephone) = LOWER(?)";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, phone);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public boolean existsBook(String isbn) {
        String request = "SELECT * FROM books WHERE ISBN = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public boolean existsBookFund(String id_book, long id_library) {
        String request = "SELECT * FROM bookfund WHERE id_book = ? AND id_library = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, id_book);
            statement.setLong(2, id_library);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ResultSet findUser(String login, String password) {
        String request = "SELECT login FROM admins WHERE login = ? AND password = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findLibrary(long id_library) {
        String request = "SELECT * FROM libraries WHERE id_library = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1, id_library);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findBook(String isbn) {
        String request = "SELECT * FROM books WHERE ISBN = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, isbn);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findAuthor(long id_author) {
        String request = "SELECT CONCAT(surname, ' ', name, ' ', patronymic) FROM authors WHERE id_author = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1,id_author);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findGenre(long id_genre) {
        String request = "SELECT genre FROM genres WHERE id_genre = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1,id_genre);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findPublisher(long id_publisher) {
        String request = "SELECT publisher FROM publishers WHERE id_publisher = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1,id_publisher);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findBookFund(long id_bookfund) {
        String request = "SELECT id_book, id_library, amount FROM bookfund WHERE id_bookfund = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1,id_bookfund);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findBookById(String isbn) {
        String request = "SELECT CONCAT(title, ' : ', year, 'г.') FROM books WHERE ISBN = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1,isbn);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findLibraryById(long id_library) {
        String request = "SELECT name FROM libraries WHERE id_library = ?";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1,id_library);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean addLibrary(String name, String address, String telephone) {
        String request = "INSERT libraries (name, address, telephone) VALUES (?, ?, ?)";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, telephone);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addGenre(String genreName) {
        String request = "INSERT genres (genre) VALUES (?)";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, genreName);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addPublisher(String publisherName) {
        String request = "INSERT publishers (publisher) VALUES (?)";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, publisherName);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addAuthor(String surname, String name, String patronymic) {
        String request = "INSERT authors (surname, name, patronymic) VALUES (?, ?, ?)";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.setString(3, patronymic);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addBook(String isbn, String title, long id_author, long id_genre, long id_publisher, Integer pages, Integer year) {
        String request = "INSERT books (ISBN, title, id_author, id_genre, id_publisher, amount_pages, year) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, isbn);
            statement.setString(2, title);
            statement.setLong(3, id_author);
            statement.setLong(4, id_genre);
            statement.setLong(5, id_publisher);
            statement.setInt(6, pages);
            statement.setInt(7, year);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addBookFund(String id_book, long id_library, int amount, String login) {
        try {

            String request = "INSERT bookfund (id_book, id_library, amount, login) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(request);
            statement.setString(1, id_book);
            statement.setLong(2, id_library);
            statement.setInt(3, amount);
            statement.setString(4, login);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean redactLibrary(long id_library,  String name, String address, String telephone) {
        String request = "UPDATE libraries SET name = ?, address = ?,  telephone = ? WHERE id_library = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, telephone);
            statement.setLong(4, id_library);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean redactBook(String isbn, String title, long id_author, long id_genre, long id_publisher, Integer amount_pages, Integer year) {
        String request = "UPDATE books SET title = ?, id_author = ?,  id_genre = ?, id_publisher = ?,  amount_pages = ?, year = ? WHERE ISBN = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, title);
            statement.setLong(2, id_author);
            statement.setLong(3, id_genre);
            statement.setLong(4, id_publisher);
            statement.setInt(5, amount_pages);
            statement.setInt(6, year);
            statement.setString(7, isbn);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean redactBookFund(long id_bookfund, String id_book, long id_library, int amount, String login) {
        try {

            String request = "UPDATE bookfund SET id_book = ?, id_library = ?, amount = ?, login = ? WHERE id_bookfund = ?";
            statement = connection.prepareStatement(request);
            statement.setString(1, id_book);
            statement.setLong(2, id_library);
            statement.setInt(3, amount);
            statement.setLong(4, id_bookfund);
            statement.setString(5, login);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getGenres() {
        String request = "SELECT genre FROM genres ORDER BY genre";
        ResultSet result = null;
        ArrayList<String> genres = new ArrayList<>();
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
            while (result.next()) {
                genres.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public ArrayList<String> getPublishers() {
        String request = "SELECT publisher FROM publishers ORDER BY publisher";
        ResultSet result = null;
        ArrayList<String> publishers = new ArrayList<>();
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
            while (result.next()) {
                publishers.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }

    public ArrayList<String> getAuthors() {
        String request = "SELECT surname, name, patronymic FROM authors ORDER BY surname, name, patronymic";
        ResultSet result = null;
        ArrayList<String> authors = new ArrayList<>();
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
            while (result.next()) {
                authors.add(result.getString(1) + " " +  result.getString(2) + " " + result.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ResultSet getLibraries(String sortLabel) {
        String request = "SELECT * FROM libraries ORDER BY " + sortLabel;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getBooks(String sortLabel) {
        String request = "SELECT ISBN, b.title, CONCAT(a.surname, ' ', a.name, ' ', a.patronymic) AS author, g.genre, p.publisher, b.amount_pages, b.year " +
                         "FROM books AS b " +
                         "LEFT OUTER JOIN authors AS a ON b.id_author = a.id_author " +
                         "LEFT OUTER JOIN genres AS g ON b.id_genre = g.id_genre " +
                         "LEFT OUTER JOIN publishers AS p ON b.id_publisher = p.id_publisher ORDER BY " + sortLabel;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getBookFund(String sortLabel) {
        String request = "SELECT CONCAT(a.surname, ' ', a.name, ' ', a.patronymic) AS author, b.title, p.publisher, b.year, b.amount_pages, g.genre, b.ISBN, l.name, l.address, l.telephone " +
                "FROM  bookfund AS bf " +
                "INNER JOIN books AS b ON b.ISBN = bf.id_book " +
                "INNER JOIN libraries AS l ON l.id_library = bf.id_library " +
                "LEFT OUTER JOIN authors AS a ON b.id_author = a.id_author " +
                "LEFT OUTER JOIN genres AS g ON b.id_genre = g.id_genre " +
                "LEFT OUTER JOIN publishers AS p ON b.id_publisher = p.id_publisher ORDER BY " + sortLabel;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Для авторов, жанров и издателей можно использовать Left Outer Join. Это аозволит иметь пустые записи в след.полях
    //Но тогда, получится что при поиске будут выдаваться все значения с акцетом на совпадения по LIKE
    //Поэтому 18.03.20 поставила все соединения INNER JOIN
    //Есть смысл еще хадуматься о своих действиях
    public ResultSet getCatalog(String sortLabel, String id_library, String titleMask, String genreMask,
                                String authorMask, String publisherMask, String isbnMask, int pageMin,
                                int pageMax, int yearMin, int yearMax) {
        String request = "SELECT CONCAT(a.surname, ' ', a.name, ' ', a.patronymic) AS author, b.title, p.publisher, b.year, b.amount_pages, g.genre, b.ISBN, l.name, l.address, l.telephone " +
                "FROM  bookfund AS bf " +
                "INNER JOIN books AS b ON b.ISBN = bf.id_book AND LOWER(b.title) LIKE LOWER(?) AND b.ISBN LIKE ? AND b.amount_pages BETWEEN ? AND ? AND b.year BETWEEN ? AND ? " +
                "INNER JOIN libraries AS l ON l.id_library = bf.id_library AND bf.id_library LIKE ? " +
                "INNER JOIN authors AS a ON b.id_author = a.id_author AND LOWER(CONCAT(a.surname, ' ', a.name, ' ', a.patronymic)) LIKE LOWER(?) " +
                "INNER JOIN genres AS g ON b.id_genre = g.id_genre AND LOWER(g.genre) LIKE LOWER(?) " +
                "INNER JOIN publishers AS p ON b.id_publisher = p.id_publisher AND LOWER(p.publisher) LIKE LOWER(?) ORDER BY " + sortLabel;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, titleMask);
            statement.setString(2, isbnMask);
            statement.setInt(3, pageMin);
            statement.setInt(4, pageMax);
            statement.setInt(5, yearMin);
            statement.setInt(6, yearMax);
            statement.setString(7, id_library);
            statement.setString(8, authorMask);
            statement.setString(9, genreMask);
            statement.setString(10, publisherMask);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getShortInfoBooks() {
        String request = "SELECT ISBN, title, year FROM books";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getShortInfoLibraries() {
        String request = "SELECT id_library, name, address FROM libraries";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getShortInfoBookFund(String sortLabel) {
        String request = "SELECT bf.id_bookfund, bf.id_book, bf.id_library, CONCAT(a.surname, ' ', a.name, ' ', a.patronymic) AS author " +
                "FROM  bookfund AS bf " +
                "INNER JOIN books AS b ON b.ISBN = bf.id_book " +
                "INNER JOIN libraries AS l ON l.id_library = bf.id_library " +
                "LEFT OUTER JOIN authors AS a ON b.id_author = a.id_author " +
                "LEFT OUTER JOIN genres AS g ON b.id_genre = g.id_genre " +
                "LEFT OUTER JOIN publishers AS p ON b.id_publisher = p.id_publisher ORDER BY " + sortLabel;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ResultSet getGenreId(String genre) {

        String request = "SELECT id_genre FROM genres WHERE LOWER(genre) = LOWER(?)";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, genre);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getPublisherId(String publisher) {

        String request = "SELECT id_publisher FROM publishers WHERE  LOWER(publisher) =  LOWER(?)";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, publisher);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getAuthorId(String surname,String name,String patronymic) {

        String request = "SELECT id_author FROM authors WHERE LOWER(surname) = LOWER(?) AND LOWER(name) = LOWER(?) AND LOWER(patronymic) = LOWER(?)";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.setString(3, patronymic);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean deleteLibrary(Long id_library) {

        String request = "DELETE FROM libraries WHERE id_library = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1, id_library);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteBook(String isbn) {

        String request = "DELETE FROM books WHERE ISBN = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setString(1, isbn);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteBookFund(long id_bookfund) {

        String request = "DELETE FROM bookfund WHERE id_bookfund = ?";
        try {
            statement = connection.prepareStatement(request);
            statement.setLong(1, id_bookfund);
            int result = statement.executeUpdate();
            if(result == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getMaxPage() {

        String request = "SELECT MAX(amount_pages) FROM books";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMaxYear() {

        String request = "SELECT MAX(year) FROM books";
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(request);
            result = statement.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////v

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

}
