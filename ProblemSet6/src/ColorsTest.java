import org.junit.Test;

import static org.junit.Assert.*;

public class ColorsTest {

    @Test
    public void testRgb() throws Exception {
        Color color = Colors.rgb(.5, .5, .5);
        assertEquals(1.0, color.alpha(), 0);
        assertEquals(.5, color.blue(), 0);
        assertEquals(.5, color.red(), 0);
        assertEquals(.5, color.green(), 0);
        assertEquals(255, color.intAlpha());
        assertEquals(128, color.intBlue());
        assertEquals(128, color.intRed());
        assertEquals(128, color.intGreen());

        assertTrue(color.isOpaque());
    }

    @Test
    public void testRgba() throws Exception {
        Color color = Colors.rgba(.5, .5, .5, 0.0);
        assertEquals(0.0, color.alpha(), 0);
        assertEquals(.5, color.blue(), 0);
        assertEquals(.5, color.red(), 0);
        assertEquals(.5, color.green(), 0);
        assertEquals(0, color.intAlpha());
        assertEquals(128, color.intBlue());
        assertEquals(128, color.intRed());
        assertEquals(128, color.intGreen());

        assertTrue(color.isTransparent());
    }

    @Test
    public void testRgbInt() throws Exception {
        Color color = Colors.rgb(128, 128, 128);
        assertEquals(1.0, color.alpha(), 0);
        assertEquals(.5, color.blue(), 0.2);
        assertEquals(.5, color.red(), 0.2);
        assertEquals(.5, color.green(), 0.2);
        assertEquals(255, color.intAlpha());
        assertEquals(128, color.intBlue());
        assertEquals(128, color.intRed());
        assertEquals(128, color.intGreen());

        assertTrue(color.isOpaque());
    }

    @Test
    public void testRgbaInt() throws Exception {
        Color color = Colors.rgba(.5, .5, .5, 0.0);
        assertEquals(0.0, color.alpha(), 0);
        assertEquals(.5, color.blue(), 0);
        assertEquals(.5, color.red(), 0);
        assertEquals(.5, color.green(), 0);
        assertEquals(0, color.intAlpha());
        assertEquals(128, color.intBlue());
        assertEquals(128, color.intRed());
        assertEquals(128, color.intGreen());

        assertTrue(color.isTransparent());
    }

    @Test
    public void testRgb2() throws Exception {
        Color color = Colors.rgb(Colors.rgb(.46, .32, .79).intValue());
        assertEquals(1.0, color.alpha(), 0);
        assertEquals(.79, color.blue(), 0.02);
        assertEquals(.46, color.red(), 0.02);
        assertEquals(.32, color.green(), 0.02);
        assertEquals(255, color.intAlpha());
        assertEquals(201, color.intBlue());
        assertEquals(117, color.intRed());
        assertEquals(82, color.intGreen());
    }

    @Test
    public void testRgba2() throws Exception {
        Color color = Colors.rgba(Colors.rgba(.46, .32, .79, 0.6).intValue());
        assertEquals(.6, color.alpha(), 0.02);
        assertEquals(.79, color.blue(), 0.02);
        assertEquals(.46, color.red(), 0.02);
        assertEquals(.32, color.green(), 0.02);
        assertEquals(153, color.intAlpha());
        assertEquals(201, color.intBlue());
        assertEquals(117, color.intRed());
        assertEquals(82, color.intGreen());
    }

    @Test
    public void testOverlay() throws Exception {
        Color background = Colors.rgba(150, 150, 150, 150);
        Color color = Colors.rgba(100, 100, 100, 200);
        Color overlayColor = color.overlay(background);

        //(200/255)*100+(1-200/255)*150 = 110.7
        assertEquals(111, overlayColor.intRed());
        assertEquals(111, overlayColor.intGreen());
        assertEquals(111, overlayColor.intBlue());
        //(200/255)*200+(1-200/255)*150 = 189.2
        assertEquals(189, overlayColor.intAlpha());

    }

    @Test
    public void testInterpolate() throws Exception {
        Color color = Colors.rgba(100, 100, 100, 100);
        assertEquals(1.0, Colors.WHITE.interpolate(1, color).red(), 0);
        assertEquals(1.0, Colors.WHITE.interpolate(1, color).green(), 0);
        assertEquals(1.0, Colors.WHITE.interpolate(1, color).blue(), 0);
        assertEquals(1.0, Colors.WHITE.interpolate(1, color).alpha(), 0);

        //(255-100)/2=177.5
        assertEquals(178, color.interpolate(0.5, Colors.WHITE).intRed());
        assertEquals(178, color.interpolate(0.5, Colors.WHITE).intGreen());
        assertEquals(178, color.interpolate(0.5, Colors.WHITE).intBlue());
        assertEquals(178, color.interpolate(0.5, Colors.WHITE).intAlpha());
    }

    @Test
    public void testBounds() {
        Color color1 = Colors.rgba(260, 270, 255, 300);
        Color white = Colors.WHITE;
        Color black = Colors.BLACK;
        //test that this makes white (s.t. all values are 255)
        assertEquals(255, color1.intAlpha());
        assertEquals(white.intBlue(), color1.intBlue());
        assertEquals(white.intGreen(), color1.intGreen());
        assertEquals(white.intRed(), color1.intRed());

        Color color2 = Colors.rgba(-6.5, -8.2, -2.1, 0);
        assertEquals(0.0, color2.alpha(), 0);
        assertEquals(black.blue(), color2.blue(), 0);
        assertEquals(black.green(), color2.green(), 0);
        assertEquals(black.red(), color2.red(), 0);

    }
}