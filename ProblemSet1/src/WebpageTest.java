import org.junit.Test;

import static org.junit.Assert.*;

public class WebpageTest {

    IPublication webpage = new Webpage("CS3500: Object-Oriented Design",
            "http://www.ccs.neu.edu/home/tov/course/cs3500/",
            "September 9,2014");

    @Test
    public void testCiteApa() throws Exception {
        assertEquals(webpage.citeApa(),
                "CS3500: Object-Oriented Design. Retrieved September 9,2014," +
                       " from http://www.ccs.neu.edu/home/tov/course/cs3500/");
    }

    @Test
    public void testCiteMla() throws Exception {
        assertEquals(webpage.citeMla(),
                "“CS3500: Object-Oriented Design.” Web. September 9,2014 " +
                        "<http://www.ccs.neu.edu/home/tov/course/cs3500/>");
    }
}