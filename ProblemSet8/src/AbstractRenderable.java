/**
 * Skeleton class to aid in implementing the {@link Renderable} interface.
 */
public abstract class AbstractRenderable implements Renderable {

  private final BoundingBox boundingBox;

  /**
   * Constructs a {@code Renderable} with the given bounding box.
   *
   * @param boundingBox the bounding box
   */
  protected AbstractRenderable(BoundingBox boundingBox) {
    this.boundingBox = boundingBox;
  }

  /**
   * Constructs a {@code Renderable} with the given bounding box.
   *
   * @param top    the y coordinate of the top of the bounding box
   * @param bottom the y coordinate of the bottom of the bounding box
   * @param left   the x coordinate of the left of the bounding box
   * @param right  the x coordinate of the right of the bounding box
   */
  protected AbstractRenderable(double top, double bottom,
                               double left, double right) {
    this(BoundingBox.fromTBLR(top, bottom, left, right));
  }

  @Override
  public final BoundingBox getBoundingBox() {
    return boundingBox;
  }

  @Override
  public final Renderable transform(AffineTransformation trans1) {
    Renderable base = this;

    return new AbstractRenderable(trans1.apply(boundingBox)) {
      @Override
      public void render(AffineTransformation trans2, Raster raster) {
        base.render(trans2.compose(trans1), raster);
      }
    };
  }
}
