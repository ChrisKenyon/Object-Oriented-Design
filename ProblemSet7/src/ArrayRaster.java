import java.io.Console;
import java.util.ArrayList;

public class ArrayRaster extends AbstractRaster implements Raster {

    private int[] array;

    public ArrayRaster(int width, int height) {
        super(width, height);
        array = new int[height() * width()];
    }

    public ArrayRaster(int width, int height, int[] buffer) {
        super(width, height);
        array = buffer;
    }

    @Override
    public int width() {
        return super.width();
    }

    @Override
    public int height() {
        return super.height();
    }

    public int[] getBuffer() {
        return array;
    }

    @Override
    public void setRGBA(Point<Integer> integerPoint, int i) {
        boundsCheck(integerPoint);
        array[integerPoint.intY()*width() + integerPoint.intX()] = i;
    }

    @Override
    public int getRGBA(Point<Integer> integerPoint) {
        return array[integerPoint.intX()+integerPoint.intY()*width()];
    }

    @Override
    public void clear(int colorValue) {
        for (int i= 0; i<array.length; i++){
            array[i]=colorValue;
        }
    }

public void dump(int[] buffer) {
    System.arraycopy(array,0,buffer,0,array.length);
    }
    
}
