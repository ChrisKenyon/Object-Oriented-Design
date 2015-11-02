public final class IntegerPoint implements Point<Integer> {

    private int x;
    private int y;

    private IntegerPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private IntegerPoint(double x, double y) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
    }

    public static Point<Integer> point(int x, int y) {
        return new IntegerPoint(x, y);
    }

    public static Point<Integer> point(double x, double y) {
        return new IntegerPoint(x, y);
    }

    @Override
    public Point<Integer> interpolate(double weight, Point<?> other) {
        return IntegerPoint.point((1.0 - weight) * (double) x + weight * (double) other.x(),
                (1.0 - weight) * (double) y + weight * (double) other.y());
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.fromTBLR(y, y, x, x);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public int intX() {
        return x;
    }

    @Override
    public int intY() {
        return y;
    }

    @Override
    public Integer boxedX() {
        return Integer.valueOf(x);
    }

    @Override
    public Integer boxedY() {
        return Integer.valueOf(y);
    }

    @Override
    public Point<Integer> scaleBy(double factor) {
        return IntegerPoint.point(x * factor, y * factor);
    }

    @Override
    public Point<Integer> add(Point<?> other) {
        return IntegerPoint.point(x + other.x(), y + other.y());
    }

    @Override
    public Point<Integer> subtract(Point<?> other) {
        return IntegerPoint.point(x - other.x(), y - other.y());
    }


    @Override
    public Point<Double> toDoublePoint() {
        return DoublePoint.point(x, y);
    }

    @Override
    public Point<Integer> toIntegerPoint() {
        return this;
    }

    @Override
    public int hashCodeForIntegerPoint() {
        return this.hashCode();
    }

    @Override
    public boolean equals(int x, int y) {
        return this.x == x && this.y == y ? true : false;
    }

    @Override
    public boolean equals(double x, double y) {
        return false;
    }
}
