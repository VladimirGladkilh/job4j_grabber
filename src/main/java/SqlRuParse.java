import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SqlRuParse {

    private static final String TODAY_TEXT = "сегодня";
    private static final String YESTERDAY_TEXT = "вчера";

    /**
     * В полях с датой надо производить замену текста Сегодня и Вчера на соответствующие даты
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SimpleDateFormat form = new SimpleDateFormat("dd MMM yy, hh:mm" );
        SimpleDateFormat shortForm = new SimpleDateFormat("dd MMM yy" );
        int pageCount = 5;
        Date yesterday = yesterday();
        for (int i =1; i <= pageCount; i ++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element parent = td.parent();
                Element dataElement = parent.child(5);
                String dataElementText = dataElement.text()
                        .replace(TODAY_TEXT, shortForm.format(new Date()))
                        .replace(YESTERDAY_TEXT, shortForm.format(yesterday));
                Date oldD = form.parse(dataElementText);
                System.out.println(oldD);
            }
        }
    }

    /**
     * все украдено до нас https://fooobar.com/questions/64063/get-yesterdays-date-using-date
     * @return
     */
    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}