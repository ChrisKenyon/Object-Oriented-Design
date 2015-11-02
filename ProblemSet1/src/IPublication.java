/**
 * Created by Chris on 9/14/2014.
 */
/**
 * The `IPublication` interface specifies operations for formatting
 * citations from bibliographic data.
 */
public interface IPublication {
    /**
     * To format a citation in APA style.
     * @return the formatted citation
     */
    String citeApa ();

    /**
     * To format a citation in MLA style.
     * @return the formatted citation
     */
    String citeMla ();
}
