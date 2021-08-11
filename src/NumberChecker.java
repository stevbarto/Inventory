/**
 * Class to help check the format of strings to verify that they are a valid integer or double.
 * @author Steven Barton
 */
public class NumberChecker {

    /**
     * Private method to determine if a String consists of only integer values.
     *
     * @param s String to be checked.
     * @return boolean value, T if only contains integers, F if it contains anything else.
     */
    public static boolean isInteger(String s) {
        boolean isInt = true;
        for(int i = 0; i < s.length(); i++) {
            if (!s.substring(i, i + 1).matches("\\d")) {
                isInt = false;
                break;
            }
        }
        return isInt;
    }

    /**
     * Method to determine if a String contains values consistent with a double.
     *
     * @param s String value to test to see if it is in double format.
     * @return Boolean true if it is in double number format or false if not.
     */
    public static boolean isDouble(String s) {

        boolean isDbl = true;
        boolean decimal = false;
        int numPeriods = 0;

        for (int i = 0; i < s.length(); i++) {
            if (!s.substring(i, i + 1).matches("\\d") && s.charAt(i) != '.') {
                isDbl = false;
            }
            if (s.charAt(i) == '.') {
                numPeriods++;
            }
        }

        return isDbl && numPeriods <= 1;
    }
}
