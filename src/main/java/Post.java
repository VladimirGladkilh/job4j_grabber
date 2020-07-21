import java.util.Date;

public class Post {
    private int id;
    private String subject;
    private String link;
    private String description;
    private Date create_date;

    public Post(String subject, String link, String description, Date create_date) {
        this.subject = subject;
        this.link = link;
        this.description = description;
        this.create_date = create_date;
    }

    public Post(String subject, String link, Date create_date) {
        this.subject = subject;
        this.link = link;
        this.description = "";
        this.create_date = create_date;
    }

    public Post(String subject, String link) {
        this.subject = subject;
        this.link = link;
        this.description = "";
        this.create_date = new Date();
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("{} ({})", this.subject, this.link);
    }
}
