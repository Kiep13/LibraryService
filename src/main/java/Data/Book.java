package Data;

public class Book {

    private String ISBN;
    private String title;
    private long id_author;
    private long id_genre;
    private long id_publisher;
    private int amount_pages;
    private int year;

    public Book(String ISBN, String title, long id_author, long id_genre, long id_publisher, int amount_pages, int year) {

        this.ISBN = ISBN;
        this.title = title;
        this.id_author = id_author;
        this.id_genre = id_genre;
        this.id_publisher = id_publisher;
        this.amount_pages = amount_pages;
        this.year = year;

    }

    public Book(String ISBN, String title,  int year) {

        this.ISBN = ISBN;
        this.title = title;
        this.year = year;

    }

    public Book(String ISBN, long id_author, long id_genre,  long id_publisher) {

        this.ISBN = ISBN;
        this.id_author = id_author;
        this.id_genre = id_genre;
        this.id_publisher = id_publisher;

    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId_author() {
        return id_author;
    }

    public void setId_author(long id_author) {
        this.id_author = id_author;
    }

    public long getId_genre() {
        return id_genre;
    }

    public void setId_genre(long id_genre) {
        this.id_genre = id_genre;
    }

    public long getId_publisher() {
        return id_publisher;
    }

    public void setId_publisher(long id_publisher) {
        this.id_publisher = id_publisher;
    }

    public int getAmount_pages() {
        return amount_pages;
    }

    public void setAmount_pages(int amount_pages) {
        this.amount_pages = amount_pages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return this.title + " : " + this.year + "г.";

    }
}
