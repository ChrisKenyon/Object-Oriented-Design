import org.junit.Test;

import static org.junit.Assert.*;

public class SampleablesTest {

  Sampleable sampleable1 = new AbstractSampleable(BoundingBox.fromTBLR(0, 10, 0, 10)) {
    @Override
    public Color getColorAt(Point<Double> point) {
      return Colors.rgba(0, 0, 1, 1);
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


  @Test
  public void testAddTransparency() throws Exception {
    assertEquals(0.25,
                 Sampleables.addTransparency(.5, sampleable3).getColorAt(DoublePoint.point(5, 5))
                     .alpha(), 0.0001);
  }

  @Test
  public void testOverlay() throws Exception {
    Sampleable sampleable = Sampleables.overlay(sampleable1, sampleable2);
    assertEquals(0, sampleable.getColorAt(DoublePoint.point(5, 5)).red(), 0.1);
    assertEquals(1, sampleable.getColorAt(DoublePoint.point(5, 5)).green(), 0.1);
    assertEquals(0, sampleable.getColorAt(DoublePoint.point(5, 5)).blue(), 0.1);
    assertEquals(.75, sampleable.getColorAt(DoublePoint.point(5, 5)).alpha(), 0.1);

  }

  @Test
  public void testCircle() throws Exception {
    Sampleable
        samp =
        Sampleables.circle(DoublePoint.point(100, 100), 50, Colors.rgba(150, 150, 150, 255));
    assertEquals(Colors.rgba(150, 150, 150, 255), samp.getColorAt(DoublePoint.point(100, 100)));
    assertEquals(Colors.rgba(0, 0, 0, 0), samp.getColorAt(DoublePoint.point(145, 145)));
  }

  @Test
  public void testScale() throws Exception {
    Sampleable
        samp =
        Sampleables.circle(DoublePoint.point(10, 10), 5, Colors.rgba(150, 150, 150, 255));
    assertEquals(Colors.rgba(150, 150, 150, 255),
                 samp.scale(10).getColorAt(DoublePoint.point(100, 100)));
    assertEquals(Colors.rgba(0, 0, 0, 0), samp.scale(10).getColorAt(DoublePoint.point(145, 145)));
  }


  @Test
  public void testSampleable() throws Exception {
    Renderable
        rend =
        Renderables.circle(DoublePoint.point(100, 100), 100, Colors.rgba(150, 150, 150, 255));
    Sampleable samp = Sampleables.sampleable(rend);
    assertEquals(Colors.rgba(150, 150, 150, 255), samp.getColorAt(DoublePoint.point(100, 100)));
    assertEquals(0, samp.getColorAt(DoublePoint.point(199, 199)).intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSampleableOutOfBounds() throws Exception {
    Renderable
        rend =
        Renderables.circle(DoublePoint.point(100, 100), 100, Colors.rgba(150, 150, 150, 255));
    Sampleable samp = Sampleables.sampleable(rend);
    assertNull(samp.getColorAt(DoublePoint.point(250, 250)));
  }

  @Test
  public void testSampleableScale() throws Exception {
    Renderable
        rend =
        Renderables.circle(DoublePoint.point(10, 10), 5, Colors.rgba(150, 150, 150, 255));
    Sampleable samp = Sampleables.sampleable(5, rend);
    assertEquals(Colors.rgba(150, 150, 150, 255).intValue(),
                 samp.getColorAt(DoublePoint.point(10, 10)).intValue());
    assertEquals(0, samp.getColorAt(DoublePoint.point(15, 15)).intValue());
  }

  @Test
  public void testSampleable2() throws Exception {
    Renderable rend =
        Renderables.circle(DoublePoint.point(10, 10), 5, Colors.rgba(150, 150, 150, 255));
    Sampleable samp = Sampleables.sampleable(1, rend);
    assertEquals(Colors.rgba(150, 150, 150, 255).intValue(),
                 samp.getColorAt(DoublePoint.point(10, 10)).intValue());
    assertEquals(0, samp.getColorAt(DoublePoint.point(14, 14)).intValue());
  }
}