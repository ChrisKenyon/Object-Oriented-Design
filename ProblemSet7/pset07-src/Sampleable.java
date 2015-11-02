/**
 * Interface for sampling colors from a visual element. This can be used to
 * rasterize at a particular resolution.
 */
public interface Sampleable extends Boundable {
  /**
   * Samples the color at a particular point.
   *
   * @param point the point to sample
   * @return the color at {@code point}
   */
  Color getColorAt(Point<Double> point);
}
