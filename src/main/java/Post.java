public class Post {
    private int id;
    private String subject;
    private String link;
    private String description;

    public Post(String subject, String link, String description) {
        this.subject = subject;
        this.link = link;
        this.description = description;
    }

    public Post(String subject, String link) {
        this.subject = subject;
        this.link = link;
        this.description = "";
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
