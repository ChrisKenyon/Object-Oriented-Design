import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Perhaps the simplest possible implementation of the {@link Raster}
 * interface, this class represents a raster as an array list of array
 * lists of integers.
 */
public final class ListRaster extends AbstractRaster {
  private final List<List<Integer>> rows;

  /*
   * CLASS INVARIANTS:
   *  - rows.size() == height()
   *  - rows.get(i).size() == width() for 0 <= i < height()
   */

  public ListRaster(int width, int height) {
    super(width, height);
    rows =  initializeArrayList(height, row ->
              initializeArrayList(width, col -> 0));
  }

  @Override
  public void setRGBA(Point<Integer> point, int colorValue) {
    boundsCheck(point);
    rows.get(point.intY()).set(point.intX(), colorValue);
  }

  @Override
  public int getRGBA(Point<Integer> point) {
    boundsCheck(point);
    return rows.get(point.intY()).get(point.intX());
  }

  /**
   * Copies the contents of the raster into a row-major array of the right size.
   *
   * <p><strong>Precondition:</strong>
   *     {@code array.length >= width() * height()}
   *
   * @param array the array to dump the pixels into
   */
  public void dump(int[] array) {
    int i = 0;
    for (List<Integer> row : rows) {
      for (int color : row) {
        array[i++] = color;
      }
    }
  }

  /**
   * Allocates and fills a new {@link ArrayList}, using a function object to
   * generate the elements. That is, each index {@code i} of the array list
   * will map to the value {@code f.apply(i)}.
   *
   * @param size the size of the new {@code ArrayList}
   * @param f the function object mapping array indices to initial values
   * @param <E> the element type
   * @return a new {@code ArrayList} with elements from {@code f.apply(0)} up
   * to {@code f.apply(size - 1)}.
   */
  public static <E>
  ArrayList<E> initializeArrayList(int size, IntFunction<E> f) {
    ArrayList<E> result = new ArrayList<>(size);
    for (int i = 0; i < size; ++i) {
      result.add(f.apply(i));
    }
    return result;
  }
}
