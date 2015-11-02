/**
 * Renders several circles using {@link RasterApplication} and {@link
 * Sampleables}.
 */
public final class CirclesDemo extends RasterApplication {
  public static void main(String[] args) {
    new CirclesDemo().launch("Circles", 1000, 800);
  }

  @Override
  protected void render(Raster raster) {
    Sampleable scene = circles();

    new SimpleSampler().sample(scene, raster);
  }

  /**
   * Returns a sampleable of circles.
   * @return a sampleable
   */
  private Sampleable circles() {
    //return Sampleables.scale(100,
    //         Sampleables.circle(DoublePoint.point(6, 3), 2,
    //                            Colors.rgba(0x99, 0x99, 0x00, 0xCC)));
    // The code below should work after you've written the addTransparency
    // and overlay methods.

    return
     Sampleables.addTransparency(0.5,
       Sampleables.scale(100,
         Sampleables.overlay(
           Sampleables.circle(DoublePoint.point(6, 3), 2,
                              Colors.rgba(0x99, 0x99, 0x00, 0xCC)),
           Sampleables.circle(DoublePoint.point(4, 3), 2,
                              Colors.rgba(0x00, 0x99, 0x99, 0x80)),
           Sampleables.circle(DoublePoint.point(5, 5), 2,
                              Colors.rgb(0x99, 0x00, 0x99))
         )
       )
    );

  }
}
