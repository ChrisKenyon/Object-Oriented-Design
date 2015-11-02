import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayRasterTest {


    @Test
    public void testWidth() throws Exception {
        ArrayRaster arrayRaster = new ArrayRaster(5, 5);
        assertEquals(5, arrayRaster.width());
    }

    @Test
    public void testHeight() throws Exception {
        ArrayRaster arrayRaster = new ArrayRaster(5, 5);
        assertEquals(5, arrayRaster.height());
    }

    @Test

    public void testConstructWithBuffer() {
        int[] buffer = new int[25];
        Arrays.fill(buffer, 0x1144DD);
        ArrayRaster arrayRaster = new ArrayRaster(5, 5, buffer);
        assertEquals(5, arrayRaster.height());
        assertEquals(5, arrayRaster.width());

        int[] testBuffer = arrayRaster.getBuffer();
        for (int i = 0; i < arrayRaster.width() * arrayRaster.height(); i++) {
            assertEquals(0x1144DD, testBuffer[i]);
        }

    }

    @Test
    public void testGetBuffer() throws Exception {
        ArrayRaster arrayRaster = new ArrayRaster(5, 5);
        arrayRaster.clear(0xAAAAAA);
        int[] buffer = arrayRaster.getBuffer();
        for (int color : buffer) {
            assertTrue(color == 0xAAAAAA);
        }
    }


    @Test
    public void testGetSetRGBA() throws Exception {
        ArrayRaster arrayRaster = new ArrayRaster(5, 5);
        Point<Integer> integerPoint = IntegerPoint.point(3, 3);
        arrayRaster.setRGBA(integerPoint, 0xBBFFAA);
        assertEquals(0xBBFFAA, arrayRaster.getRGBA(integerPoint));
    }

    @Test
    public void testClear() throws Exception {
        ArrayRaster arrayRaster = new ArrayRaster(5, 5);
        arrayRaster.clear(0xAAAAAA);
        boolean fail = false;
        for (int h = 0; h < arrayRaster.height(); h++) {
            for (int w = 0; w < arrayRaster.width(); w++) {
                if (arrayRaster.getRGBA(IntegerPoint.point(w, h)) != 0xAAAAAA) {
                    fail = true;
                }
            }
        }
        if (fail == true) {
            assertFalse(fail);
        }
    }
}