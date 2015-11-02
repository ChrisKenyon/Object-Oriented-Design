import edu.neu.ccs.cs3500.pixel_buffer.PixelBufferApplication;
import edu.neu.ccs.cs3500.pixel_buffer.PixelSurface;
public final class MySecondPBA extends RasterApplication{

    @Override
    protected void render(int[] buffer, int width, int height) {
        circle(width, height, buffer, 350, 200, 100, 0xFF990000);
        circle(width, height, buffer, 350, 200, 75, 0xFF000099);
        circle(width, height, buffer, 350, 200, 50, 0xFF009900);
    }

    public static void main(String[] args) {
        new MySecondPBA().launch("second app",1200,1024);
    }

    @Override
    protected void render(PixelSurface pixelSurface) throws Exception {
        int width = pixelSurface.width();
        int height = pixelSurface.height();
        int[] buffer = new int[width * height];

        render(buffer,width,height);


    }

    private void circle(int width, int height, int[] buffer, int cx, int cy, int radius, int color) {
        for (int row = 0; row<height; ++row){
            for (int col = 0; col<width; ++col) {
                int dx = col-cx;
                int dy = row -cy;
                if (dx*dx+dy*dy <=radius *radius)
                    buffer[row * width + col] = color;
            }
        }
    }


    private int u8(double intensity){
        return (int) Math.round(255*intensity);
    }
}

