package grabber;

import java.text.SimpleDateFormat;
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
        //return String.format("<tr><td><a href='%s'> %s</a></td><td>%s</td><td>%s</td></tr>", this.link, this.subject,
        //        new SimpleDateFormat("dd MMM yy hh:mm").format(this.create_date), this.description);
        return String.format("<tr><td width='35%%'><a href='%s'>%s</a></td><td width='15%%'>%s</td><td width='50%%'>%s</td></tr>",
                this.link, this.subject,
                new SimpleDateFormat("dd MMM yy hh:mm").format(this.create_date),
                this.description);
    }


    public void setId(int id) {
        this.id = id;
    }
}
