import org.junit.Test;

import static org.junit.Assert.*;

public class OverlayRasterTest {


  @Test
  public void testWidth() throws Exception {
    OverlayRaster overlayRaster = new OverlayRaster(5, 5);
    assertEquals(5, overlayRaster.width());

  }

  @Test
  public void testHeight() throws Exception {
    OverlayRaster overlayRaster = new OverlayRaster(5, 5);
    assertEquals(5, overlayRaster.height());

  }

  @Test
  public void testSetRGBA() throws Exception {
    Point<Integer> point1 = IntegerPoint.point(5, 1);
    Point<Integer> pointBoth = IntegerPoint.point(5, 6);
    Point<Integer> point2 = IntegerPoint.point(5, 13);
    Point<Integer> pointOut = IntegerPoint.point(30, 30);

    Renderable rend = Renderables.rectangle(0, 10, 0, 10, Colors.rgba(.5, .5, .5, 1));
    Renderable rend2 = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(0, 0, 0, 0.5));
    ArrayRaster aRast = new ArrayRaster(50, 50);
    rend.render(aRast);
    OverlayRaster rast = new OverlayRaster(aRast);
    rend2.render(rast);

    assertEquals(Colors.rgba(.5, .5, .5, 1).intValue(),
                 Colors.rgba(rast.getRGBA(point1)).intValue());
    assertEquals(Colors.rgba(.125, .125, .125, 1).intValue(),
                 Colors.rgba(rast.getRGBA(pointBoth)).intValue());
    assertEquals(0, Colors.rgba(rast.getRGBA(point2)).blue(), .01);
    assertEquals(0, Colors.rgba(rast.getRGBA(point2)).red(), .01);
    assertEquals(0, Colors.rgba(rast.getRGBA(point2)).green(), .01);
    assertEquals(0.75, Colors.rgba(rast.getRGBA(point2)).alpha(), .01);
    assertEquals(0, rast.getRGBA(pointOut));
  }

  @Test
  public void testGetRGBA() throws Exception {
    OverlayRaster overlayRaster = new OverlayRaster(5, 5);
    overlayRaster.setRGBA(IntegerPoint.point(2, 2), Colors.WHITE.intValue());
    assertEquals(overlayRaster.getRGBA(IntegerPoint.point(2, 2)), Colors.WHITE.intValue());
  }
}