/**
 * A sampler encapsulates a strategy for rendering a {@link Sampleable} to a
 * {@link Raster}.
 */
public interface Sampler {
  /**
   * Renders the given {@code Sampleable} to the given {@code Raster}. The
   * dimensions are translated one-for-one, with each pixel in the raster
   * corresponding to a unit square in the scene. For example, the pixel
   * (3, 4) in the raster corresponds to the unit square in the scene whose
   * upper left is at (3.0, 4.0) and whose lower right is at (4.0, 5.0).
   *
   * @param scene the sampleable to sample from
   * @param raster the raster to render to
   */
  void sample(Sampleable scene, Raster raster);
}
