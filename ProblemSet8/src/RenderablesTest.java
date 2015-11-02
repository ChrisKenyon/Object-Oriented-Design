import org.junit.Test;

import static org.junit.Assert.*;

public class RenderablesTest {

  @Test
  public void testCircle() throws Exception {
    Renderable rend = Renderables.circle(DoublePoint.point(10, 10), 10, Colors.WHITE);
    OverlayRaster rast = new OverlayRaster(50,50);
    rend.render(rast);
    assertEquals(Colors.WHITE.intValue(),rast.getRGBA(IntegerPoint.point(10,10)));
    assertEquals(0,rast.getRGBA(IntegerPoint.point(19,19)));
  }

  @Test
  public void testRenderable() throws Exception {
    Sampleable samp = Sampleables.circle(DoublePoint.point(10, 10), 10, Colors.WHITE);
    Renderable rend = Renderables.renderable(samp);
    Renderable expected = Renderables.circle(DoublePoint.point(10, 10), 10, Colors.WHITE);

    OverlayRaster expectedRast = new OverlayRaster(50, 50);
    OverlayRaster rast = new OverlayRaster(50, 50);

    rend.render(rast);
    expected.render(expectedRast);

    Point<Integer> point = IntegerPoint.point(15, 15);
    Point<Integer> point2 = IntegerPoint.point(20, 20);

    assertEquals(expectedRast.getRGBA(point),rast.getRGBA(point));
    assertEquals(0,expectedRast.getRGBA(point2));
    assertEquals(expectedRast.getRGBA(point2),rast.getRGBA(point2));
  }


  @Test
  public void testOverlay() throws Exception {
    Renderable rend = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(.5, .5, .5, 1));
    Renderable rend2 = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(0, 0, 0, 0.5));
    Renderable rend3 = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(.75, .75, .75, 0.5));

    Renderable olay = Renderables.overlay(rend3, rend2, rend);
    OverlayRaster rast = new OverlayRaster(50, 50);
    olay.render(rast);
    Point<Integer> point = IntegerPoint.point(5, 10);

    assertEquals(0.6, Colors.rgba(rast.getRGBA(point)).blue(), .01);
    assertEquals(0.6, Colors.rgba(rast.getRGBA(point)).red(), .01);
    assertEquals(0.6, Colors.rgba(rast.getRGBA(point)).green(), .01);
    assertEquals(1, Colors.rgba(rast.getRGBA(point)).alpha(), .01);

  }

  @Test
  public void testOverlayOpaque() throws Exception {
    Renderable rend = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(.5, .5, .5, 1));
    Renderable rend2 = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(0, 0, 0, 0.5));
    Renderable rend3 = Renderables.rectangle(5, 15, 0, 10, Colors.rgba(.75, .75, .75, 0.5));

    Renderable olay = Renderables.overlay(rend, rend2, rend3);
    OverlayRaster rast = new OverlayRaster(50, 50);
    olay.render(rast);
    Point<Integer> point = IntegerPoint.point(5, 10);

    assertEquals(0.5, Colors.rgba(rast.getRGBA(point)).blue(), .01);
    assertEquals(0.5, Colors.rgba(rast.getRGBA(point)).red(), .01);
    assertEquals(0.5, Colors.rgba(rast.getRGBA(point)).green(), .01);
    assertEquals(1, Colors.rgba(rast.getRGBA(point)).alpha(), .01);

  }

  @Test
  public void testRectangle() throws Exception {
    Point<Integer> point1 = IntegerPoint.point(150, 125);
    Point<Integer> point2 = IntegerPoint.point(100, 100);
    Point<Integer> point3 = IntegerPoint.point(150, 225);
    Point<Integer> point4 = IntegerPoint.point(150, 250);
    Point<Integer> pointBoth = IntegerPoint.point(150, 175);
    Point<Integer> pointOut1 = IntegerPoint.point(251, 201);
    Point<Integer> pointOut2 = IntegerPoint.point(99, 99);

    Renderable rend = Renderables.rectangle(100, 200, 100, 200, Colors.rgba(.5, .5, .5, 1));
    Renderable rend2 = Renderables.rectangle(150, 250, 100, 200, Colors.rgba(0, 0, 0, 0.5));
    ArrayRaster aRast = new ArrayRaster(500, 500);
    rend.render(aRast);
    OverlayRaster rast = new OverlayRaster(aRast);
    rend2.render(rast);

    assertEquals(Colors.rgba(.5, .5, .5, 1).intValue(),
                 Colors.rgba(rast.getRGBA(point1)).intValue());
    assertEquals(Colors.rgba(.5, .5, .5, 1).intValue(),
                 Colors.rgba(rast.getRGBA(point2)).intValue());

    assertEquals(0, Colors.rgba(rast.getRGBA(point3)).blue(), .01);
    assertEquals(0, Colors.rgba(rast.getRGBA(point3)).red(), .01);
    assertEquals(0, Colors.rgba(rast.getRGBA(point3)).green(), .01);
    assertEquals(0.75, Colors.rgba(rast.getRGBA(point3)).alpha(), .01);

    assertEquals(0, Colors.rgba(rast.getRGBA(point4)).blue(), .01);
    assertEquals(0, Colors.rgba(rast.getRGBA(point4)).red(), .01);
    assertEquals(0, Colors.rgba(rast.getRGBA(point4)).green(), .01);
    assertEquals(0.75, Colors.rgba(rast.getRGBA(point4)).alpha(), .01);

    assertEquals(Colors.rgba(.125, .125, .125, 1).intValue(),
                 Colors.rgba(rast.getRGBA(pointBoth)).intValue());
    assertEquals(0, rast.getRGBA(pointOut1));
    assertEquals(0, rast.getRGBA(pointOut2));

  }
}
