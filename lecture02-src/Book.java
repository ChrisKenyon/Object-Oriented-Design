/**
 * The `Book` class represents bibliographic information for books.
 */
public class Book implements IPublication {
    private final String title, author, publisher, location;
    private final int year;

    /**
     * Construct a `Book` object.
     *
     * @param title     the title of the book
     * @param author    the author of the book
     * @param publisher the publisher of the book
     * @param location  the location of the publisher
     * @param year      the year of publication
     */
    public Book(String title, String author, String publisher,
                String location, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.location = location;
        this.year = year;
    }

    public String citeApa() {
        return author + " (" + year + "). " + title + ". " +
                location + ": " + publisher + ".";
    }

    public String citeMla() {
        return author + ". " + title + ". " + location + ": " +
                publisher + ", " + year + ".";
    }
}
