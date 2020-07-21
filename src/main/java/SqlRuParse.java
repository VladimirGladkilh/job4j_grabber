import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SqlRuParse implements Parse {

    private static final String TODAY_TEXT = "сегодня";
    private static final String YESTERDAY_TEXT = "вчера";
    private static final SimpleDateFormat form = new SimpleDateFormat("dd MMM yy, hh:mm" );
    private static final SimpleDateFormat shortForm = new SimpleDateFormat("dd MMM yy" );

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    @Override
    public List<Post> list(String link) throws IOException, ParseException {
        List<Post> posts = new LinkedList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            String postLink = href.attr("href");
            posts.add(detail(postLink));
        }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException, ParseException {
        Date yesterday = yesterday();
        Document doc = Jsoup.connect(link).get();
        Elements rows = doc.select(".msgHeader");
        String subject = rows.get(0).text();

        rows = doc.select(".msgBody");
        String description = rows.get(1).text();

        rows = doc.select(".msgFooter");
        String footerText = rows.get(0).text().split("\\[")[0].trim();
        String dataElementText = footerText
                .replace(TODAY_TEXT, shortForm.format(new Date()))
                .replace(YESTERDAY_TEXT, shortForm.format(yesterday));
        Date create_date = form.parse(dataElementText);

        return new Post(subject, link, description, create_date);
    }
}