import org.junit.Test;
import org.junit.Assert;

import javax.swing.*;

import static org.junit.Assert.*;

public class ArrayRasterTest {

    @Test
    public void testWidth() throws Exception {

    }

    @Test
    public void testHeight() throws Exception {
    }

    @Test
    public void testGetBuffer() throws Exception {

    }

    ArrayRaster arrayRaster = new ArrayRaster(5,5);
    @Test
    public void testSetRGBA() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(3,3);
        arrayRaster.setRGBA(integerPoint,200);
        assertEquals(200, arrayRaster.getRGBA(integerPoint));
    }

    @Test
    public void testGetRGBA() throws Exception {

    }

    @Test
    public void testClear() throws Exception {

    }
}