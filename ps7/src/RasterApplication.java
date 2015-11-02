import edu.neu.ccs.cs3500.pixel_buffer.PixelBufferApplication;
import edu.neu.ccs.cs3500.pixel_buffer.PixelSurface;

/**
 * The raster application framework encapsulates the process of rendering a
 * single image to a window on the screen.  To use this framework:
 * <ol>
 *   <li>Extend the {@link RasterApplication} class.
 *   <li>Write a main method that creates an instance of your subclass and
 *   launches it, specifying the window title and dimensions:
 * <pre><code>public static void main(String[] args) {
 *   new MyRasterApplication().launch("Title", 800, 600);
 * }</code></pre>
 *   </li>
 *   <li>
 *     Override method {@link #render(Raster)} to do the actual
 *     rendering. For more details about what this means, see the documentation
 *     for {@link #render(Raster) render} below.
 *   </li>
 * </ol>
 */
public abstract class RasterApplication {
    private final PixelBufferApplication delegate = new PixelBufferDelegate();

    /**
     * Renders the desired image to the given raster.
     *
     * @param raster the raster to render to
     * @throws Exception if necessary
     */
    protected abstract void render(Raster raster) throws Exception;

    /**
     * Launches the application with the given window title and dimensions.
     * Calling this more than once will throw an exception.
     *
     * @param windowTitle the window title
     * @param width the window width
     * @param height the window height
     * @throws IllegalArgumentException if the dimensions aren't positive
     * @throws IllegalStateException if called more than once
     */
    public final void launch(String windowTitle, int width, int height) {
        delegate.launch(windowTitle, width, height);
    }

    private class PixelBufferDelegate extends PixelBufferApplication {
        @Override
        protected final void render(PixelSurface surface) throws Exception {
            int width = surface.width();
            int height = surface.height();
            int buffer[] = new int[width * height];
            ArrayRaster raster = new ArrayRaster(width, height);

            RasterApplication.this.render(raster);

            surface.setPixels(0, 0, width, height, raster.getBuffer(), 0, width);
        }
    }
}