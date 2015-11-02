/**
 * Created by Chris on 9/14/2014.
 */

/**
 * The Webpage class represents bibliographic informaton for webpages.
 */
public class Webpage implements IPublication {

    private final String title, url, retrieved;

    /**
     * Construct a Webpage object
     * @param title the webpage title
     * @param url the url of the webpage
     * @param retrieved the date the webpage was retrieved
     *
     */
    public Webpage(String title, String url, String retrieved) {
        this.title = title;
        this.url = url;
        this.retrieved = retrieved;
    }

    public String citeApa() {
        return title + ". Retrieved " + retrieved + ", from " + url;
    }

    public String citeMla() {
        return "“" + title + ".” Web. " + retrieved + " <" + url + ">";
    }
}
