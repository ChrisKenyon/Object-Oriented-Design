import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    IPublication rushdie = new Book("Midnight’s Children", "Salman Rushdie",
                                    "Jonathan Cape", "London", 1980);

    @Test
    public void testCiteApa() {
        assertEquals(rushdie.citeApa(),
                     "Salman Rushdie (1980). Midnight’s Children. " +
                     "London: Jonathan Cape.");
    }

    @Test
    public void testCiteMla() {
        assertEquals(rushdie.citeMla(),
                     "Salman Rushdie. Midnight’s Children. London: " +
                     "Jonathan Cape, 1980.");
    }
}
