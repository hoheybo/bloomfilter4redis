package bloomfiter;

public class Preconditions {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static void checkArgument(boolean b,  String errorMessageTemplate, int p1) {
        if (!b) {
            throw new IllegalArgumentException(String.format(errorMessageTemplate, new Object[]{p1}));
        }
    }
}
