/**
 * A simple super-sampling renderer. This takes more than one sample per pixel, for a form of simple
 * and expensive anti-aliasing.
 */
public final class SimpleSuperSampler implements Sampler {

  private final int factor;

  /**
   * Creates a new super-sampling renderer with the given sampling factor. In particular, it will
   * take factor^2 samples per pixel.
   *
   * @param factor the sampling factor
   */
  public SimpleSuperSampler(int factor) {
    this.factor = factor;
  }

  @Override
  public void sample(Sampleable scene, Raster raster) {
    int width = raster.width();
    int height = raster.height();
    Colors.Blender cb = new Colors.Blender();

    for (int col = 0; col < width; ++col) {
      for (int row = 0; row < height; ++row) {
        cb.reset();

        for (int i = 0; i < factor; ++i) {
          for (int j = 0; j < factor; ++j) {
            Point<Double> point = DoublePoint.point(col + (i + 0.5) / factor,
                                                    row + (j + 0.5) / factor);
            cb.add(scene.getColorAt(point));
          }
        }

        raster.setRGBA(IntegerPoint.point(col, row), cb.build().intValue());
      }
    }
  }
}
