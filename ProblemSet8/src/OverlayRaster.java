import java.awt.*;

/**
 * Created by Chris on 12/1/2014.
 */
public class OverlayRaster implements Raster {

  private Raster delegate;

  OverlayRaster(int width, int height) {
    delegate = new ArrayRaster(width, height);
  }

  public OverlayRaster(Raster base) {
    if (base == null) {
      throw new NullPointerException("null base");
    }
    delegate = base;
  }


  @Override
  public int width() {
    return delegate.width();
  }

  @Override
  public int height() {
    return delegate.height();
  }

  @Override
  public void setRGBA(Point<Integer> integerPoint, int i) {
    Color overlayColor = Colors.rgba(i).overlay(getColor(integerPoint));
    delegate.setRGBA(integerPoint, overlayColor.intValue());
  }

  @Override
  public int getRGBA(Point<Integer> integerPoint) {
    return delegate.getRGBA(integerPoint);
  }
}
