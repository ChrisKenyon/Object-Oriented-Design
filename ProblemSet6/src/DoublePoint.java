public final class DoublePoint implements Point<Double> {

    private double x;
    private double y;

    private DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point<Double> point(double x, double y) {
        return new DoublePoint(x, y);
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
        return (int) Math.round(x);
    }

    @Override
    public int intY() {
        return (int) Math.round(y);
    }

    @Override
    public Double boxedX() {
        return Double.valueOf(x);
    }

    @Override
    public Double boxedY() {
        return Double.valueOf(y);
    }

    @Override
    public Point<Double> scaleBy(double factor) {
        return DoublePoint.point(x * factor, y * factor);
    }

    @Override
    public Point<Double> add(Point<?> other) {
        return DoublePoint.point(x + other.x(), y + other.y());
    }

    @Override
    public Point<Double> subtract(Point<?> other) {
        return DoublePoint.point(x - other.x(), y - other.y());
    }

    @Override
    public Point<Double> toDoublePoint() {
        return this;
    }

    @Override
    public Point<Integer> toIntegerPoint() {
        return IntegerPoint.point(x, y);
    }

    @Override
    public boolean equals(int x, int y) {
        return false;
    }

    @Override
    public boolean equals(double x, double y) {
        return this.x == x && this.y == y ? true : false;
    }

    @Override
    public Point<Double> interpolate(double weight, Point<?> other) {
        return DoublePoint.point((1.0 - weight) * x + weight * other.x(),
                (1.0 - weight) * y + weight * other.y());
    }

    @Override
    public int hashCodeForDoublePoint() {
        return this.hashCode();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.fromTBLR(y, y, x, x);
    }
}
