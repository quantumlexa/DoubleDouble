package org.quantumlexa;

public class DoubleDoubleCustom {
    private double x, y;


    public static void main(String[] args) {
        DoubleDoubleCustom multiplication = new DoubleDoubleCustom(999_999_998.0001).multiplication(new DoubleDoubleCustom(100_000_000L));
        DoubleDoubleCustom multiplication2 = new DoubleDoubleCustom(1_999_999_998.01).multiplication(new DoubleDoubleCustom(100_000_000L));
        DoubleDoubleCustom multiplication3 = new DoubleDoubleCustom(999_999_998.00001).multiplication(new DoubleDoubleCustom(100_000_000L));
        System.out.println((long) (999_999_998.0001 * 100_000_000L));
        System.out.println(multiplication2.longValue());
        System.out.println(multiplication3.longValue());
    }

    public DoubleDoubleCustom(double x) {
        this.x = x;
        this.y = 0.0;
    }

    public DoubleDoubleCustom(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private final int SHIFT_POW = 27;


    public DoubleDoubleCustom multiplication(DoubleDoubleCustom a) {
        double x_high, x_low;
        double y_high, y_low;
        double t_1;
        double t_2;
        double t_3;
        double r_1, r_2;
        x_low = getLo(x);
        x_high = getHi(x);
        y_low = getLo(a.x);
        y_high = getHi(a.x);

        r_1 = x * a.x;
        t_1 = x_high * y_high - r_1;
        t_2 = t_1 + +x_high * y_low;
        t_3 = t_2 + x_low * y_high;
        r_2 = t_3 + x_low * y_low;
        return new DoubleDoubleCustom(r_1, r_2);
    }


    long longValue() {
        return (long) x;
    }


    double getHi(double x) {
        long C = (1L << SHIFT_POW) + 1;
        double gamma = C * x;
        double delta = x - gamma;
        return gamma + delta;
    }

    double getLo(double x) {
        long C = (1L << SHIFT_POW) + 1;
        double gamma = C * x;
        double delta = x - gamma;
        double x_hi = gamma + delta;
        return x - x_hi;
    }

}
