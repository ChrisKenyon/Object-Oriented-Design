/**
 * Skeleton class for implementing sampleables. This class manages the bounding
 * box and leaves it to subclasses to implement the color sampling method. (It
 * might have been reasonable to call this {@code AbstractBoundable}, but I
 * expect it to do more eventually.
 */
public abstract class AbstractSampleable implements Sampleable {
  private final BoundingBox bb;

  /**
   * Creates a {@code Sampleable} with the specified bounding box coordinates.
   *
   * @param top the y-coordinate of the top of the bounding box
   * @param bottom the y-coordinate of the bottom of the bounding box
   * @param left the x-coordinate of the left side of the bounding box
   * @param right the x-coordinate of the right side of the bounding box
   */
  public AbstractSampleable(double top, double bottom,
                            double left, double right) {
    bb = BoundingBox.fromTBLR(top, bottom, left, right);
  }

  /**
   * Creates a {@code Sampleable} with the specified bounding box.
   *
   * @param bb the bounding box
   */
  public AbstractSampleable(BoundingBox bb) {
    this.bb = bb;
  }

  @Override
  public BoundingBox getBoundingBox() {
    return bb;
  }
}
