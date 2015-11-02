import edu.neu.ccs.cs3500.pixel_buffer.PixelBufferApplication;
import edu.neu.ccs.cs3500.pixel_buffer.PixelSurface;
public final class MyFirstPBA extends PixelBufferApplication{
   public static void main(String[] args) {
       new MyFirstPBA().launch("first app",1200,1024);
   }

    @Override
    protected void render(PixelSurface pixelSurface) throws Exception {
        int width = pixelSurface.width();
        int height = pixelSurface.height();
        int[] buffer = new int[width * height];

        for (int row = 0; row<height; ++row){
            for (int col = 0; col<width; ++col) {
                int red = u8(1-(double)col/width);
                int green = u8((double) col/width);
                int blue = u8((double)row/height);

                buffer[row * width + col] = 0xFF <<24
                        | red <<16
                        | green <<8
                        | blue<< 0;
            }
        }

        pixelSurface.setPixels(0,0,width,height,buffer,0,width);
    }


    private int u8(double intensity){
        return (int) Math.round(255*intensity);
    }
}
