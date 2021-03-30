package org.quantumlexa;


import org.junit.Assert;
import org.junit.Test;

public class DoubleDoubleTest {

    static double BIG_VAL = 999_999_998.0001;
    static long MUL = 100_000_000L;
    static double DOUBLE_MUL = MUL;

    @Test
    public void multiplicationTest() {
        double bigValue = 999_999_998.0001;
        long multiplication = 100_000_000L;
        Assert.assertEquals(999_999_99800010000L, convertDD(bigValue, multiplication));


    }


    private static long convertDD(double price, long multiplication) {
        DoubleDouble dd = new DoubleDouble(price);
        return dd.of(price).multiply(multiplication).floor().longValue();
    }
}