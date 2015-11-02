/**
 * Operations for working with RGBA colors.
 * <p>
 * Two colors must be {@link Object#equals(Object) equal} if they have the same
 * red, green, blue, and alpha components, even if they are represented by
 * different classes. Furthermore, we must override {@link Object#hashCode()} to
 * make sure that all implementations of {@code Color} agree on their hash
 * codes. The static method {@link #hashCode(Color)} provides an implementation
 * of hashing of colors that all implementations of this interface should
 * delegate to.
 */
public interface Color extends Interpolable<Color> {
  /**
   * Gets the red component of this color.
   *
   * @return 0.0 ≤ <em>red()</em> ≤ 1.0
   */
  double red();

  /**
   * Gets the green component of this color.
   *
   * @return 0.0 ≤ <em>green()</em> ≤ 1.0
   */
  double green();

  /**
   * Gets the blue component of this color.
   *
   * @return 0.0 ≤ <em>blue()</em> ≤ 1.0
   */
  double blue();

  /**
   * Gets the opacity component of this color.
   *
   * @return 0.0 ≤ <em>alpha()</em> ≤ 1.0
   */
  double alpha();

  /**
   * Gets the red component of this color.
   *
   * @return 0 ≤ <em>intRed()</em> ≤ 255
   */
  default int intRed() {
    return (int) Math.round(255 * red());
  }

  /**
   * Gets the green component of this color.
   *
   * @return 0 ≤ <em>intGreen()</em> ≤ 255
   */
  default int intGreen() {
    return (int) Math.round(255 * green());
  }

  /**
   * Gets the blue component of this color.
   *
   * @return 0 ≤ <em>intBlue()</em> ≤ 255
   */
  default int intBlue() {
    return (int) Math.round(255 * blue());
  }

  /**
   * Gets the opacity component of this color.
   *
   * @return 0 ≤ <em>intAlpha()</em> ≤ 255
   */
  default int intAlpha() {
    return (int) Math.round(255 * alpha());
  }

  /**
   * Gets this color as a 32-bit RGBA integer.
   *
   * @return 0x<em>AARRGGBB</em>
   */
  default int intValue() {
    return intAlpha() << 24
            | intRed() << 16
            | intGreen() << 8
            | intBlue();
  }

  /**
   * Determines whether this color is fully transparent.
   *
   * @return whether this is transparent
   */
  default boolean isTransparent() {
    return alpha() == 0.0;
  }

  /**
   * Determines whether this color is fully opaque.
   *
   * @return whether this is opaque
   */
  default boolean isOpaque() {
    return alpha() == 1.0;
  }

  /**
   * Overlays this color (the foreground color) over the {@code background}
   * color. This calculates the resulting color when a semi-transparent
   * foreground color is placed in front of some background color, which lets
   * some of the background show through.
   *
   * <p>The result is a weighted average (interpolation) between the
   * foreground color made opaque (that is, with alpha set to 1.0) and the
   * background color, where the foreground alpha determines the weight:
   * {@code this.alpha()} is the weight of foreground, and
   * {@code 1 - this.alpha()} is the weight of the background.
   *
   * @param background the background color
   * @return the combined color
   */
  Color overlay(Color background);

  /**
   * Interpolates a color between {@code this} and {@code other}. The {@code
   * weight} parameter controls the weight of the average: 0.0 returns {@code
   * this}, 1.0 returns {@code other}, and weights in between result in some
   * linear interpolation of the two.
   * <p>
   * For opaque colors, this is merely the weighted average of each color
   * component. When there is non-opaque alpha, however, we need to use a more
   * general formula. Suppose that {@code c == c1.interpolate(weight, c2)} The
   * alpha of the {@code c} is just the weighted average of the alpha of each
   * contributing color:
   * <pre><code>    c.alpha() == Interpolable.interpolate(c1.alpha(), weight, c2.alpha())</code></pre>
   * <p>
   * The correct linear interpolation for the color components with transparency
   * is between each color (or component multiplied) by its alpha, rather than
   * between the colors directly. That is,
   * <pre><code>    c.red() * c.alpha() == Interpolable.interpolate(c1.red() * c1.alpha,
   *                                                    weight,
   *                                                    c2.red() * c2.alpha())</code></pre>
   * and likewise for the green and blue components. Then we can compute the
   * components of the result by dividing out {@code c.alpha()}.
   * <p>
   * <strong>Precondition: </strong>0.0 ≤ {@code weight} ≤ 1.0
   *
   * @param weight the proportion of the distance to go from {@code this} color
   *               to the {@code other} color
   * @param other  the other color
   * @return the interpolated color
   */
  @Override
  Color interpolate(double weight, Color other);

  /**
   * Hashes colors. Every implementation of the {@code Color} interface must
   * return the same hash code that is computed by this method. So a correct
   * implementation of {@code hashCode()} will look like this:
   *
   * <pre><code>@Override
   * public int hashCode() {
   *   return Color.hashCode(this);
   * }</code></pre>
   *
   * @param color the color to hash
   * @return the color's hash code
   */
  public static int hashCode(Color color) {
    int result = 0;
    result = 31 * result + Double.hashCode(color.red());
    result = 31 * result + Double.hashCode(color.green());
    result = 31 * result + Double.hashCode(color.blue());
    result = 31 * result + Double.hashCode(color.alpha());
    return result;
  }
}

