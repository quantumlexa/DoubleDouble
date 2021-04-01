package org.quantumlexa;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class DoubleToLongPriceConvertorTest {
    private final static int DEFAULT_NUMBER_OF_ZEROS = 8;

    private DoubleToLongPriceConvertor convertor;


    @BeforeEach
    private void init() {
        this.convertor = new DoubleToLongPriceConvertor();
    }


    private static Stream<Arguments> testParameters() {
        return Stream.of(
                Arguments.of("0.1", 8, 10_000_000L),
                Arguments.of("0.01", 8, 1_000_000L),
                Arguments.of("0.001", 8, 100_000L),
                Arguments.of("0.0001", 8, 10_000L),
                Arguments.of("0.00001", 8, 1_000L),
                Arguments.of("0.000001", 8, 100L),
                Arguments.of("0.0000001", 8, 10L),
                Arguments.of("0.00000001", 8, 1L),
                Arguments.of("0.000000001", 8, 0L),
                Arguments.of("1.00000001", 8, 100_000_001L),
                Arguments.of("11.00000001", 8, 1100_000_001L),
                Arguments.of("111.00000001", 8, 11100_000_001L),
                Arguments.of("1111.00000001", 8, 111100_000_001L),
                Arguments.of("11111.00000001", 8, 1111100_000_001L),
                Arguments.of("111111.00000001", 8, 11111100_000_001L),
                Arguments.of("1111111.00000001", 8, 111111100_000_001L),
                Arguments.of("11111111.00000001", 8, 1111111100_000_001L),
                Arguments.of("111111111.00000001", 8, 11111111100_000_001L),
                Arguments.of("99999999.01", 8, 9999999901000000L)
        );

    }


    private double generateRandomNumber(long min, long max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return min + random.nextDouble(max - min);

    }


    public long convertBigDecimal(double val, int numberOfZeroes) {
        long mul = (long) Math.pow(10, numberOfZeroes);
        return BigDecimal.valueOf(val)
                .multiply(BigDecimal.valueOf(mul))
                .setScale(0, RoundingMode.FLOOR)
                .longValue();
    }


    @Test
    @Ignore
    public void compareWithBigDecimalLogic() {
        int iter = 10000000;
        while (iter-- > 0) {
            double value = generateRandomNumber(-1_000_000_000, 1_000_000_000);
            long expected = convertBigDecimal(value, DEFAULT_NUMBER_OF_ZEROS);
            long actual = convertor.convert(value, DEFAULT_NUMBER_OF_ZEROS);
//            System.out.println("actual: " + actual + " expected: " + expected);
            Assertions.assertEquals(expected, actual);
        }


    }

    @ParameterizedTest
    @MethodSource("testParameters")
    public void test(String doubleStr, int numberOfZeroes, long expected) {
        double value = Double.parseDouble(doubleStr);
        Assertions.assertEquals(expected, convertor.convert(value, numberOfZeroes));

    }

}