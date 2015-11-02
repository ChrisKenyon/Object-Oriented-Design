import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoundingBoxTest {
    static final BoundingBox box1 = fromTBLR(1, 5, 1, 9);
    static final BoundingBox box2 = fromTBLR(3, 8, 5, 13);
    static final BoundingBox box3 = fromTBLR(9, 17, 10, 12);

    @Test(expected = IllegalArgumentException.class)
    public void testFromLRTB_neg_dx() {
        fromTBLR(1, 5, 9, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLRTB_neg_dy() {
        fromTBLR(5, 1, 1, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLRTB_neg() {
        fromTBLR(5, 1, 9, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLRTB_inf() {
        fromTBLR(1, Double.POSITIVE_INFINITY, 1, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLRTB_nan() {
        fromTBLR(1, Double.NaN, 1, 9);
    }

    @Test
    public void testLeft() {
        assertNearby(1, box1.left());
        assertNearby(5, box2.left());
    }

    @Test
    public void testRight() {
        assertNearby(9, box1.right());
        assertNearby(13, box2.right());
    }

    @Test
    public void testTop() {
        assertNearby(1, box1.top());
        assertNearby(3, box2.top());
    }

    @Test
    public void testBottom() {
        assertNearby(5, box1.bottom());
        assertNearby(8, box2.bottom());
    }

    @Test
    public void testTopLeft() {
        assertNearby(p(1, 1), box1.topLeft());
        assertNearby(p(5, 3), box2.topLeft());
    }

    @Test
    public void testTopRight() {
        assertNearby(p(9, 1), box1.topRight());
        assertNearby(p(13, 3), box2.topRight());
    }

    @Test
    public void testBottomLeft() {
        assertNearby(p(1, 5), box1.bottomLeft());
        assertNearby(p(5, 8), box2.bottomLeft());
    }

    @Test
    public void testBottomRight() {
        assertNearby(p(9, 5), box1.bottomRight());
        assertNearby(p(13, 8), box2.bottomRight());
    }

    @Test
    public void testWidth() {
        assertNearby(8, box1.width());
        assertNearby(2, box3.width());
    }

    @Test
    public void testHeight() {
        assertNearby(4, box1.height());
        assertNearby(8, box3.height());
    }

    @Test
    public void testContains_Point() {
        assertTrue(box1.contains(p(2, 4)));
        assertTrue(box2.contains(p(7, 4)));
        assertTrue(box3.contains(p(11, 13)));
        assertFalse(box1.contains(p(10, 4)));
        assertFalse(box2.contains(p(3, 4)));
        assertFalse(box3.contains(p(11, 6)));
    }

    @Test
    public void testContains_Box() {
        assertTrue(box1.contains(fromTBLR(3, 4, 1, 7)));

        assertFalse(box1.contains(fromTBLR(3, 6, 1, 7)));
        assertFalse(box1.contains(fromTBLR(0, 4, 1, 7)));
        assertFalse(box1.contains(fromTBLR(3, 4, 1, 10)));
        assertFalse(box1.contains(fromTBLR(19, 20, 15, 16)));
    }

    @Test
    public void testJoin() {
        BoundingBox box12 = box1.join(box2);
        assertNearby(p(1, 1), box12.topLeft());
        assertNearby(p(13, 8), box12.bottomRight());

        BoundingBox box13 = box1.join(box3);
        assertNearby(p(1, 1), box13.topLeft());
        assertNearby(p(12, 17), box13.bottomRight());
    }

    @Test
    public void testMeet() {
        BoundingBox box12 = box1.intersect(box2);
        assertNearby(p(5, 3), box12.topLeft());
        assertNearby(p(9, 5), box12.bottomRight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntersect_error1() {
        BoundingBox box13 = box1.intersect(box3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntersect_error2() {
        BoundingBox box23 = box2.intersect(box3);
    }

    @Test
    public void testIsDisjoint() {
        assertFalse(box1.isDisjoint(box2));
        assertTrue(box1.isDisjoint(box3));
        assertTrue(box2.isDisjoint(box3));
    }

    @Test
    public void testContaining() {
        double left = -5;
        BoundingBox box = BoundingBox.containing(fromTBLR(0, 10, 0, 10),
                fromTBLR(5, 15, 3, 4),
                fromTBLR(4, 6, left, 12));
        assertNearby(p(-5, 0), box.topLeft());
        assertNearby(p(12, 15), box.bottomRight());
    }

    @Test
    public void testContainedBy() {
        double left = -5;
        BoundingBox box = BoundingBox.containedBy(fromTBLR(0, 10, 0, 10),
                fromTBLR(5, 15, 3, 9),
                fromTBLR(4, 6, left, 12));
        assertNearby(p(3, 5), box.topLeft());
        assertNearby(p(9, 6), box.bottomRight());
    }

    /**
     * Tolerance for testing.
     */

    static final double EPSILON = 1E-10;

    static void assertNearby(String msg, Point<?> p1, Point<?> p2) {
        assertNearby(msg, 0.0, Math.hypot(p1.x() - p2.x(), p1.y() - p2.y()));
    }

    static void assertNearby(Point<?> p1, Point<?> p2) {
        assertNearby("points are not nearby", p1, p2);
    }

    static void assertNearby(double x1, double x2) {
        assertEquals(x1, x2, EPSILON);
    }

    static void assertNearby(String msg, double x1, double x2) {
        assertEquals(msg, x1, x2, EPSILON);
    }

    /*
     * The right way to do this would be import static, but that doesn't work
     * when the name you want to import static is in the default package. And
     * we're stuck in the default package, lest auto-grading again become a
     * horrible mess.
     */
    public static BoundingBox fromTBLR(double t, double b, double l, double r) {
        return BoundingBox.fromTBLR(t, b, l, r);
    }

    public static Point<Double> p(double x, double y) {
        return DoublePoint.point(x, y);
    }
}