/**
 * Interface for objects that know how to render themselves to a raster.
 */
public interface Renderable extends Boundable, Transformable<Renderable> {

  /**
   * Renders this object on the given raster while transforming it as specified.
   *
   * @param trans  the transformation to apply
   * @param raster the raster to render on
   * @throws IllegalArgumentException if {@code trans} is not invertible
   */
  void render(AffineTransformation trans, Raster raster);

  /**
   * Renders this object on the given raster.
   *
   * @param raster the raster to render on
   */
  default void render(Raster raster) {
    render(AffineTransformation.IDENTITY, raster);
  }
}
