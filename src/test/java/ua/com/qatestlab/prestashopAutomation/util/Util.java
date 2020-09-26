package ua.com.qatestlab.prestashopAutomation.util;

public class Util {

    public static final char USD_SIGN = '$';

    public static char getLastChar(String string) {
        return string.charAt(string.length() - 1);
    }

    public static int getNumbersFromString(String string) {
        return Integer.parseInt(string.replaceAll("\\D+",""));
    }

    public static float getPriceFromPriceString(String priceString) {
        String substring = priceString.substring(0, priceString.length() - 2);
        return Float.parseFloat(substring.replace(',', '.'));
    }
}
