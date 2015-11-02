import edu.neu.ccs.cs3500.pixel_buffer.PixelBufferApplication;
import edu.neu.ccs.cs3500.pixel_buffer.PixelSurface;

public abstract class RasterApplication {

    private final MyPixelBufferApplication pixelBufferApplication = new MyPixelBufferApplication();

    protected abstract void render(int[] buffer, int width, int height);

    protected abstract void render(PixelSurface pixelSurface) throws Exception;

    public void launch(String windowTitle, int width, int height) {
        pixelBufferApplication.launch(windowTitle, width, height);
    }

    private class MyPixelBufferApplication extends PixelBufferApplication {
        protected void render(PixelSurface pixelSurface) throws Exception {
            RasterApplication.this.render(pixelSurface);
        }
    }
}
