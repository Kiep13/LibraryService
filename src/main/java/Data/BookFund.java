package Data;

public class BookFund {

    public long id_bookfund;
    public String id_book;
    public long id_library;
    public int amount;
    public String login;

    public BookFund(long id_bookfund, String id_book, long id_library, int amount, String login) {

        this.id_bookfund = id_bookfund;
        this.id_book = id_book;
        this.id_library = id_library;
        this.amount = amount;
        this.login = login;

    }

    public BookFund(long id_bookfund,String id_book, long id_library) {

        this.id_bookfund = id_bookfund;
        this.id_book = id_book;
        this.id_library = id_library;

    }

    public long getId_bookfund() {
        return id_bookfund;
    }

    public void setId_bookfund(long id_bookfund) {
        this.id_bookfund = id_bookfund;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public long getId_library() {
        return id_library;
    }

    public void setId_library(long id_library) {
        this.id_library = id_library;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
