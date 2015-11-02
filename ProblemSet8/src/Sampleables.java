/**
 * Static factories for creating {@link Sampleable}s, including several sampleable decorators.
 */
public final class Sampleables {

  /**
   * Prevents instantiation.
   */
  private Sampleables() {
  }

  /**
   * Creates a sampleable circle with the given parameters.
   *
   * @param center the center of the circle
   * @param radius the radius of the circle
   * @param color  the color of the circle
   * @return the circle
   */
  public static Sampleable circle(Point<Double> center, double radius,
                                  Color color) {
    return new AbstractSampleable(center.y() - radius, center.y() + radius,
                                  center.x() - radius, center.x() + radius) {
      @Override
      public Color getColorAt(Point<Double> point) {
        if (center.distanceTo(point) <= radius) {
          return color;
        } else {
          return Colors.TRANSPARENT;
        }
      }
    };
  }

  /**
   * Scales a sampleable by the given factor. The position of the origin is fixed.
   *
   * @param factor the factor to scale by
   * @param base   the sampleable to scale
   * @return the scaled sampleable
   */
  public static Sampleable scale(double factor, Sampleable base) {
    BoundingBox bb = base.getBoundingBox();
    return new AbstractSampleable(factor * bb.top(), factor * bb.bottom(),
                                  factor * bb.left(), factor * bb.right()) {
      @Override
      public Color getColorAt(Point<Double> point) {
        return base.getColorAt(point.scaleBy(1 / factor));
      }
    };
  }

  /**
   * Overlays several sampleables from top to bottom. This method ignores the alpha channel in the
   * sense that it doesn't show lower layers through semi-transparent upper layers.
   *
   * @param elements the sampleables to overlay, top first
   * @return the composed sampleable
   */
  public static Sampleable opaqueOverlay(Sampleable... elements) {
    return new AbstractSampleable(BoundingBox.containing(elements)) {
      @Override
      public Color getColorAt(Point<Double> point) {
        for (Sampleable element : elements) {
          BoundingBox bb = element.getBoundingBox();
          if (bb.contains(point)) {
            Color color = element.getColorAt(point);
            if (!color.isTransparent()) {
              return color;
            }
          }
        }

        return Colors.TRANSPARENT;
      }
    };
  }

  /**
   * Helper method for overlay
   *
   * @param bb           bounding box containing {@code layers}
   * @param blended      first should be the last element of layers, and will recursively become the
   *                     overlay of each of the colors before it
   * @param toBlendIndex index before the blended end of the array
   * @param layers       the array of layers
   */

  private static Sampleable overlay(BoundingBox bb, Sampleable blended,

                                    int toBlendIndex, Sampleable... layers) {

    if (toBlendIndex != -1) {
      return overlay(bb, new AbstractSampleable(bb) {
        @Override
        public Color getColorAt(Point<Double> point) {
          if (layers[toBlendIndex].getColorAt(point).intAlpha() == 255) {
            return layers[toBlendIndex].getColorAt(point);
          }
          return layers[toBlendIndex].getColorAt(point)
              .overlay(blended.getColorAt(point));
        }
      }, toBlendIndex - 1, layers);
    } else {
      return blended;
    }
  }

  /**
   * Overlays layers of {@code Sampleable}s with the first element of {@code layers} being on top.
   * This method respects the transparency in the alpha component of each layer
   *
   * @param layers the array of layers
   */
  public static Sampleable overlay(Sampleable... layers) {
    if (layers.length == 0) {
      throw new IllegalArgumentException("Empty array was passed into overlay");
    }
    if (layers.length == 1) {
      return new AbstractSampleable(layers[0].getBoundingBox()) {
        @Override
        public Color getColorAt(Point<Double> point) {
          return layers[0].getColorAt(point);
        }
      };
    }
    return overlay(BoundingBox.containing(layers), layers[layers.length - 1],
                   layers.length - 1, layers);
  }

  /**
   * Adds (partial) transparency to a sampleable. This decorator multiplies the alpha channel of the
   * base sampleable by an opacity factor, {@code alpha}. If the base sampleable is opaque, then
   * {@code alpha} gives the opacity of the result, but to the extent that the base sampleable is
   * semi-transparent, {@code alpha} applies additional transparency on top of that by multiplying
   * the alpha at each point.
   *
   * @param alpha the opacity of the resulting sampleable
   * @param base  the sampleable to make partially transparent
   * @return the partially transparent sampleable
   */
  public static Sampleable addTransparency(double alpha, Sampleable base) {
    return new AbstractSampleable(base.getBoundingBox()) {
      @Override
      public Color getColorAt(Point<Double> point) {
        Color c = base.getColorAt(point);
        return Colors.rgba(c.red(), c.green(), c.blue(), alpha * c.alpha());
      }
    };
  }

  /**
   * Adapts a {@link Renderable} to conform to the {@link Sampleable} interface. This adaptor
   * renders the renderable at {@code factor} times its native resolution, which can prevent or
   * reduce pixelation if the resulting sampleable is subsequently transformed. Using a factor of
   * {@code 1} is equivalent to {@link #sampleable(Renderable)}.
   *
   * @param factor the resolution to render at, effectively the amount of scaling that can be
   *               applied before pixelation
   * @param base   the renderable to adapt
   * @return an equivalent sampleable
   * @see #sampleable(Renderable)
   * @see Renderables#renderable(Sampleable)
   */
  // Uncomment this method after implementing #sampleable(Renderable):
  public static Sampleable sampleable(double factor, Renderable base) {
    return sampleable(base.scale(factor)).scale(1 / factor);
  }


  public static Sampleable sampleable(Renderable base) {
    BoundingBox bb = base.getBoundingBox();
    OverlayRaster or = new OverlayRaster(bb.intRight() + 1, bb.intBottom() + 1);
    base.render(or);
    return new AbstractSampleable(bb) {
      @Override
      public Color getColorAt(Point<Double> point) {
        return or.getColor(point.toIntegerPoint());
      }
    };
  }
}
