package bloomfiter;

import java.math.RoundingMode;

public class LongMath {
    public static long divide(long p, long q, RoundingMode mode) {
        Preconditions.checkNotNull(mode);
        long div = p / q;
        long rem = p - q * div;
        if (rem == 0L) {
            return div;
        } else {
            int signum = 1 | (int)((p ^ q) >> 63);
            boolean increment;
            switch(mode) {
                case UNNECESSARY:
                    MathPreconditions.checkRoundingUnnecessary(rem == 0L);
                case DOWN:
                    increment = false;
                    break;
                case FLOOR:
                    increment = signum < 0;
                    break;
                case UP:
                    increment = true;
                    break;
                case CEILING:
                    increment = signum > 0;
                    break;
                case HALF_DOWN:
                case HALF_UP:
                case HALF_EVEN:
                    long absRem = Math.abs(rem);
                    long cmpRemToHalfDivisor = absRem - (Math.abs(q) - absRem);
                    if (cmpRemToHalfDivisor == 0L) {
                        increment = mode == RoundingMode.HALF_UP | mode == RoundingMode.HALF_EVEN & (div & 1L) != 0L;
                    } else {
                        increment = cmpRemToHalfDivisor > 0L;
                    }
                    break;
                default:
                    throw new AssertionError();
            }

            return increment ? div + (long)signum : div;
        }
    }

    public static long roundToLong(double x, RoundingMode mode) {
        double z = roundIntermediate(x, mode);
        MathPreconditions.checkInRangeForRoundingInputs(-9.223372036854776E18D - z < 1.0D & z < 9.223372036854776E18D, x, mode);
        return (long)z;
    }

    public static double roundIntermediate(double x, RoundingMode mode) {
        if (! isFinite(x) ) {
            throw new ArithmeticException("input is infinite or NaN");
        } else {
            double z;
            switch(mode) {
                case UNNECESSARY:
                    MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(x));
                    return x;
                case FLOOR:
                    if (x < 0.0D && !isMathematicalInteger(x)) {
                        return (double)((long)x - 1L);
                    }

                    return x;
                case CEILING:
                    if (x > 0.0D && !isMathematicalInteger(x)) {
                        return (double)((long)x + 1L);
                    }

                    return x;
                case DOWN:
                    return x;
                case UP:
                    if (isMathematicalInteger(x)) {
                        return x;
                    }

                    return (double)((long)x + (long)(x > 0.0D ? 1 : -1));
                case HALF_EVEN:
                    return Math.rint(x);
                case HALF_UP:
                    z = Math.rint(x);
                    if (Math.abs(x - z) == 0.5D) {
                        return x + Math.copySign(0.5D, x);
                    }

                    return z;
                case HALF_DOWN:
                    z = Math.rint(x);
                    if (Math.abs(x - z) == 0.5D) {
                        return x;
                    }

                    return z;
                default:
                    throw new AssertionError();
            }
        }
    }

    public static boolean isMathematicalInteger(double x) {
        return isFinite(x) && (x == 0.0D || 52 - Long.numberOfTrailingZeros(getSignificand(x)) <= Math.getExponent(x));
    }

    public static boolean isFinite(double d) {
        return Math.getExponent(d) <= 1023;
    }

    public static long getSignificand(double d) {
        if( !isFinite(d)){
            throw new IllegalArgumentException("d not a normal value");
        }
        int exponent = Math.getExponent(d);
        long bits = Double.doubleToRawLongBits(d);
        bits &= 4503599627370495L;
        return exponent == -1023 ? bits << 1 : bits | 4503599627370496L;
    }

}
