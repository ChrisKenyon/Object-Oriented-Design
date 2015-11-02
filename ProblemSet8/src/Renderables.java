import java.util.ArrayList;
import java.util.List;

/**
 * Static factories for creating {@link Renderables}s.
 */
public final class Renderables {

  /**
   * Prevents instantiation.
   */
  private Renderables() {
  }

  /**
   * Creates a {@link Renderable} circle with the given parameters.
   *
   * @param center the center of the circle
   * @param radius the radius of the circle
   * @param color  the color of the circle
   * @return the circle
   */
  public static Renderable circle(Point<Double> center, double radius,
                                  Color color) {
    return new AbstractRenderable(center.y() - radius, center.y() + radius,
                                  center.x() - radius, center.x() + radius) {
      @Override
      public void render(AffineTransformation trans, Raster raster) {
        BoundingBox bb = getBoundingBox().transform(trans);
        AffineTransformation inv = trans.inverse();

        int left = Integer.max(0, bb.intLeft());
        int right = Integer.min(raster.width() - 1, bb.intRight());
        int top = Integer.max(0, bb.intTop());
        int bottom = Integer.min(raster.height() - 1, bb.intBottom());

        for (int x = left; x <= right; ++x) {
          for (int y = top; y <= bottom; ++y) {
            Point<Double> p = DoublePoint.point(x + .5, y + .5).transform(inv);
            if (center.distanceTo(p) <= radius) {
              raster.setColor(IntegerPoint.point(x, y), color);
            }
          }
        }
      }
    };
  }

  public static Renderable rectangle(double top, double bottom, double left, double right,
                                     Color color) {
    {
      return new AbstractRenderable(top, bottom, left, right) {

        @Override
        public void render(AffineTransformation trans, Raster raster) {
          BoundingBox bb = getBoundingBox().transform(trans);
          AffineTransformation inv = trans.inverse();

          int left = Integer.max(0, bb.intLeft());
          int right = Integer.min(raster.width() - 1, bb.intRight());
          int top = Integer.max(0, bb.intTop());
          int bottom = Integer.min(raster.height() - 1, bb.intBottom());
          for (int x = left; x <= right; ++x) {
            for (int y = top; y <= bottom; ++y) {
              Point<Double> p = DoublePoint.point(x, y).transform(inv);
              Point<Double> pPlus = DoublePoint.point(x - .5, y - .5).transform(inv);
              raster.setColor(p.toIntegerPoint(), color);
              raster.setColor(pPlus.toIntegerPoint(), color);
            }
          }
        }
      };
    }
  }

  /**
   * Adapts a {@link Sampleable} to conform to the {@link Renderable} interface.
   *
   * @param base the sampleable to adapt
   * @return an equivalent {@code Renderable}
   * @see Sampleables#sampleable(Renderable)
   * @see Sampleables#sampleable(double, Renderable)
   */
  public static Renderable renderable(Sampleable base) {
    if (base == null) {
      throw new NullPointerException("Can't pass null base");
    }
    BoundingBox bb = base.getBoundingBox();

    return new AbstractRenderable(bb) {
      @Override
      public void render(Raster raster) {
        int left = Integer.max(0, bb.intLeft());
        int right = Integer.min(raster.width() - 1, bb.intRight());
        int top = Integer.max(0, bb.intTop());
        int bottom = Integer.min(raster.height() - 1, bb.intBottom());

        for (int x = left; x <= right; ++x) {
          for (int y = top; y <= bottom; ++y) {
            Color color = base.getColorAt(DoublePoint.point(x + .5, y + .5));
            if (!color.isTransparent()) {
              raster.setColor(IntegerPoint.point(x, y), color);
            }
          }
        }
      }

      @Override
      public void render(AffineTransformation trans, Raster raster) {
        renderable(trans.apply(base)).render(raster);
      }
    };
  }

  public static Renderable overlay(Renderable... layers) {
    BoundingBox bb = BoundingBox.EMPTY;
    for (Renderable r : layers) {
      bb = BoundingBox.containing(bb, r.getBoundingBox());
    }
    return new AbstractRenderable(bb) {
      @Override
      public void render(AffineTransformation trans, Raster raster) {
        Raster raster1 = new OverlayRaster(raster);
        for (int i = layers.length - 1; i >= 0; --i) {
          layers[i].render(raster1);
        }
      }
    };
  }
}
