public final class Colors {

    public static final Color TRANSPARENT = rgba(0, 0, 0, 0);
    public static final Color BLACK = rgba(0.0, 0.0, 0.0, 1.0);
    public static final Color WHITE = rgba(1.0, 1.0, 1.0, 1.0);

    private Colors() {
    }

    public static Color rgb(double r, double g, double b) {
        return rgba(r, g, b, 1);
    }

    public static Color rgba(double r, double g, double b, double a) {
        Color color = new Color() {
            @Override
            public double red() {
                return (r <= 1.0 && r >= 0 ? r : (r > .5 ? 1.0 : 0));
            }

            @Override
            public double green() {
                return (g <= 1.0 && g >= 0 ? g : (g > .5 ? 1.0 : 0));
            }

            @Override
            public double blue() {
                return (b <= 1.0 && b >= 0 ? b : (b > .5 ? 1.0 : 0));
            }

            @Override
            public double alpha() {
                return (a <= 1.0 && a >= 0 ? a : (a > .5 ? 1.0 : 0));
            }

            @Override
            public Color overlay(Color background) {
                return interpolate(alpha(), background);
            }

            @Override
            public Color interpolate(double weight, Color other) {
                return rgba(red() * weight + other.red() * (1 - weight),
                        green() * weight + other.green() * (1 - weight),
                        blue() * weight + other.blue() * (1 - weight),
                        alpha() * weight + other.alpha() * (1 - weight));
            }
        };
        return color;
    }

    public static Color rgb(int r, int g, int b) {
        return rgba(r, g, b, 255);
    }

    public static Color rgba(int r, int g, int b, int a) {
        return rgba(((double) r) / 255.0, ((double) g) / 255.0,
                ((double) b) / 255.0, ((double) a) / 255.0);
    }

    public static Color rgb(int rgb) {
        return rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, (rgb) & 0xFF);
    }

    public static Color rgba(int rgb) {
        return rgba((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, (rgb) & 0xFF, (rgb >> 24) & 0xFF);
    }

}
