package grabber;

import java.util.Date;

public class Post {
    private int id;
    private String subject;
    private String link;
    private String description;
    private Date create_date;

    public Post(String subject, String link, String description, Date createDate) {
        this.subject = subject;
        this.link = link;
        this.description = description;
        this.create_date = createDate;
    }

    public Post(String subject, String link, Date createDate) {
        this.subject = subject;
        this.link = link;
        this.description = "";
        this.create_date = createDate;
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

    public Date getCreate_date() {
        return create_date;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.subject, this.link);
    }


    public void setId(int id) {
        this.id = id;
    }
}
