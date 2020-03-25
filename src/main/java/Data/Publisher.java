package Data;

public class Publisher {

    private long id_publisher;
    private String publisher;

    public Publisher(long id_publisher, String publisher) {
        this.id_publisher = id_publisher;
        this.publisher = publisher;
    }

    public long getId_publisher() {
        return id_publisher;
    }

    public void setId_publisher(long id_publisher) {
        this.id_publisher = id_publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
