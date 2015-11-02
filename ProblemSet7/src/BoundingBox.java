import static java.lang.Double.isFinite;

/**
 * An class for working with bounding boxes. The constructor is private,
 * but there are several public static factory methods for creating
 * bounding boxes.
 * <p>
 * With one notable exception ({@link #EMPTY}), a bounding box is a
 * finite, rectangular area of the plane. We can specify a bounding box by
 * giving the x coordinates of the left and right sides and the y coordinates
 * of the top and bottom sides.
 * <p>
 * Our bounding boxes are considered to include their boundaries.
 */
public final class BoundingBox implements Boundable, Interpolable<BoundingBox> {
  private final double top;
  private final double bottom;
  private final double left;
  private final double right;

  // Private constructor
  private BoundingBox(double top, double bottom, double left, double right) {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  /**
   * Gets the y coordinate of the top of this bounding box.
   *
   * @return the top
   */
  public double top() {
    return top;
  }

  /**
   * Gets the y coordinate of the bottom of this bounding box.
   *
   * @return the bottom
   */
  public double bottom() {
    return bottom;
  }

  /**
   * Gets the x coordinate of the left side of this bounding box.
   *
   * @return the left
   */
  public double left() {
    return left;
  }

  /**
   * Gets the x coordinate of the right side of this bounding box.
   *
   * @return the right
   */
  public double right() {
    return right;
  }

  /**
   * Gets the width of this bounding box.
   *
   * @return {@code right() - left()}
   */
  public double width() {
    return right - left;
  }

  /**
   * Gets the height of this bounding box.
   *
   * @return {@code bottom() - top()}
   */
  public double height() {
    return bottom - top;
  }

  /**
   * Determines whether the bounding box of {@code other} is a subset of this
   * bounding box.
   *
   * @param other the other object
   * @return whether {@code other} fits within this
   */
  public boolean contains(Boundable other) {
    return contains(other.getBoundingBox());
  }

  /**
   * Determines whether the bounding box {@code other} is a subset of this
   * bounding box.
   *
   * @param other the other bounding box
   * @return whether {@code other} fits within this
   */
  public boolean contains(BoundingBox other) {
    return other.left() >= left()
            && other.right() <= right()
            && other.top() >= top()
            && other.bottom() <= bottom();
  }

  /**
   * Determines whether two bounding boxes are disjoint.
   *
   * @param other the other bounding box
   * @return whether the boxes are disjoint
   * @see #intersect(BoundingBox)
   */
  public boolean isDisjoint(BoundingBox other) {
    return other.right() <= left()
            || other.left() >= right()
            || other.bottom() <= top()
            || other.top() >= bottom();
  }

  /**
   * Joins this bounding box with another bounding box. Returns the smallest
   * bounding box containing both {@code this} and {@code other}.
   *
   * @param other the other bounding box
   * @return {@code BoundingBox.containing(this, other)}
   * @see #containing(Boundable...)
   */
  public BoundingBox join(BoundingBox other) {
    return containing(this, other);
  }

  /**
   * Intersects this bounding box with another bounding box. Returns the
   * largest bounding box contained by both {@code this} and {@code other}.
   * If {@code this} and {@code other} are disjoint, returns {@link #EMPTY}.
   *
   * @param other the other bounding box
   * @return {@code BoundingBox.containedBy(this, other)}
   * @see #containedBy(Boundable, Boundable...)
   * @see #isDisjoint(BoundingBox)
   */
  public BoundingBox intersect(BoundingBox other) {
    return containedBy(this, other);
  }

  @Override
  public BoundingBox getBoundingBox() {
    return this;
  }

  @Override
  public BoundingBox interpolate(double weight, BoundingBox other) {
    return fromTBLR(Interpolable.interpolate(top(), weight, other.top()),
                    Interpolable.interpolate(bottom(), weight, other.bottom()),
                    Interpolable.interpolate(left(), weight, other.left()),
                    Interpolable.interpolate(right(), weight, other.right()));
  }

  /**
    * The empty bounding box.
    */
  public static BoundingBox EMPTY =
   new BoundingBox(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                   Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);

  /**
   * Creates a bounding box with the given sides.
   * <p>
   * <strong>Preconditions:</strong>
   * <ul>
   *   <li>{@code top <= bottom}</li>
   *   <li>{@code left <= right}</li>
   *   <li>All four parameters are finite</li>
   * </ul>
   * There is one exception to the preconditions, which is for the empty
   * bounding box {@link #EMPTY}. For that bounding box, the top and left are
   * represented by positive infinity, and the bottom and right by negative
   * infinity. (This naturally results in correct results for a variety of
   * bounding box operations.) This violates <em>all</em> the preconditions
   * listed above, but the method handles it as a special case.
   *
   * @param top the y coordinate of the top of the bounding box
   * @param bottom the y coordinate of the bottom of the bounding box
   * @param left the x coordinate of the left side of the bounding box
   * @param right the x coordinate of the right side of the bounding box
   * @return the new bounding box
   * @throws IllegalArgumentException if the preconditions are violated
   */
  public static BoundingBox fromTBLR(double top, double bottom,
                                     double left, double right)
  {
    if (top > bottom || left > right) {
      if (top == EMPTY.top() && bottom == EMPTY.bottom()
           && left == EMPTY.left() && right == EMPTY.right()) {
        return EMPTY;
      } else {
        throw new IllegalArgumentException("bounding box must be non-empty");
      }
    }

    if (! (isFinite(top) && isFinite(bottom)
            && isFinite(left) && isFinite (right)))
    {
      throw new IllegalArgumentException("bounding box must be finite");
    }

    return new BoundingBox(top, bottom, left, right);
  }

  /**
   * Returns the smallest bounding box containing all the arguments.
   *
   * @param contents the objects to bound
   * @return the smallest bounding box bounding {@code contents}
   * @see #join(BoundingBox)
   */
  public static BoundingBox containing(Boundable... contents) {
    BoundingBox bb;

    double left   = EMPTY.left();
    double right  = EMPTY.right();
    double top    = EMPTY.top();
    double bottom = EMPTY.bottom();

    for (Boundable item : contents) {
      bb = item.getBoundingBox();

      left   = Double.min(left, bb.left());
      right  = Double.max(right, bb.right());
      top    = Double.min(top, bb.top());
      bottom = Double.max(bottom, bb.bottom());
    }

    return fromTBLR(top, bottom, left, right);
  }

  /**
   * Returns the largest bounding box contained by all the arguments. If the
   * arguments do not all intersect, returns {@link #EMPTY}.
   *
   * @param first one object to use as a bound
   * @param rest the other objects to use as a bounds
   * @return the largest box bounded by {@code containers}
   * @see #intersect(BoundingBox)
   */
  public static BoundingBox containedBy(Boundable first, Boundable... rest) {
    BoundingBox bb = first.getBoundingBox();

    double left   = bb.left();
    double right  = bb.right();
    double top    = bb.top();
    double bottom = bb.bottom();

    for (Boundable item : rest) {
      bb = item.getBoundingBox();

      left   = Double.max(left, bb.left());
      right  = Double.min(right, bb.right());
      top    = Double.max(top, bb.top());
      bottom = Double.min(bottom, bb.bottom());
    }

    try {
      return fromTBLR(top, bottom, left, right);
    } catch (IllegalArgumentException e) {
      return EMPTY;
    }
  }
}


