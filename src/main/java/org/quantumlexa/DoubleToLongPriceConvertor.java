package org.quantumlexa;

import sun.misc.FloatingDecimal;

public class DoubleToLongPriceConvertor {


    private final StringBuilder sb = new StringBuilder();
    private final int numberOfZeroes;

    public DoubleToLongPriceConvertor(int numberOfZeroes) {
        this.numberOfZeroes = numberOfZeroes;
    }

    public DoubleToLongPriceConvertor() {
        this(8);
    }

    long convert(double val, int numberOfZeroes) {
        sb.setLength(0);
        fillStringBuilder(val, sb);
        return convert(sb, numberOfZeroes);
    }

    /**
     * @param sb
     * @param numberOfZeroes
     * @return
     */
    private long convert(StringBuilder sb, int numberOfZeroes) {
        long val = 0;
        int commaIdx = -1;
        int expIdx = -1;
        int exp = 0;
        boolean expSignNegative = false;
        boolean signNegative = false;
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '.') {
                commaIdx = i;
            } else if (c == 'E') {
                expIdx = i;
            } else if (c == '-') {
                if (expIdx >= 0) {
                    expSignNegative = true;
                } else {
                    signNegative = true;
                }
            } else if (Character.isDigit(c)) {
                if (expIdx >= 0) {
                    exp = exp * 10 + c - '0';
                } else {
                    val = val * 10 + c - '0';
                }
            } else {
                throw new IllegalArgumentException("the number is not in the scientific notation: " + sb.toString());
            }
        }
        if (expSignNegative) {
            exp *= -1;
        }
        int idx = expIdx < 0 ? sb.length() - 1 : expIdx - 1;
        if (commaIdx >= 0) {
            exp -= (idx - commaIdx);
        }
        exp += numberOfZeroes;
        for (int i = 0; i < exp; i++) {
            val *= 10;
        }
        for (int i = exp; i < 0; i++) {
            if (signNegative && val % 10 != 0) {
                //that is required to implement floor behavior for negative values
                val = val / 10 + 1;
            } else {
                val = val / 10;
            }
        }

        if (signNegative) {
            val *= -1;
        }
        return val;
    }

    private void fillStringBuilder(double value, StringBuilder target) {
        FloatingDecimal.BinaryToASCIIConverter converter = FloatingDecimal.getBinaryToASCIIConverter(value);
        converter.appendTo(target);
    }


}
