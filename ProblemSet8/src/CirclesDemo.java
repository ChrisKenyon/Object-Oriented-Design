

/**
 * Renders several circles using {@link RasterApplication} and {@link Sampleables}.
 */

public final class CirclesDemo extends RasterApplication {

  public static void main(String[] args) {
    new CirclesDemo().launch("Circles", 1000, 1000);
  }


  @Override
  protected void render(Raster raster) {
    //raster = new OverlayRaster(raster);
    //rendCircles().render(raster);
    for (int x = 0; x < 1500; x = x + 10) {
      Renderables.circle(DoublePoint.point(600, 300), 50, Colors.BLACK)
          .rotate(DoublePoint.point(600, 400), x).render(raster);
    }

    Renderables.rectangle(100, 300, 100, 300, Colors.BLACK)
        .rotate(IntegerPoint.point(200, 200), 100).render(raster);
    //Sampleable samp = Sampleables.sampleable(1.0,rendCircles());
    //Sampleable samp = sampCircles();
    //new SimpleSampler().sample(samp, raster);

  }

  /**
   * Returns a sampleable of circles.
   *
   * @return a sampleable
   */
  private Renderable rendCircles() {
    // A visual example:

    return Renderables.overlay(
        Renderables.renderable(
            Sampleables.circle(DoublePoint.point(600, 300),
                               200, Colors.rgb(0x99, 0x99, 0x00))),
        Renderables.circle(DoublePoint.point(400, 300),
                           200, Colors.rgba(0x00, 0x99, 0x99, 0x80))
            .scale(DoublePoint.point(400, 300), .75),
        Renderables.circle(DoublePoint.point(500, 500),
                           200, Colors.rgb(0x00, 0x00, 0x00)).rotate(1),
        Renderables.rectangle(100, 400, 450, 850, Colors.rgb(0x00, 0xFF, 0x00)).rotate(100)
            .translate(0, 500)

    );
  }

  private Sampleable sampCircles() {
    return Sampleables.addTransparency(0.5,
                                       Sampleables.scale(100,
                                                         Sampleables.overlay(
                                                             Sampleables
                                                                 .circle(DoublePoint.point(6, 3), 2,
                                                                         Colors
                                                                             .rgba(0x99, 0x99, 0x00,
                                                                                   0xCC)),
                                                             Sampleables
                                                                 .circle(DoublePoint.point(4, 3), 2,
                                                                         Colors
                                                                             .rgba(0x00, 0x99, 0x99,
                                                                                   0x80)),
                                                             Sampleables
                                                                 .circle(DoublePoint.point(5, 5), 2,
                                                                         Colors.rgb(0x99, 0x00,
                                                                                    0x99))))
    );
  }
}
