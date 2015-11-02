/**
 * A 2-D affine transformation matrix has the form
 * <pre>
 *   [ a b c ]
 *   [ d e f ]
 *   [ 0 0 1 ]
 * </pre>
 *
 * It is applied to a point {@code (x, y)} via matrix multiplication with the column vector
 * <pre>
 *   [ x ]
 *   [ y ]
 *   [ 1 ]
 * </pre>
 *
 * The resulting point is {@code (a * x + b * y + c, d * x + e * y + f)}.
 */
public final class AffineTransformation
    implements Transformable<AffineTransformation> {

  private final double a, b, c, d, e, f;

  private AffineTransformation(double a, double b, double c,
                               double d, double e, double f) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.e = e;
    this.f = f;
  }

  /**
   * Creates a new affine transformation with the given elements. The resulting affine
   * transformation corresponds to the transformation matrix
   * <pre>
   *   [ a b c ]
   *   [ d e f ]
   *   [ 0 0 1 ]
   * </pre>
   *
   * @param a the x coordinate scaling element
   * @param b the x coordinate shearing element
   * @param c the x coordinate translation element
   * @param d the y coordinate scaling element
   * @param e the y coordinate shearing element
   * @param f the y coordinate translation element
   * @return the affine transformation
   */
  public static AffineTransformation matrix(double a, double b, double c,
                                            double d, double e, double f) {
    return new AffineTransformation(a, b, c, d, e, f);
  }

  /**
   * The identity transformation.
   */
  public static final AffineTransformation IDENTITY = matrix(1, 0, 0, 0, 1, 0);

  /**
   * Returns a translation transformation.
   *
   * @param dx the x displacement
   * @param dy the y displacement
   * @return the translation transformation
   */
  public static AffineTransformation translation(double dx, double dy) {
    return matrix(1, 0, dx, 0, 1, dy);
  }

  /**
   * Returns a scaling transformation that leaves the origin fixed.
   *
   * @param factor the factor to scale by
   * @return the scaling transformation
   */
  public static AffineTransformation scaling(double factor) {
    return matrix(factor, 0, 0, 0, factor, 0);
  }

  /**
   * Returns a scaling transformation that leaves the specified point fixed.
   *
   * @param center the fixed, central point to scale away from
   * @param factor the factor to scale by
   * @return the scaling transformation
   */
  public static AffineTransformation scaling(Point<?> center, double factor) {
    return matrix(factor, 0, (1 - factor) * center.x(),
                  0, factor, (1 - factor) * center.y());
  }

  /**
   * Returns a rotation transformation that leaves the origin fixed.
   *
   * @param theta the angle to rotate by, in radians
   * @return the rotation transformation
   */
  public static AffineTransformation rotation(double theta) {
    double sin = Math.sin(theta);
    double cos = Math.cos(theta);

    return matrix(cos, -sin, 0, sin, cos, 0);
  }

  /**
   * Returns a rotation transformation that leaves the specified point fixed.
   *
   * @param center the fixed, central point to rotate around
   * @param theta  the angle to rotate by, in radians
   * @return the rotation transformation
   */
  public static AffineTransformation rotation(Point<?> center, double theta) {
    double sin = Math.sin(theta);
    double cos = Math.cos(theta);
    double x = center.x();
    double y = center.y();

    return matrix(cos, -sin, x - cos * x + sin * y,
                  sin, cos, y - sin * x - cos * y);
  }

  /**
   * Gets the x coordinate scaling element of the transformation.
   *
   * @return element {@code a} of the affine transformation matrix
   */
  public double getA() {
    return a;
  }

  /**
   * Gets the x coordinate shearing element of the transformation.
   *
   * @return element {@code b} of the affine transformation matrix
   */
  public double getB() {
    return b;
  }

  /**
   * Gets the x coordinate translation element of the transformation.
   *
   * @return element {@code c} of the affine transformation matrix
   */
  public double getC() {
    return c;
  }

  /**
   * Gets the y coordinate scaling element of the transformation.
   *
   * @return element {@code d} of the affine transformation matrix
   */
  public double getD() {
    return d;
  }

  /**
   * Gets the y coordinate shearing element of the transformation.
   *
   * @return element {@code e} of the affine transformation matrix
   */
  public double getE() {
    return e;
  }

  /**
   * Gets the y coordinate translation element of the transformation.
   *
   * @return element {@code f} of the affine transformation matrix
   */
  public double getF() {
    return f;
  }

  /**
   * Returns the x coordinate of the result of applying this transformation to the point {@code (x,
   *y)}.
   *
   * @param x the x coordinate of the point to transform
   * @param y the y coordinate of the point to transform
   * @return the x coordinate of the transformed point
   */
  public double applyX(double x, double y) {
    return a * x + b * y + c;
  }

  /**
   * Returns the y coordinate of the result of applying this transformation to the point {@code (x,
   *y)}.
   *
   * @param x the x coordinate of the point to transform
   * @param y the y coordinate of the point to transform
   * @return the y coordinate of the transformed point
   */
  public double applyY(double x, double y) {
    return d * x + e * y + f;
  }

  /**
   * Applies this transformation to any object implementing the {@link Transformable} interface.
   *
   * @param t   the object to transform
   * @param <T> the type of the object to transform
   * @return the transformed object
   */
  public <T extends Transformable<T>> T apply(T t) {
    return t.transform(this);
  }

  /**
   * Composes two affine transformations. The resulting transformation is equivalent to applying
   * first {@code other} and then {@code this}.
   *
   * @param other the transformation to compose with
   * @return the composed transformation
   */
  public AffineTransformation compose(AffineTransformation other) {
    /*
                 [ a2 b2 c2 ]
                 [ d2 e2 f2 ]
                 [ 0  0  1  ]
    [ a1 b1 c1 ] [ a1 a2 + b1 d2   a1 b2 + b1 e2   a1 c2 + b1 f2 + c1 ]
    [ d1 e1 f1 ] [ d1 a2 + e1 d2   d1 b2 + e1 e2   d1 c2 + e1 f2 + f1 ]
    [ 0  0  1  ] [ 0               0               1                  ]
     */

    return matrix(a * other.a + b * other.d,
                  a * other.b + b * other.e,
                  a * other.c + b * other.f + c,
                  d * other.a + e * other.d,
                  d * other.b + e * other.e,
                  d * other.c + e * other.f + f);
  }

  /**
   * Returns the inverse of this affine transformation if it has one.
   *
   * @return the inverse transformation
   * @throws IllegalArgumentException if this transformation has no inverse
   */
  public AffineTransformation inverse() {
    double determinant = a * e - b * d;

    if (determinant == 0.0) {
      throw new IllegalArgumentException("not invertible");
    }

    double newA = e / determinant;
    double newB = -b / determinant;
    double newD = -d / determinant;
    double newE = a / determinant;

    double newC = -(newA * c + newB * f);
    double newF = -(newD * c + newE * f);

    return matrix(newA, newB, newC, newD, newE, newF);
  }

  @Override
  public AffineTransformation transform(AffineTransformation other) {
    return other.compose(this);
  }

  /**
   * A builder for {@link AffineTransformation}s. This allows building a transformation in several
   * steps rather than specifying the whole transformation matrix directly.
   *
   * <strong>Note:</strong> The {@link Transformable} methods in this class mutate the builder and
   * return it instead of returning a new object.
   */
  public final static class Builder implements Transformable<Builder> {

    private double a, b, c, d, e, f;

    /**
     * Constructs a new affine transformation builder, initialized to the identity transformation.
     */
    public Builder() {
      a = 1;
      b = 0;
      c = 0;
      d = 0;
      e = 1;
      f = 0;
    }

    /**
     * Constructs a new affine transformation builder, initialized to the given transformation.
     *
     * @param trans the transformation to initialize the builder to
     */
    public Builder(AffineTransformation trans) {
      a = trans.a;
      b = trans.b;
      c = trans.c;
      d = trans.d;
      e = trans.e;
      f = trans.f;
    }

    /**
     * Sets the x coordinate scaling element of the transformation matrix.
     *
     * @param a the new x coordinate scaling element
     * @return this builder, for method chaining
     */
    public Builder a(double a) {
      this.a = a;
      return this;
    }

    /**
     * Sets the x coordinate shearing element of the transformation matrix.
     *
     * @param b the new x coordinate shearing element
     * @return this builder, for method chaining
     */
    public Builder b(double b) {
      this.b = b;
      return this;
    }

    /**
     * Sets the x coordinate rotation element of the transformation matrix.
     *
     * @param c the new x coordinate translation element
     * @return this builder, for method chaining
     */
    public Builder c(double c) {
      this.c = c;
      return this;
    }

    /**
     * Sets the y coordinate shearing element of the transformation matrix.
     *
     * @param d the new y coordinate shearing element
     * @return this builder, for method chaining
     */
    public Builder d(double d) {
      this.d = d;
      return this;
    }

    /**
     * Sets the y coordinate scaling element of the transformation matrix.
     *
     * @param e the new y coordinate shearing element
     * @return this builder, for method chaining
     */
    public Builder e(double e) {
      this.e = e;
      return this;
    }

    /**
     * Sets the y coordinate translation element of the transformation matrix.
     *
     * @param f the new y coordinate translation element
     * @return this builder, for method chaining
     */
    public Builder f(double f) {
      this.f = f;
      return this;
    }

    @Override
    public Builder transform(AffineTransformation trans) {
      double a_ = a, b_ = b, c_ = c, d_ = d, e_ = e, f_ = f;

      a = trans.a * a_ + trans.b * d_;
      b = trans.a * b_ + trans.b * e_;
      c = trans.a * c_ + trans.b * f_ + c_;
      d = trans.d * a_ + trans.e * d_;
      e = trans.d * b_ + trans.e * e_;
      f = trans.d * c_ + trans.e * f_ + f_;

      return this;
    }

    @Override
    public Builder translate(double dx, double dy) {
      c += dx;
      f += dy;
      return this;
    }

    @Override
    public Builder scale(double factor) {
      a *= factor;
      b *= factor;
      c *= factor;
      d *= factor;
      e *= factor;
      f *= factor;
      return this;
    }

    @Override
    public Builder scale(Point<?> center, double factor) {
      scale(factor);
      translate((1 - factor) * center.x(),
                (1 - factor) * center.y());
      return this;
    }

    @Override
    public Builder rotate(double theta) {
      double cos = Math.cos(theta);
      double sin = Math.sin(theta);

      double a_ = a;
      a = cos * a_ - sin * d;
      d = sin * a_ + cos * d;

      double b_ = b;
      b = cos * b_ - sin * e;
      e = sin * b_ + cos * e;

      double c_ = c;
      c = cos * c_ - sin * f;
      f = sin * c_ + cos * f;

      return this;
    }

    @Override
    public Builder rotate(Point<?> center, double theta) {
      double cos = Math.cos(theta);
      double sin = Math.sin(theta);
      double x = center.x();
      double y = center.y();

      rotate(theta);
      translate(x - cos * x + sin * y,
                y - sin * x - cos * y);
      return this;
    }

    /**
     * Returns the affine transformation as specified by this builder.
     *
     * @return an affine transformation
     */
    public AffineTransformation build() {
      return matrix(a, b, c, d, e, f);
    }
  }
}
