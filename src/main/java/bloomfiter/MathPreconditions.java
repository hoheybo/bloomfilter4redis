package bloomfiter;

import java.math.RoundingMode;

public class MathPreconditions {
   public static void checkRoundingUnnecessary(boolean condition) {
        if (!condition) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    public static void checkInRangeForRoundingInputs(boolean condition, double input, RoundingMode mode) {
        if (!condition) {
            throw new ArithmeticException("rounded value is out of range for input " + input + " and rounding mode " + mode);
        }
    }

}
