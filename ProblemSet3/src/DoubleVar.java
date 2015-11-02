/**
 * A continuous variable having type {@code double}. That is, a {@code
 * DoubleVar} represents the data points for some statistical variable. (It's
 * safe to think of it as a glorified array of {@code double}s.)
 */

// DoubleVar has a Class Invariant that it contains an
// array, called array, of 'count' doubles
public final class DoubleVar {
  /**
   * The sample size.
   * The fact that this is a public field is not a problem because it is final
   * and it is unlikely that any changes to this implementation would affect
   * outside class accessing it, considering it is a straightforward field.
   * This simply allows other classes to see the length of the array of this
   * class, which may be helpful, but does not allow another class to
   * change the value.
   */
  public final int count;

  // The array of data points
  private final double[] array;

  /**
   * Constructs a {@code DoubleVar} from a {@code double[]} (which may be
   * given using varargs syntax).
   *
   * @param args the array of doubles
   */
  public DoubleVar(double... args) {
    array = args.clone();
    count = array.length;
  }

  /**
   * Returns the sum of all the data points.
   *
   * @return the sum
   */
  public double sum() {
    double sum = 0;

    for (double d : array) {
      sum += d;
    }

    return sum;
  }

  /**
   * Returns the arithmetic mean (the average) of the data set.
   *@PRECONDITION: Count cannot be 0
   * @return the mean
   */
  public double mean() {
    return sum() / count;
  }

  /**
   * Returns the (population) variance of the data set.
   *
   * @return the variance
   */
  public double variance() {
    return varianceHelper() / count;
  }

  /**
   * Returns the sample variance of the data set.
   * @PRECONDITION: Count cannot be 1
   * @return the sample variance
   */
  public double sampleVariance() {
    return varianceHelper() / (count - 1);
  }

  // Helper for computing variance that sums the squares of the displacements
  // from the mean, but doesn't divide by the count (- 1), since that differs
  // between population variance and sample variance.
  private double varianceHelper() {
    double mean = mean();
    double sum = 0;

    for (double f : array) {
      double df = f - mean;
      sum += df * df;
    }

    return sum;
  }

  /**
   * Returns the (population) standard deviation of the data set.
   * PRECONDITION (is held up by the method): variance() > 0
   * @return the standard deviation
   */
  public double stdDev() {
    return Math.sqrt(variance());
  }

  /**
   * Returns the sample standard deviation of the data set.
   * PRECONDITION (is held up by the method): sampleVariance() > 0
   * @return the sample standard deviation
   */
  public double sampleStdDev() {
    return Math.sqrt(sampleVariance());
  }
}
