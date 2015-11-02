/**
 * Static factories for creating {@link Sampleable}s, including several
 * {@link Sampleable} decorators.
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
     * Scales a sampleable by the given factor. The position of the origin is
     * fixed.
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
     * Overlays several sampleables from top to bottom. This method ignores the
     * alpha channel in the sense that it doesn't show lower layers through
     * semi-transparent upper layers.
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

    public static Sampleable addTransparency(double alpha, Sampleable base) {
        return new AbstractSampleable(base.getBoundingBox()) {
            @Override
            public Color getColorAt(Point<Double> point) {
                return Colors.rgba(base.getColorAt(point).red(),
                        base.getColorAt(point).blue(),
                        base.getColorAt(point).green(),
                        base.getColorAt(point).alpha() * alpha);
            }
        };
    }

    /**
     * Helper method for overlay
     * @param bb bounding box containing {@code layers}
     * @param blended first should be the last element of layers,
     *                and will recursively become the overlay of each of the colors before it
     * @param toBlendIndex index before the blended end of the array
     * @param layers the array of layers
     * @return
     */
    private static Sampleable overlay(BoundingBox bb, Sampleable blended, int toBlendIndex, Sampleable... layers){
        if (toBlendIndex!=-1){
            return overlay(bb, new AbstractSampleable(bb) {
                @Override
                public Color getColorAt(Point<Double> point) {
                    return layers[toBlendIndex].getColorAt(point).overlay(blended.getColorAt(point));
                }
            },toBlendIndex-1,layers);
        } else return blended;
    }


    public static Sampleable overlay(Sampleable... layers){

        if (layers.length == 0){
            return new AbstractSampleable(BoundingBox.EMPTY) {
                @Override
                public Color getColorAt(Point<Double> point) {
                    return Colors.TRANSPARENT;
                }
            };
        }
        if (layers.length==1){
            return new AbstractSampleable(layers[0].getBoundingBox()) {
                @Override
                public Color getColorAt(Point<Double> point) {
                    return layers[0].getColorAt(point);
                }
            };
        }
        return overlay(BoundingBox.containing(layers),layers[layers.length-1],layers.length-1,layers);
    }
}