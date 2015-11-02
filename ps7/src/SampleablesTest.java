import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class SampleablesTest {

    Sampleable sampleable1 = new AbstractSampleable(BoundingBox.fromTBLR(0,10,0,10)) {
        @Override
        public Color getColorAt(Point<Double> point) {
            return Colors.rgba(0,0,1,1);
        }
    };
    Sampleable sampleable2 = new AbstractSampleable(BoundingBox.fromTBLR(0,10,0,10)) {
        @Override
        public Color getColorAt(Point<Double> point) {
            return Colors.rgba(0,1,0,.5);
        }
    };
    Sampleable sampleable3 = new AbstractSampleable(BoundingBox.fromTBLR(0,10,0,10)) {
        @Override
        public Color getColorAt(Point<Double> point) {
            return Colors.rgba(1,0,0,.5);
        }
    };




    @Test
    public void testAddTransparency() throws Exception {
        assertEquals(0.25,Sampleables.addTransparency(.5,sampleable3).getColorAt(DoublePoint.point(5,5)).alpha(),0.0001);

    }

    @Test
    public void testOverlay() throws Exception {

        Sampleable sampleable = Sampleables.overlay(sampleable1,sampleable2);

        assertEquals(sampleable.getColorAt(DoublePoint.point(5,5)).blue(), 1.0,0.1);

    }
}