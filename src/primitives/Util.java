package primitives;

/**
 * Util class is used for some internal utilities, e.g. controlling accuracy
 *
 * @author Dan
 */
public abstract class Util {
    // It is binary, equivalent to ~1/1,000,000,000,000 in decimal (12 digits)
    private static final int ACCURACY = -40;

    /**
     * Empty private constructor to hide the public one
     */
    private Util() {
    }

    /**
     * Retrieves the exponent of a given number in double precision format.
     * This method calculates the exponent of the specified number by performing the following steps:
     * 1. Convert the number to a set of bits using {@link Double#doubleToRawLongBits(double)}.
     * 2. Shift all 52 bits to the right, effectively removing the mantissa.
     * 3. Zero the sign of the number bit by applying a mask of 0x7FF.
     * 4. "De-normalize" the exponent by subtracting 1023.
     *
     * @param num The number for which the exponent needs to be determined.
     * @return The exponent of the specified number.
     */
    private static int getExp(double num) {
        return (int) ((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
    }

    /**
     * Checks whether the number is [almost] zero
     *
     * @param number the number to check
     * @return true if the number is zero or almost zero, false otherwise
     */
    public static boolean isZero(double number) {
        return getExp(number) < ACCURACY;
    }

    /**
     * Aligns the number to zero if it is almost zero
     *
     * @param number the number to align
     * @return 0.0 if the number is very close to zero, the number itself otherwise
     */
    public static double alignZero(double number) {
        return getExp(number) < ACCURACY ? 0.0 : number;
    }

    /**
     * Check whether two numbers have the same sign
     *
     * @param n1 1st number
     * @param n2 2nd number
     * @return true if the numbers have the same sign
     */
    public static boolean checkSign(double n1, double n2) {
        return (n1 < 0 && n2 < 0) || (n1 > 0 && n2 > 0);
    }

    /**
     * Provide a real random number in range between min and max
     *
     * @param min value (included)
     * @param max value (excluded)
     * @return the random value
     */
    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }

}
