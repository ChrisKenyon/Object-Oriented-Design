/**
 * A simple sampler. Samples the {@link Sampleable} once in the center of each
 * {@link Raster} pixel's corresponding box.
 */
public class SimpleSampler implements Sampler {
  @Override
  public void sample(Sampleable scene, Raster raster) {
    int width  = raster.width();
    int height = raster.height();

    for (int col = 0; col < width; ++col) {
      for (int row = 0; row < height; ++row) {
        Color color = scene.getColorAt(DoublePoint.point(col + 0.5, row + 0.5));
        raster.setRGBA(IntegerPoint.point(col, row), color.intValue());
      }
    }
  }
}
