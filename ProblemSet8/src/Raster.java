/**
 * A surface that supports rendering pixels. This is perhaps the simplest possible rendering
 * interface (plus a default method).
 */
public interface Raster {

  /**
   * Gets the width of the surface, in pixels.
   *
   * @return the width
   */
  int width();

  /**
   * Gets the height of the surface, in pixels.
   *
   * @return the height
   */
  int height();

  /**
   * Sets the pixel at column {@code point.intX()} and row {@code point.intY()} to color {@code
   * colorValue}.
   *
   * <p> <strong>Preconditions:</strong> <ul> <li>{@code 0} ≤ {@code point.intX()} &lt; {@code
   * width()} <li>{@code 0} ≤ {@code point.intY()} &lt; {@code height()} </ul>
   *
   * @param point      the location of the pixel (zero-based, top to bottom)
   * @param colorValue the 32-bit RGBA color to set the pixel to
   * @throws IndexOutOfBoundsException if the pixel is out of bounds
   */
  void setRGBA(Point<Integer> point, int colorValue);

  /**
   * Sets the pixel at column {@code point.intX()} and row {@code point.intY()} to color {@code
   * color}.
   *
   * <p>The default implementation uses {@link #setRGBA(Point, int) setRGBA}.
   *
   * <p> <strong>Preconditions:</strong> <ul> <li>{@code 0} ≤ {@code point.intX()} &lt; {@code
   * width()} <li>{@code 0} ≤ {@code point.intY()} &lt; {@code height()} </ul>
   *
   * @param point the location of the pixel (zero-based, top to bottom)
   * @param color the color to set the pixel to
   * @throws IndexOutOfBoundsException if the pixel is out of bounds
   */
  default void setColor(Point<Integer> point, Color color) {
    setRGBA(point, color.intValue());
  }

  /**
   * Gets the 32-bit RGBA color of the specified pixel. This is not strictly required for rendering,
   * but it can make it easier to implement a raster in terms of another raster.
   *
   * <p> <strong>Preconditions:</strong> <ul> <li>{@code 0} ≤ {@code point.intX()} &lt; {@code
   * width()} <li>{@code 0} ≤ {@code point.intY()} &lt; {@code height()} </ul>
   *
   * @param point the location of the pixel
   * @return the 32-bit RGBA color value of the pixel
   * @throws IndexOutOfBoundsException if the pixel is out of bounds
   */
  int getRGBA(Point<Integer> point);

  /**
   * Gets the color of the specified pixel. This is not strictly required for rendering, but it can
   * make it easier to implement a raster in terms of another raster.
   *
   * <p>The default implementation uses {@link #getRGBA(Point) getRGBA}.
   *
   * <p> <strong>Preconditions:</strong> <ul> <li>{@code 0} ≤ {@code point.intX()} &lt; {@code
   * width()} <li>{@code 0} ≤ {@code point.intY()} &lt; {@code height()} </ul>
   *
   * @param point the location of the pixel
   * @return the color value of the pixel
   * @throws IndexOutOfBoundsException if the pixel is out of bounds
   */
  default Color getColor(Point<Integer> point) {
    return Colors.rgba(getRGBA(point));
  }

  /**
   * Fills the entire surface with the given color. Instances might override this with a more
   * efficient method.
   *
   * @param colorValue a 32-bit RGBA color value
   */
  default void clear(int colorValue) {
    for (int col = 0; col < width(); ++col) {
      for (int row = 0; row < height(); ++row) {
        setRGBA(IntegerPoint.point(col, row), colorValue);
      }
    }
  }
}
