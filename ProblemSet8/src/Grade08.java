import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Grade08 {

  Point<Integer> pi = IntegerPoint.point(200, 150);
  Point<Double> pd = pi.toDoublePoint();
  Raster raster = new OverlayRaster(400, 300);

  @Test(expected = NullPointerException.class)
  public void testOverlayRaster_null() {
    new OverlayRaster(null);
  }

  @Test
  public void testOverlayRaster() {
    Raster over = new OverlayRaster(raster);
    over.setRGBA(pi, 0xFF00FF00);
    assertEquals(0xFF00FF00, raster.getRGBA(pi));
  }

  @Test
  public void testOverlayRaster_50() {
    raster.setRGBA(pi, 0xFF00FF00);
    raster.setRGBA(pi, 0x80FFFF00);
    assertEquals(0xFF80FF00, raster.getRGBA(pi));
  }

  @Test
  public void testRectangle_and_Overlay() {
    Renderable rect = Renderables.rectangle(100, 200, 100, 300,
                                            Colors.rgb(0x0000FF));
    Renderable scene = Renderables.overlay(rect, rect);
    rect.render(raster);
    assertEquals(0xFF0000FF, raster.getRGBA(pi));
  }

  @Test
  public void testSampleable_Renderable() {
    Renderable circleR = Renderables.circle(pd, 100, Colors.rgb(0x0000FF));
    Sampleable circleS = Sampleables.sampleable(circleR);
    assertEquals(Colors.rgb(0x0000FF), circleS.getColorAt(pd));
  }

  private static final double EPSILON = 1E-10;

  private void assertNearby(double expected, double actual) {
    assertEquals(expected, actual, EPSILON);
  }

  private void assertNearby(Color expected, Color actual) {
    assertNearby(expected.red(), actual.red());
    assertNearby(expected.green(), actual.green());
    assertNearby(expected.blue(), actual.blue());
    assertNearby(expected.alpha(), actual.alpha());
  }
}
