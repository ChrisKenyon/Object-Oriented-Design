import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerPointTest {

    public void testPoint() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(1.0, 2.5);
        assertEquals(1, integerPoint.x(), 0);
        assertEquals(3, integerPoint.y(), 0);
        assertEquals(1, integerPoint.intX(), 0);
        assertEquals(3, integerPoint.intY(), 0);
        assertEquals(Integer.valueOf(1), integerPoint.boxedX(), 0);
        assertEquals(Integer.valueOf(3), integerPoint.boxedY(), 0);

        Point<Integer> integerPoint1 = IntegerPoint.point(1, 3);
        assertEquals(1, integerPoint1.x(), 0);
        assertEquals(3, integerPoint1.y(), 0);
        assertEquals(1, integerPoint1.intX(), 0);
        assertEquals(3, integerPoint1.intY(), 0);
        assertEquals(Integer.valueOf(1), integerPoint1.boxedX(), 0);
        assertEquals(Integer.valueOf(3), integerPoint1.boxedY(), 0);
    }


    @Test
    public void testInterpolate() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(0, 0);
        Point<Integer> integerPoint1 = IntegerPoint.point(5, 5);
        boolean test = (integerPoint.interpolate(0.6, integerPoint1).equals(3, 3));
        assertTrue(test);
    }

    /**
     * If two BoundingBox objects contain each other,
     * then they are equal
     *
     * @throws Exception
     */
    @Test
    public void testGetBoundingBox() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(15, 20);
        BoundingBox testBox = BoundingBox.fromTBLR(20, 20, 15, 15);
        assertTrue(testBox.contains(integerPoint.getBoundingBox()) &&
                integerPoint.getBoundingBox().contains(testBox));
    }

    @Test
    public void testScaleBy() throws Exception {
        Point<Integer> scaledPoint = IntegerPoint.point(10, 10).scaleBy(2.0);
        Point<Integer> integerPoint = IntegerPoint.point(20, 20);
        assertTrue(integerPoint.equals(scaledPoint.intX(), scaledPoint.intY()));
    }

    @Test
    public void testAdd() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(13, 8);
        Point<Integer> integerPoint2 = IntegerPoint.point(7, 12);
        Point<Integer> addedPoint = integerPoint.add(integerPoint2);

        assertTrue(addedPoint.equals(IntegerPoint.point(20, 20).intX(),
                IntegerPoint.point(20, 20).intY()));
    }

    @Test
    public void testAddToItself() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(13, 8);
        Point<Integer> addedPoint = integerPoint.add(integerPoint);

        assertTrue(addedPoint.equals(IntegerPoint.point(26, 16).intX(),
                IntegerPoint.point(26, 16).intY()));
    }


    @Test
    public void testSubtract() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(3, 2);
        Point<Integer> integerPoint1 = IntegerPoint.point(5, 1);
        Point<Integer> subtractedPoint = integerPoint.subtract(integerPoint1);
        assertTrue(subtractedPoint.equals(IntegerPoint.point(-2, 1).intX(),
                IntegerPoint.point(-2, 1).intY()));
    }

    @Test
    public void testToDoublePoint() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(2, 2);
        Point<Double> doublePoint = integerPoint.toDoublePoint();
        assertTrue(doublePoint.equals(DoublePoint.point(2.0, 2.0).x(),
                DoublePoint.point(2.0, 2.0).y()));
    }

    @Test
    public void testToIntegerPoint() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(2, 2);
        Point<Integer> clonePoint = integerPoint.toIntegerPoint();
        assertEquals(integerPoint, clonePoint);
    }

    @Test
    public void testHashCodeForIntegerPoint() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(1, 1);
        assertEquals(integerPoint.hashCode(), integerPoint.hashCodeForIntegerPoint());
    }

    @Test
    public void testHashCodeForDoublePoint() throws Exception {
        Point<Integer> integerPoint = IntegerPoint.point(1, 1);
        assertEquals(31 * Double.hashCode(integerPoint.x()) + Double.hashCode(integerPoint.y()),
                integerPoint.hashCodeForDoublePoint(), 0);
    }
}