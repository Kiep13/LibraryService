package Data;

public class Author {

    private long id_author;
    private String surname;
    private String name;
    private String patronymic;

    public Author(long id_author, String surname, String name, String patronymic) {
        this.id_author = id_author;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public long getId_author() {
        return id_author;
    }

    public void setId_author(long id_author) {
        this.id_author = id_author;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return this.surname + " " + this.name + " " + this.patronymic;
    }
}
