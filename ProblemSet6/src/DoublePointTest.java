import org.junit.Test;

import static org.junit.Assert.*;

public class DoublePointTest {

    public void testPoint() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(1.0, 2.5);
        assertEquals(1.0, doublePoint.x(), 0);
        assertEquals(2.5, doublePoint.y(), 0);
        assertEquals(1, doublePoint.intX(), 0);
        assertEquals(3, doublePoint.intY(), 0);
        assertEquals(Double.valueOf(1), doublePoint.boxedX(), 0);
        assertEquals(Double.valueOf(3), doublePoint.boxedY(), 0);

        Point<Double> doublePoint1 = DoublePoint.point(1, 3);
        assertEquals(1.0, doublePoint1.x(), 0);
        assertEquals(3.0, doublePoint1.y(), 0);
        assertEquals(1.0, doublePoint1.intX(), 0);
        assertEquals(3.0, doublePoint1.intY(), 0);
        assertEquals(Double.valueOf(1.0), doublePoint1.boxedX(), 0);
        assertEquals(Double.valueOf(3.0), doublePoint1.boxedY(), 0);
    }

    @Test
    public void testScaleBy() throws Exception {
        Point<Double> scaledPoint = DoublePoint.point(10.0, 10.0).scaleBy(2.0);
        Point<Double> doublePoint = DoublePoint.point(20.0, 20.0);
        assertTrue(doublePoint.equals(scaledPoint.x(), scaledPoint.y()));
    }

    @Test
    public void testAdd() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(13.0, 8.0);
        Point<Double> doublePoint1 = DoublePoint.point(7.0, 12.0);
        Point<Double> addedPoint = doublePoint.add(doublePoint1);

        assertTrue(addedPoint.equals(DoublePoint.point(20.0, 20.0).x(),
                DoublePoint.point(20.0, 20.0).x()));
    }

    @Test
    public void testAddToItself() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(13.0, 8.0);
        Point<Double> addedPoint = doublePoint.add(doublePoint);

        assertTrue(addedPoint.equals(DoublePoint.point(26.0, 16.0).x(),
                DoublePoint.point(26.0, 16.0).y()));
    }

    @Test
    public void testSubtract() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(3.0, 2.0);
        Point<Double> integerPoint1 = DoublePoint.point(5.0, 1.0);
        Point<Double> subtractedPoint = doublePoint.subtract(integerPoint1);
        assertTrue(subtractedPoint.equals(IntegerPoint.point(-2.0, 1.0).x(),
                IntegerPoint.point(-2.0, 1.0).y()));
    }

    @Test
    public void testToDoublePoint() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(2.0, 2.0);
        Point<Double> clonePoint = doublePoint.toDoublePoint();
        assertEquals(doublePoint, clonePoint);
    }

    @Test
    public void testToIntegerPoint() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(2, 2);
        Point<Integer> integerPoint = doublePoint.toIntegerPoint();
        assertTrue(integerPoint.equals(IntegerPoint.point(2, 2).intX(),
                IntegerPoint.point(2, 2).intY()));
    }

    @Test
    public void testInterpolate() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(0, 0);
        Point<Double> doublePoint1 = DoublePoint.point(5.5, 5.5);
        boolean test = (doublePoint.interpolate(0.2, doublePoint1).equals(1.1, 1.1));
        assertTrue(test);
    }

    @Test
    public void testHashCodeForDoublePoint() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(1.0, 1.0);
        assertEquals(doublePoint.hashCode(), doublePoint.hashCodeForDoublePoint());
    }

    @Test
    public void testHashCodeForIntegerPoint() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(1.0, 1.0);
        assertEquals(31 * doublePoint.intX() + doublePoint.intY(),
                doublePoint.hashCodeForIntegerPoint(), 0);
    }

    /**
     * If two BoundingBox objects contain each other, then they are equal
     */
    @Test
    public void testGetBoundingBox() throws Exception {
        Point<Double> doublePoint = DoublePoint.point(1.5, 2.0);
        BoundingBox testBox = BoundingBox.fromTBLR(2.0, 2.0, 1.5, 1.5);
        assertTrue(testBox.contains(doublePoint.getBoundingBox()) &&
                doublePoint.getBoundingBox().contains(testBox));
    }
}