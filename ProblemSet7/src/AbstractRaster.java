/**
 * Skeleton class for implementing {@link Raster}. This class manages the
 * dimensions of the raster and provides bounds checking; subclasses are
 * responsible for representing and manipulating the actual pixels. To use,
 * extend this class, override method {@link #setRGBA(Point, int)
 * setRGBA(Point&lt;Integer&gt;, int)}, and if you want to support reading from
 * the raster, override method {@link #getRGBA(Point) getRGBA(Point&lt;
 * Integer&gt;} as well. (The default implementation of {@code getRGBA}
 * throws an {@link UnsupportedOperationException}.)
 */
public abstract class AbstractRaster implements Raster {
  private final int width;
  private final int height;

  /**
   * Creates an abstract raster with the given dimensions.
   *
   * @param width the width
   * @param height the height
   * @throws IllegalArgumentException if either dimension is negative
   */
  public AbstractRaster(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("raster dimensions can't be negative");
    }

    this.width = width;
    this.height = height;
  }

  @Override
  public int width() {
    return width;
  }

  @Override
  public int height() {
    return height;
  }

  @Override
  public abstract void setRGBA(Point<Integer> point, int colorValue);

  @Override
  public int getRGBA(Point<Integer> point) {
    throw new UnsupportedOperationException(this.getClass() + ".getRGBA");
  }

  /**
   * Performs a bounds check on the given point and throws an exception if it's
   * out of bounds.
   * <p>
   * <strong>Inheritance note:</strong> This method uses the {@link #width
   * ()} and {@link #height()} methods to perform the bounds check, so
   * overriding these methods will affect what coordinates are allowed.
   *
   * @param point the point to check
   * @throws IndexOutOfBoundsException if out of bounds
   */
  protected void boundsCheck(Point<Integer> point) {
    if (point.intX() < 0 || point.intX() >= width()
         || point.intY() < 0 || point.intY() >= height()) {
      throw new IndexOutOfBoundsException(point.intX() + "/" + width() + ", "
                                           + point.intY() + "/" + height());
    }
  }
}
