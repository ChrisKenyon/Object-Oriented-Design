import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class SampleablesTest {

    Sampleable sampleable1 = new AbstractSampleable(BoundingBox.fromTBLR(0, 10, 0, 10)) {
        @Override
        public Color getColorAt(Point<Double> point) {
            return Colors.rgba(0, 0, 1, .5);
        }
    };
    Sampleable sampleable2 = new AbstractSampleable(BoundingBox.fromTBLR(0, 10, 0, 10)) {
        @Override
        public Color getColorAt(Point<Double> point) {
            return Colors.rgba(0, 1, 0, .5);
        }
    };
    Sampleable sampleable3 = new AbstractSampleable(BoundingBox.fromTBLR(0, 10, 0, 10)) {
        @Override
        public Color getColorAt(Point<Double> point) {
            return Colors.rgba(1, 0, 0, .5);
        }
    };
    Sampleable whiteSampleable = new AbstractSampleable(BoundingBox.fromTBLR(0, 10, 0, 10)) {
        @Override
        public Color getColorAt(Point<Double> doublePoint) {
            return Colors.WHITE;
        }
    };
    Point<Double> point = DoublePoint.point(5, 5);


    @Test
    public void testAddTransparency() throws Exception {
        Sampleable tranSampleable = Sampleables.addTransparency(.5, sampleable3);
        assertEquals(0.25, tranSampleable.getColorAt(point).alpha(), 0.0001);
        assertEquals(1, tranSampleable.getColorAt(point).red(), 0.0001);
        assertEquals(0, tranSampleable.getColorAt(point).blue(), 0.0001);
        assertEquals(0, tranSampleable.getColorAt(point).green(), 0.0001);
    }

    @Test
    public void testOverlay() {

        Sampleable sampleable = Sampleables.overlay(sampleable1, sampleable2, sampleable3);

        assertEquals(0.2, sampleable.getColorAt(point).red(), 0.01);
        assertEquals(0.53, sampleable.getColorAt(point).blue(), 0.01);
        assertEquals(0.26, sampleable.getColorAt(point).green(), 0.01);
        assertEquals(0.94, sampleable.getColorAt(point).alpha(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArray() {
        Sampleables.overlay();
    }

    @Test
    public void testArrayOfOneElement() {
        Sampleable oneSamp = Sampleables.overlay(sampleable3);
        assertEquals(0.5, oneSamp.getColorAt(point).alpha(), 0.0001);
        assertEquals(1, oneSamp.getColorAt(point).red(), 0.0001);
        assertEquals(0, oneSamp.getColorAt(point).blue(), 0.0001);
        assertEquals(0, oneSamp.getColorAt(point).green(), 0.0001);

    }

    @Test
    public void testOverlayOpaqueFirst() {
        Sampleable sampleable = Sampleables.overlay(whiteSampleable, sampleable1,
                sampleable2, sampleable3);
        assertEquals(1.0, sampleable.getColorAt(point).alpha(), 0.01);
        assertEquals(1.0, sampleable.getColorAt(point).red(), 0.01);
        assertEquals(1.0, sampleable.getColorAt(point).green(), 0.01);
        assertEquals(1.0, sampleable.getColorAt(point).blue(), 0.01);
    }
}