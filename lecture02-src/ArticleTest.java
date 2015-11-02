import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {
    IPublication turing =
            new Article("Computing machinery and intelligence",
                        "A. M. Turing", "Mind", 59, 236, 1950);

    @Test
    public void testCiteApa() {
        assertEquals(turing.citeApa(),
                     "A. M. Turing (1950). Computing machinery and " +
                     "intelligence. Mind, 59(236).");
    }

    @Test
    public void testCiteMla() {
        assertEquals(turing.citeMla(),
                     "A. M. Turing. “Computing machinery and " +
                     "intelligence.” Mind 59.236 (1950).");
    }
}
