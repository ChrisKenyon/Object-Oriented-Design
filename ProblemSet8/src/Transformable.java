/**
 * Objects that can be transformed by affine transformations.
 */
public interface Transformable<T extends Transformable<T>> {

  /**
   * Transform an object by the given affine transformation.
   *
   * @param trans transformation to apply
   * @return the transformed object
   */
  T transform(AffineTransformation trans);

  /**
   * Returns this object translated.
   *
   * @param dx the x displacement
   * @param dy the y displacement
   * @return the translated object
   */
  default T translate(double dx, double dy) {
    return transform(AffineTransformation.translation(dx, dy));
  }

  /**
   * Returns this object scaled with the origin fixed.
   *
   * @param factor the factor to scale by
   * @return the scaled object
   */
  default T scale(double factor) {
    return transform(AffineTransformation.scaling(factor));
  }

  /**
   * Returns this object scaled with the specified point fixed.
   *
   * @param center the fixed, central point to scale away from
   * @param factor the factor to scale by
   * @return the scaled object
   */
  default T scale(Point<?> center, double factor) {
    return transform(AffineTransformation.scaling(center, factor));
  }

  /**
   * Returns this object rotated with the origin fixed.
   *
   * @param theta the angle to rotate by, in radians
   * @return the rotated object
   */
  default T rotate(double theta) {
    return transform(AffineTransformation.rotation(theta));
  }

  /**
   * Returns this object rotated with the specified point fixed.
   *
   * @param center the fixed, central point to rotate around
   * @param theta  the angle to rotate by, in radians
   * @return the rotated object
   */
  default T rotate(Point<?> center, double theta) {
    return transform(AffineTransformation.rotation(center, theta));
  }
}
